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
        int nx = x;
        if (player.getColor().isBlack()) {
            nx = 7 - x;
        }
        Square sq = board.getSquareAt(nx, y);
        // if piece already selected
        if (selectedPiece != null) {
            // if new selected piece is a target of old piece
            if (pieceTargets.contains(sq)) {
                // move it and clear selection vars
                board.movePiece(selectedPiece, sq);
                boardUI.movePiece(selectedPiece, sq);
                clearSelection();
                return;
            }
            clearSelection();
        }
        if (sq.isSettled() && sq.getPiece().getColor() == player.getColor()) {
            pieceTargets = sq.getPiece().getValidTargets(board, sq);
            selectedPiece = sq;
            //System.out.println("Selected:" + selectedPiece + "\nTargets: " + pieceTargets + "\n\n");
            boardUI.selectPiece(sq, pieceTargets);
            // compute legal moves and highlight them
        }
    }

    private void clearSelection() {
        boardUI.deselectPiece(selectedPiece, pieceTargets);
        selectedPiece = null;
        pieceTargets = null;
    }
}
