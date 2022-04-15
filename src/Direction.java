import java.awt.*;

public enum Direction {
    NONE(0, 0),
    N(0, 1),
    NE(1, 1),
    E(1, 0),
    SE(1, -1),
    S(0, -1),
    SW(-1, -1),
    W(-1, 0),
    NW(-1, 1);

    final int x;
    final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    static public Direction from2Squares(Square sq1, Square sq2)  {
        return Direction.fromValues(sq2.getX() - sq1.getX(), sq2.getY() - sq1.getY());
    }

    static public Direction fromValues(int x, int y) {
        if (x < 0) {
            if (y < 0) return SW;
            if (y == 0) return W;
            return NW;
        }
        if (x == 0) {
            if (y > 0) return N;
            if (y < 0) return S;
            return NONE;
        }
        if (y > 0) return NE;
        if (y == 0) return E;
        return SE;

    }
}

/*
        if (x == 0 && y > 0) return N;
        if (x > 0 && y > 0) return NE;
        if (x > 0 && y == 0) return E;
        if (x > 0 && y < 0) return SE;
        if (x == 0 && y < 0) return S;
        if (x < 0 && y < 0) return SW;
        if (x < 0 && y == 0) return W;
        if (x < 0 && y > 0) return NW;
 */