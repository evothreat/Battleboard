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

    public void show() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(boardUI.getMainPanel());
        window.setResizable(false);                             // export to method?
        window.setVisible(true);
    }

    public void showInfoDialog(String title, String msg, String filename) {
        JOptionPane.showMessageDialog(window, msg, title, JOptionPane.INFORMATION_MESSAGE,
                                      filename != null ? new ImageIcon(filename) : null);
    }
}
