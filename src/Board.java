import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Square[][] state;

    private Square blackKingSquare;
    private Square whiteKingSquare;

    public Board() {
        state = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            Color side = x < 4 ? Color.BLACK : Color.WHITE;
            if (x == 0 || x == 7) {
                state[x][0] = new Square(new Rook(side), x, 0);
                state[x][1] = new Square(new Knight(side), x, 1);
                state[x][2] = new Square(new Bishop(side), x, 2);
                state[x][3] = new Square(new Queen(side), x, 3);
                state[x][4] = new Square(new King(side), x, 4);
                state[x][5] = new Square(new Bishop(side), x, 5);
                state[x][6] = new Square(new Knight(side), x, 6);
                state[x][7] = new Square(new Rook(side), x, 7);
                continue;
            }
            for (int y = 0; y < 8; y++) {
                if (x == 1 || x == 6) {
                    state[x][y] = new Square(new Pawn(side), x, y);
                    continue;
                }
                state[x][y] = new Square(null, x, y);
            }
        }
    }

    public Square getKingSquare(final Color color) {
        return color.isBlack() ? blackKingSquare : whiteKingSquare;
    }

    public void setBlackKingSquare(Square blackKingSquare) {
        this.blackKingSquare = blackKingSquare;
    }

    public void setWhiteKingSquare(Square whiteKingSquare) {
        this.whiteKingSquare = whiteKingSquare;
    }

    public Piece getPieceAt(final int x, final int y) {
        return 8 > x && y >= 0 ? state[x][y].getPiece() : null;
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
        for (int i = 0; i < 8; i++) {
            for (Square sq : state[i]) {
                if (sq.isSettled() && sq.getPiece().getColor() == color) {
                    targets.addAll(sq.getPiece().getValidTargets(this, sq));
                }
            }
        }
        return targets;
    }
}
