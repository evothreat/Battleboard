import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BoardUI {

    static Color labelBgColor = Color.decode("#3f2a14");
    static Color labelFgColor = Color.decode("#FFFFFF");

    static Color blackSqColor = Color.decode("#3f2a14");
    static Color whiteSqColor = Color.decode("#D2B48C");

    static Color selectedSqColor = Color.decode("#8B4513");
    static Color highlightedSqColor = Color.decode("#E5AA70");

    private final JLabel[][] boardSquares;

    private final JPanel mainPanel;
    private final JPanel centerPanel;
    private final JPanel southPanel;
    private final JPanel westPanel;


    public BoardUI() {
        mainPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new GridLayout(8, 8));
        southPanel = new JPanel(new GridLayout(0, 8));
        westPanel = new JPanel(new GridLayout(8, 0));

        boardSquares = new JLabel[8][8];
    }

    public void init(Board board, Colour playerColor) {
        drawLabels();
        drawBoard(board, playerColor);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(westPanel, BorderLayout.WEST);
    }

    private void drawLabels() {
        String[] chars = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        String[] digs = new String[]{"8", "7", "6", "5", "4", "3", "2", "1"};

        for (int i = 0; i < 8; i++) {
            JLabel ch = new JLabel(chars[i], SwingConstants.CENTER);
            ch.setBorder(new EmptyBorder(8, 0, 8, 0));
            ch.setBackground(labelBgColor);
            ch.setForeground(labelFgColor);
            ch.setOpaque(true);

            JLabel dig = new JLabel(digs[i], SwingConstants.CENTER);
            dig.setBackground(labelBgColor);
            dig.setForeground(labelFgColor);
            dig.setOpaque(true);
            dig.setBorder(new EmptyBorder(0, 8, 0, 8));

            southPanel.add(ch);
            westPanel.add(dig);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void selectPiece(Square square, List<Square> targets) {
        JLabel sq = boardSquares[square.getX()][square.getY()];
        sq.setBackground(selectedSqColor);
        targets.forEach(s -> boardSquares[s.getX()][s.getY()].setBackground(highlightedSqColor));
    }

    public void deselectPiece(Square square, List<Square> targets) {
        JLabel sq = boardSquares[square.getX()][square.getY()];
        sq.setBackground(sq.getName().equals("w") ? whiteSqColor : blackSqColor);
        for (Square t : targets) {
            JLabel ts = boardSquares[t.getX()][t.getY()];
            ts.setBackground(ts.getName().equals("w") ? whiteSqColor : blackSqColor);
        }
    }

    public void movePiece(Square src, Square dst) {
        JLabel srcSq = boardSquares[src.getX()][src.getY()];
        boardSquares[dst.getX()][dst.getY()].setIcon(srcSq.getIcon());
        srcSq.setIcon(null);
    }

    public void swapPieces(Square src, Square dst) {
        JLabel srcSq = boardSquares[src.getX()][src.getY()];
        JLabel dstSq = boardSquares[dst.getX()][dst.getY()];
        Icon srcIcon = srcSq.getIcon();
        srcSq.setIcon(dstSq.getIcon());
        dstSq.setIcon(srcIcon);
    }

    public void deletePiece(Square square) {
        boardSquares[square.getX()][square.getY()].setIcon(null);
    }

    public void setPieceAt(Square square, Piece piece) {
        JLabel sq = boardSquares[square.getX()][square.getY()];
        sq.setIcon(getPieceIcon(piece));
    }

    // store later in static variable...
    public Icon getPieceIcon(Piece piece) {
        String c = piece.isWhite() ? "w" : "b";
        switch (piece.getPieceType()) {
            case KING:
                return new ImageIcon("resources/"+c+"k.png");
            case QUEEN:
                return new ImageIcon("resources/"+c+"q.png");
            case ROOK:
                return new ImageIcon("resources/"+c+"r.png");
            case BISHOP:
                return new ImageIcon("resources/"+c+"b.png");
            case KNIGHT:
                return new ImageIcon("resources/"+c+"n.png");
            case PAWN:
                return new ImageIcon("resources/"+c+"p.png");
        }
        return null;
    }

    private void drawBoard(Board board, Colour playerColor) {
        // draw pieces
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getSquareAt(x, y).getPiece();
                JLabel label = new JLabel("", SwingConstants.CENTER);
                if (piece != null) {
                    label.setIcon(getPieceIcon(piece));
                }
                boardSquares[x][y] = label;
            }
        }
        // draw squares
        boolean playerIsBlack = playerColor == Colour.BLACK;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int z = playerIsBlack ? 7-x : x;
                JLabel lab = boardSquares[z][y];
                lab.setOpaque(true);
                lab.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if ((z + y) % 2 == 0) {
                    lab.setBackground(whiteSqColor);
                    lab.setName("w");
                } else {
                    lab.setBackground(blackSqColor);
                    lab.setName("b");
                }
                centerPanel.add(lab);
            }
        }
    }
}