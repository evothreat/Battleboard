import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Game {

    final private Board board;
    final private BoardUI boardUI;

    final private List<Move> history;

    final private ChessAI chessAI;
    final private Player player;

    private Square selectedPiece;
    private List<Square> pieceTargets;

    public Game(Board board, BoardUI boardUI, Player player, ChessAI chessAI) {
        this.board = board;
        this.boardUI = boardUI;

        this.player = player;
        this.chessAI = chessAI;

        this.history = new ArrayList<>();
    }

    public void start() {
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
        Square dstSq = board.getSquareAt(player.getColor().isBlack() ? 7-x : x, y);
        if (selectedPiece != null) {
            if (pieceTargets.contains(dstSq)) {
                movePiece(selectedPiece, dstSq);
                return;
            }
            clearSelection();
        }
        if (dstSq.isSettled() && dstSq.getPiece().getColor() == player.getColor()) {
            selectPiece(dstSq);
        }
    }

    private void selectPiece(Square square) {
        pieceTargets = square.getPiece().getValidTargets(board, square);
        selectedPiece = square;
        boardUI.selectPiece(square, pieceTargets);
    }

    private void movePiece(Square src, Square dst) {
        if (!src.getPiece().hasMoved()) {
            src.getPiece().setHasMoved(true);
        }
        if (src.getPiece().hasSameColor(dst.getPiece())) {
            ((King) src.getPiece()).setDidCastling(true);
            board.swapPieces(src, dst);
            boardUI.swapPieces(src, dst);
        } else {
            board.movePiece(src, dst);
            boardUI.movePiece(src, dst);
        }
        clearSelection();
    }

    private void clearSelection() {
        boardUI.deselectPiece(selectedPiece, pieceTargets);
        selectedPiece = null;
        pieceTargets = null;
    }
}
