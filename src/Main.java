import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args){
        Player player = new Player(Color.WHITE);
        BoardUI boardUI = new BoardUI(player.getColor().isBlack());
        Game game = new Game(new Board(), boardUI, player, null);
        game.start();

        JFrame window = new JFrame("Chess");
        window.getContentPane().add(boardUI.getMainPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850,800);
        window.setVisible(true);
    }
}
