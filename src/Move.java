import java.sql.Time;

public class Move {
    final private Player player;
    final private Board board;
    final private Time begin;

    final private Square src;
    final private Square dst;

    public Move(Player player, Board board, Time begin, Square src, Square dst) {
        this.player = player;
        this.board = board;
        this.begin = begin;

        this.src = src;
        this.dst = dst;
    }
}
