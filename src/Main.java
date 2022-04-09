import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args){
        BoardUI boardUI = new BoardUI();
        JFrame frame = new JFrame("Chess");
        frame.getContentPane().add(boardUI.getBoardPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(boardUI.getSouthPanel(), BorderLayout.SOUTH);
        frame.getContentPane().add(boardUI.getWestPanel(), BorderLayout.WEST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850,800);
        frame.setVisible(true);
    }
}
