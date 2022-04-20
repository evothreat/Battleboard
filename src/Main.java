import javax.swing.*;

public class Main {

    static Integer[][] testBoard = {
            {-4, 0, -3, 0, 0, 0, 0, -4},
            {-1, -1, -1, -6, 0, -1, -1, -1},
            {0, 0, -2, 0, 0, 0, 0, 0},
            {0, 0, 3, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 3, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0},
            {4, 2, 0, 5, 6, 0, 0, 4}
    };

    public static void main(String[] args){
        GameUI gameUI = new GameUI("Chess");
        gameUI.setSize(780, 760);

        Player player = new Player(Colour.WHITE);
        ChessAI chessAI = new ChessAI(Colour.BLACK);

        Game game = new Game(gameUI, player, chessAI);
        game.setBoardState(testBoard);
        game.start();
    }
}
