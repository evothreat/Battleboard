import javax.swing.*;

public class Main {

    static Integer[][] testBoard = {
            {0, -3, -4, 0, 0, 0, -6, 0},
            {0, 0, 3, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 6, 0, 0, 0}
    };

    public static void main(String[] args){
        Player player = new Player(Colour.WHITE);
        ChessAI chessAI = new ChessAI(Colour.BLACK);

        Board board = new Board(testBoard, player.getColor());
        BoardUI boardUI = new BoardUI();

        Game game = new Game(board, boardUI, player, chessAI);
        game.start();

        JFrame window = new JFrame("Chess");
        window.getContentPane().add(boardUI.getMainPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850,800);
        window.setVisible(true);
    }
}
