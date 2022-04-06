import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] state;

    public Board() {
        state = new Piece[8][8];
    }

    public Piece[][] getState() {
        return state;
    }

    public Piece getPieceAt(final Position position) {
        return state[position.getX()][position.getY()];
    }

    public void move(final Position src, final Position dst) {
        state[src.getX()][src.getY()] = state[dst.getX()][dst.getY()];
    }

    // TODO: implement
    public boolean inCheck(final Color color) {
        return false;
    }
    // TODO: implement
    public MateType inMate(final Color color) {
        return MateType.NONE;
    }

    public List<Position> getLegalMoves(final Color color) {
        List<Position> legalMoves = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = state[x][y];
                if (p != null && p.getColor() == color) {
                    legalMoves.addAll(p.getLegalMoves(this, new Position(x, y)));
                }
            }
        }
        return legalMoves;
    }
}
