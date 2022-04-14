import java.util.*;

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
    private Colour turn;
    private List<Square> kingDefenders;
    private boolean isKingInCheck;

    private final List<Move> storedMoves;

    public Board(final Integer[][] newState) {
        state = new Square[8][8];
        storedMoves = new ArrayList<>();

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

    public Square getSquareAt(final int x, final int y) {
        return 8 > x && x >= 0 && 8 > y && y >= 0 ? state[x][y] : null;
    }

    public MoveEvent calcCheckOrMate(final Colour color) {
        for (int i = 0; i < 8; i++) {
            for (Square sq : state[i]) {
                // enemy found
                if (sq.isSettled() && sq.getPiece().getColor() != color) {
                    Square kingSq = sq.getPiece().findEnemyKing(this, sq);
                    // enemy is an attacker
                    if (kingSq != null) {
                        isKingInCheck = true;
                        kingDefenders = findKingDefenders(color, sq, kingSq);
                        if (kingSq.getPiece().getValidTargets(this, kingSq).isEmpty() && kingDefenders.isEmpty()) {
                            return MoveEvent.CHECKMATE;
                        }
                        return MoveEvent.CHECK;
                    }
                }
            }
        }
        if (!canMove(color.toggle())) {
            return MoveEvent.STALEMATE;
        }
        return MoveEvent.NONE;
    }

    public List<Square> findKingDefenders(final Colour color, final Square enemySq, final Square kingSq) {
        List<Square> defenders = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (Square sq : state[i]) {
                if (sq.isSettled() && sq.getPiece().getColor() == color && !sq.getPiece().isKing() &&
                    sq.getPiece().canDefendKing(this, sq, enemySq, kingSq)) {
                    defenders.add(sq);
                }
            }
        }
        return defenders;
    }

    public EnumSet<MoveEvent> makeMove(final Square src, final Square dst, final boolean storeMove) {
        if (storeMove) {
            storedMoves.add(new Move(new Square(src), new Square(dst)));
        }
        EnumSet<MoveEvent> events = EnumSet.noneOf(MoveEvent.class);
        Piece piece = src.getPiece();
        if (!piece.hasMoved()) {
            piece.setHasMoved(true);
        }
        if (piece.isPawn() && dst.getX() == (piece.isWhite() ? 0 : 7)) {
            src.setPiece(null);
            piece = new Queen(piece.getColor());
            dst.setPiece(piece);
            events.add(MoveEvent.PROMOTION);
        }
        else if (piece.hasSameColor(dst.getPiece())) {
            src.setPiece(dst.getPiece());
            dst.setPiece(piece);
            ((King) piece).setDidCastling(true);
            events.add(MoveEvent.CASTLING);
        } else {
            events.add(MoveEvent.MOVE);
            dst.setPiece(piece);
            src.setPiece(null);
        }
        events.add(calcCheckOrMate(piece.getColor().toggle()));
        return events;
    }

    public void unmakeMove() {
        if (storedMoves.isEmpty()) return;

        int i = storedMoves.size()-1;
        Move lastMove = storedMoves.get(i);
        Square src = lastMove.getSrc();
        Square dst = lastMove.getDst();

        state[src.getX()][src.getY()].setPiece(state[dst.getX()][dst.getY()].getPiece());
        state[dst.getX()][dst.getY()].setPiece(null);

        storedMoves.remove(i);
    }

    public void setState(final Integer[][] newState) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                state[x][y] = new Square(PIECE_MAP.get(newState[x][y]), x, y);
            }
        }
    }

    public boolean canMove(final Colour color) {
        for (int i = 0; i < 8; i++) {
            for (Square sq : state[i]) {
                if (sq.isSettled() && sq.getPiece().getColor() == color &&
                    !sq.getPiece().getValidTargets(this, sq).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
