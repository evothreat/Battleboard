import javax.swing.*;

public class Main {

    static Integer[][] testBoard = {
            {-6, -5, -4, -3, -2, -3, -4, -5},
            {0, 0, -1, -1, 0, -1, -1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 0},
            {2, 3, 4, 5, 6, 2, 3, 4}
    };

    public static void main(String[] args){
        Player player = new Player(Colour.WHITE);

        Board board = new Board(null);

        BoardUI boardUI = new BoardUI();

        Game game = new Game(board, boardUI, player, null);
        game.start();

        JFrame window = new JFrame("Chess");
        window.getContentPane().add(boardUI.getMainPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850,800);
        window.setVisible(true);
    }
}
