import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Color;

public class BoardUI {

    static Color labelBgColor = Color.decode("#3f2a14");
    static Color labelFgColor = Color.decode("#FFFFFF");

    static Color bSquareColor = Color.decode("#3f2a14");
    static Color wSquareColor = Color.decode("#C89D7C");

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

    public JPanel getMainPanel() {
        return mainPanel;
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
                boardSquares[x][y] = new JLabel();
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JLabel lab = boardSquares[x][y];
                lab.setOpaque(true);

                if ((x + y) % 2 == 0) {
                    lab.setBackground(bSquareColor);
                } else {
                    lab.setBackground(wSquareColor);
                }
                centerPanel.add(lab);
            }
        }
    }
}
