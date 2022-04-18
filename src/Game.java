import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Game {

    final private Board board;
    final private BoardUI boardUI;

    final private ChessAI chessAI;
    final private Player player;

    private Square selectedPiece;
    private List<Square> pieceTargets;

    public Game(Board board, BoardUI boardUI, Player player, ChessAI chessAI) {
        this.board = board;
        this.boardUI = boardUI;

        this.player = player;
        this.chessAI = chessAI;
    }

    public void start() {
        boardUI.init(board, player.getColor());
        boardUI.getCenterPanel().addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JPanel panel = (JPanel) e.getSource();
                        int cellW = panel.getWidth() / 8;
                        int cellH = panel.getHeight() / 8;
                        handleClickOnSquare(e.getY() / cellH, e.getX() / cellW);
                    }
                }
        );
    }

    public void handleClickOnSquare(int x, int y) {
        Square dstSq = board.getSquareAt(player.getColor() == Colour.BLACK ? 7-x : x, y);
        if (selectedPiece != null) {
            if (pieceTargets.contains(dstSq)) {
                handleMove(selectedPiece, dstSq);
                return;
            }
            clearSelection();
        }
        if (dstSq.isSettled() && dstSq.getPiece().getColor() == player.getColor()) {
            selectPiece(dstSq);
        }
    }

    private void selectPiece(Square square) {
        pieceTargets = board.getPossibleTargets(square);
        selectedPiece = square;
        boardUI.selectPiece(square, pieceTargets);
    }

    private void handleMove(Square src, Square dst) {
        movePiece(src, dst);                            // player
        clearSelection();

        // TODO: check for check & mate

        Move aiMove = chessAI.computeMove(board);       // ai
        movePiece(aiMove.getSrc(), aiMove.getDst());
    }

    private void movePiece(Square src, Square dst) {
        Piece dstPiece = dst.getPiece();
        switch (board.makeMove(src, dst)) {
            case PROMOTION:
                boardUI.deletePiece(src);
                boardUI.setPieceAt(dst, dstPiece);
                break;
            case CASTLING:
                boolean isLeft = dst.getY() == 0;
                Square kingSq = board.getSquareAt(src.getX(), isLeft ? src.getY()-2 : src.getY()+2);
                Square rookSq = board.getSquareAt(src.getX(), isLeft ? src.getY()-1 : src.getY()+1);
                boardUI.deletePiece(src);
                boardUI.deletePiece(dst);
                boardUI.setPieceAt(kingSq, kingSq.getPiece());
                boardUI.setPieceAt(rookSq, rookSq.getPiece());
                break;
            case MOVE:
                boardUI.movePiece(src, dst);
                break;
        }
    }

    private void clearSelection() {
        boardUI.deselectPiece(selectedPiece, pieceTargets);
        selectedPiece = null;
        pieceTargets = null;
    }
}
