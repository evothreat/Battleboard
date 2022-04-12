import java.util.HashMap;
import java.util.Map;

public class Board {

    static final Map<Integer, Piece> PIECE_MAP  = new HashMap<>() {{
        put(-6, new King(Colour.BLACK));
        put(-5, new Queen(Colour.BLACK));
        put(-4, new Rook(Colour.BLACK));
        put(-3, new Bishop(Colour.BLACK));
        put(-2, new Knight(Colour.BLACK));
        put(-1, new Pawn(Colour.BLACK));
        put(0, null);
        put(6, new King(Colour.WHITE));
        put(5, new Queen(Colour.WHITE));
        put(4, new Rook(Colour.WHITE));
        put(3, new Bishop(Colour.WHITE));
        put(2, new Knight(Colour.WHITE));
        put(1, new Pawn(Colour.WHITE));
    }};

    private final Square[][] state;

    private Square blackKingSquare;
    private Square whiteKingSquare;

    public Board(Integer[][] newState) {
        state = new Square[8][8];
        if (newState != null) {
            setState(newState);
            return;
        }
        for (int x = 0; x < 8; x++) {
            Colour side = x < 4 ? Colour.BLACK : Colour.WHITE;
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

    public Square getKingSquare(final Colour color) {
        return color == Colour.BLACK ? blackKingSquare : whiteKingSquare;
    }

    public void setBlackKingSquare(Square square) {
        this.blackKingSquare = square;
    }

    public void setWhiteKingSquare(Square square) {
        this.whiteKingSquare = square;
    }

    public Square getSquareAt(final int x, final int y) {
        return 8 > x && x >= 0 && 8 > y && y >= 0 ? state[x][y] : null;
    }

    public void movePiece(Square src, Square dst) {
        dst.setPiece(src.getPiece());
        src.setPiece(null);
    }

    public void swapPieces(Square src, Square dst) {
        Piece srcPiece = src.getPiece();
        src.setPiece(dst.getPiece());
        dst.setPiece(srcPiece);
    }

    public void setState(Integer[][] newState) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                state[x][y] = new Square(PIECE_MAP.get(newState[x][y]), x, y);
            }
        }
    }
}
