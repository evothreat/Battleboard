import java.util.ArrayList;
import java.util.List;

public class Game {

    final private Board board;
    final private BoardUI boardUI;

    final private List<Move> history;

    final private Player player1;
    final private Player player2;

    public Game(Board board, BoardUI boardUI, Player player1, Player player2) {
        this.board = board;
        this.boardUI = boardUI;

        this.player1 = player1;
        this.player2 = player2;

        this.history = new ArrayList<>();
    }

    public void start() {

    }
}
