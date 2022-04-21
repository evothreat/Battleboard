import javax.swing.*;
import java.awt.*;

public class GameUI {

    private final JFrame window;
    private final BoardUI boardUI;

    public GameUI(String title) {
        window = new JFrame(title);
        boardUI = new BoardUI();
    }

    public BoardUI getBoardUI() {
        return boardUI;
    }

    public void setTitle(String title) {
        window.setTitle(title);
    }

    public void setSize(int w, int h) {
        window.setSize(w, h);
    }

    public void setResizable(boolean value) {
        window.setResizable(value);
    }

    public void showInfoDialog(String title, String msg, String filename) {
        JOptionPane.showMessageDialog(window, msg, title, JOptionPane.INFORMATION_MESSAGE,
                                      filename != null ? new ImageIcon(filename) : null);
    }

    public void show() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(boardUI.getMainPanel());
        window.pack();
        window.setVisible(true);
    }
}
