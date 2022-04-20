import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Game {

    final private GameUI gameUI;

    final private Board board;
    final private BoardUI boardUI;

    final private ChessAI chessAI;
    final private Player player;

    private Square selectedPiece;
    private List<Square> pieceTargets;

    public Game(GameUI gameUI, Player player, ChessAI chessAI) {
        this.gameUI = gameUI;

        this.board = new Board(null, player.getColor());
        this.boardUI = gameUI.getBoardUI();

        this.player = player;
        this.chessAI = chessAI;
    }

    public void start() {
        // initialize board ui
        boardUI.init(board, player.getColor());
        // add click listener
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
        // show main window
        gameUI.setResizable(false);
        gameUI.show();
    }

    public void handleClickOnSquare(int x, int y) {
        Square dstSq = board.getSquareAt(player.getColor() == Colour.BLACK ? 7-x : x, y);
        // if source already selected
        if (selectedPiece != null) {
            // and destination is valid
            if (pieceTargets.contains(dstSq)) {
                // move
                handleMove(selectedPiece, dstSq);
                return;
            }
            // clicked on any square - clear selection
            clearSelection();
        }
        // clicked on non-empty square with own pieces
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
        handleCheckOrMate();

        Move aiMove = chessAI.computeMove(board);       // ai
        movePiece(aiMove.getSrc(), aiMove.getDst());
        handleCheckOrMate();
    }

    private void handleCheckOrMate() {
        switch (board.calcCheckOrMate()) {
            case CHECKMATE:
                if (board.getTurn() == player.getColor()) {
                    gameUI.showInfoDialog("Checkmate", "You lost...", "resources/lost.png");
                } else {
                    gameUI.showInfoDialog("Checkmate", "You won!", "resources/won.png");
                }
                System.exit(0);
                break;
            case STALEMATE:
                gameUI.showInfoDialog("Stalemate", "No one could win...", "resources/draw.png");
                System.exit(0);
                break;
        }
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

    public void setBoardState(Integer[][] state) {
        board.setState(state);
    }
}
