import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
    static final Integer[][] DEFAULT_BOARD_STATE = {
            {-4, -2, -3, -5, -6, -3, -2, -4},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {4, 2, 3, 5, 6, 3, 2, 4}
    };

    private final Square[][] state;

    private Colour turn;

    private List<Square> kingDefense;
    private boolean kingInCheck;

    private final List<Move> storedMoves;

    public Board(final Integer[][] state, final Colour turn) {
        this.state = new Square[8][8];
        this.turn = turn;

        storedMoves = new ArrayList<>();

        setState(state != null ? state : DEFAULT_BOARD_STATE);
    }

    public Square getSquareAt(final int x, final int y) {
        return 8 > x && x >= 0 && 8 > y && y >= 0 ? state[x][y] : null;
    }

    public MoveEvent calcCheckOrMate(final Colour color) {
        for (int i = 0; i < 8; i++) {
            for (Square sq : state[i]) {
                // enemy found
                if (sq.isSettled() && sq.getPiece().getColor() != color) {
                    Square kingSq = sq.getPiece().getEnemyKing(this, sq);
                    // enemy is an attacker
                    if (kingSq != null) {
                        kingInCheck = true;
                        kingDefense = getKingDefenseTargets(color, sq, kingSq);
                        // will be calculated only once so don't worry about...
                        if (kingDefense.isEmpty() && kingSq.getPiece().getValidTargets(this, kingSq).isEmpty()) {
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

    public List<Square> getKingDefenseTargets(final Colour color, final Square enemySq, final Square kingSq) {
        List<Square> defense = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (Square sq : state[i]) {
                if (sq.isSettled() && sq.getPiece().getColor() == color && !sq.getPiece().isKing()) {
                    defense.addAll(sq.getPiece().getKingDefenseTargets(this, sq, enemySq, kingSq));
                }
            }
        }
        return defense;
    }

    public boolean isKingInCheck(final Colour color) {
        return color == turn && kingInCheck;
    }

    public List<Square> getKingDefenseIntersect(final Square square) {
        return square.getPiece().getValidTargets(this, square).stream().
                filter(sq -> kingDefense.contains(sq)).collect(Collectors.toList());
    }

    public List<Square> getValidTargets(final Board board, final Square square) {
        Colour pieceColor = square.getPiece().getColor();
        if (turn != pieceColor) {
            return new ArrayList<>();
        }
        if (isKingInCheck(pieceColor)) {
            return getKingDefenseIntersect(square);
        }
        return square.getPiece().getValidTargets(this, square);
    }

    public EnumSet<MoveEvent> makeMove(final Square src, final Square dst, final boolean storeMove) {
        if (getValidTargets(this, src).isEmpty()) {
            return EnumSet.of(MoveEvent.NONE);
        }
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
        kingInCheck = false;
        events.add(calcCheckOrMate(piece.getColor().toggle()));
        turn = turn.toggle();
        return events;
    }

    public Colour getTurn() {
        return turn;
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
