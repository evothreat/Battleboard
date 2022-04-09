import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Square[][] state;

    public Board() {
        state = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            Color side = x < 4 ? Color.BLACK : Color.WHITE;
            if (x == 0 || x == 7) {
                state[x][0] = new Square(new Rook(side), new Position(x, 0));
                state[x][1] = new Square(new Knight(side), new Position(x, 1));
                state[x][2] = new Square(new Bishop(side), new Position(x, 2));
                state[x][3] = new Square(new Queen(side), new Position(x, 3));
                state[x][4] = new Square(new King(side), new Position(x, 4));
                state[x][5] = new Square(new Bishop(side), new Position(x, 5));
                state[x][6] = new Square(new Knight(side), new Position(x, 6));
                state[x][7] = new Square(new Rook(side), new Position(x, 7));
                continue;
            }
            for (int y = 0; y < 8; y++) {
                if (x == 1 || x == 6) {
                    state[x][y] = new Square(new Pawn(side), new Position(x, y));
                    continue;
                }
                state[x][y] = new Square(null, new Position(x, y));
            }
        }
    }

    public Piece getPieceAt(final int x, final int y) {
        return 8 > x && y >= 0 ? state[x][y].getPiece() : null;
    }

    public Piece getPieceAt(final Position position) {
            return getPieceAt(position.getX(), position.getY());
    }

    public void setPieceAt(final Position position, Piece piece) {
        state[position.getX()][position.getY()].setPiece(piece);
    }

    public Square getSquareAt(final int x, final int y) {
        return 8 > x && y >= 0 ? state[x][y] : null;
    }

    public boolean isSettled(final int x, final int y) {
        return 8 > x && y >= 0 && state[x][y].isSettled();
    }

    public void movePiece(final Position src, final Position dst) {
        state[dst.getX()][dst.getY()].setPiece(state[src.getX()][src.getY()].getPiece());
        state[src.getX()][src.getY()].setPiece(null);
    }

    // TODO: implement
    public boolean inCheck(final Color color) {
        return false;
    }
    // TODO: implement
    public MateType inMate(final Color color) {
        return MateType.NONE;
    }

    public List<Square> getValidTargets(final Color color) {
        List<Square> targets = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = state[x][y].getPiece();
                if (p != null && p.getColor() == color) {
                    targets.addAll(p.getValidTargets(this, new Position(x, y))); // TODO: pass x and y instead of position
                }
            }
        }
        return targets;
    }
}
