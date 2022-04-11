import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Color;
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

        initLabels();
        initBoard();

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(westPanel, BorderLayout.WEST);
    }

    private void initLabels() {
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

    private void initBoard() {
        for (int x = 0; x < 8; x++) {
            String c = x < 4 ? "b" : "w";
            if (x == 0 || x == 7) {
                boardSquares[x][0] = new JLabel(new ImageIcon("resources/"+c+"r.png"));
                boardSquares[x][1] = new JLabel(new ImageIcon("resources/"+c+"n.png"));
                boardSquares[x][2] = new JLabel(new ImageIcon("resources/"+c+"b.png"));
                boardSquares[x][3] = new JLabel(new ImageIcon("resources/"+c+"q.png"));
                boardSquares[x][4] = new JLabel(new ImageIcon("resources/"+c+"k.png"));
                boardSquares[x][5] = new JLabel(new ImageIcon("resources/"+c+"b.png"));
                boardSquares[x][6] = new JLabel(new ImageIcon("resources/"+c+"n.png"));
                boardSquares[x][7] = new JLabel(new ImageIcon("resources/"+c+"r.png"));
                continue;
            }
            for (int y = 0; y < 8; y++) {
                if (x == 1 || x == 6) {
                    boardSquares[x][y] = new JLabel(new ImageIcon("resources/"+c+"p.png"));
                    continue;
                }
                boardSquares[x][y] = new JLabel("", SwingConstants.CENTER);
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JLabel lab = boardSquares[x][y];
                lab.setOpaque(true);
                lab.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if ((x + y) % 2 == 0) {
                    lab.setBackground(blackSqColor);
                    lab.setName("b");
                } else {
                    lab.setBackground(whiteSqColor);
                    lab.setName("w");
                }
                centerPanel.add(lab);
            }
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
}