import java.sql.Time;

public class Move {

    final private Square src;
    final private Square dst;

    public Move(Square src, Square dst) {
        this.src = src;
        this.dst = dst;
    }

    public Square getSrc() {
        return src;
    }

    public Square getDst() {
        return dst;
    }

    @Override
    public String toString() {
        return "Move{" +
                "src=" + src +
                ", dst=" + dst +
                '}';
    }
}
