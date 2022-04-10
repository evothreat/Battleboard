import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args){
        BoardUI boardUI = new BoardUI();
        JFrame window = new JFrame("Chess");
        window.getContentPane().add(boardUI.getMainPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850,800);
        window.setVisible(true);
    }
}
