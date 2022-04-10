import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args){
        BoardUI boardUI = new BoardUI();
        Game game = new Game(new Board(), boardUI, new Player(Color.WHITE), null);
        game.start();

        JFrame window = new JFrame("Chess");
        window.getContentPane().add(boardUI.getMainPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850,800);
        window.setVisible(true);
    }
}
