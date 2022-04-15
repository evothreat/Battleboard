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

    private Square blackKingSq;
    private final List<Square> blackPiecesSq;

    private Square whiteKingSq;
    private final List<Square> whitePiecesSq;

    private boolean isCheck;

    private final List<Move> storedMoves;

    public Board(final Integer[][] state, final Colour turn) {
        this.state = new Square[8][8];
        this.turn = turn;

        blackPiecesSq = new ArrayList<>();
        whitePiecesSq = new ArrayList<>();

        storedMoves = new ArrayList<>();

        setState(state != null ? state : DEFAULT_BOARD_STATE);
    }

    public Colour getTurn() {
        return turn;
    }

    public Square getKingSq() {
        return turn == Colour.BLACK ? blackKingSq : whiteKingSq;
    }

    private void setKingSq(Square square) {
        if (square.getPiece().getColor() == Colour.BLACK) {
            blackKingSq = square;
        } else {
            whiteKingSq = square;
        }
    }

    public List<Square> getAllyPiecesSq() {
        return turn == Colour.BLACK ? blackPiecesSq : whitePiecesSq;
    }

    public List<Square> getEnemyPiecesSq() {
        return turn == Colour.BLACK ? whitePiecesSq : blackPiecesSq;
    }

    public Square getSquareAt(final int x, final int y) {
        return 8 > x && x >= 0 && 8 > y && y >= 0 ? state[x][y] : null;
    }

    public List<Move> getPossibleMoves() {
        isCheck = false;
        Square kingSq = getKingSq();
        List<Square> allyPiecesSq = getAllyPiecesSq();
        List<Square> enemyPiecesSq = getEnemyPiecesSq();

        List<Move> moves = kingSq.getPiece().getValidTargets(this, kingSq).stream()
                        .map(sq -> new Move(kingSq, sq))
                        .collect(Collectors.toList());

        List<Square> enemyTargets = new ArrayList<>();
        for (Square esq : enemyPiecesSq) {
            Piece enemy = esq.getPiece();
            if (enemy.getValidTargets(this, esq).contains(kingSq)) {
                if (enemy.isKnight()) {
                    enemyTargets.add(esq);
                } else {
                    enemyTargets.addAll(Target.getTargetsInDirection(this, esq, enemy.getColor(),
                                                                     Direction.from2Squares(esq, kingSq)));
                }
                isCheck = true;
                break;
            }
        }
        for (Square asq : allyPiecesSq) {
            for (Square t : asq.getPiece().getValidTargets(this, asq)) {
                if (isCheck) {
                    if (enemyTargets.contains(t)) {
                        moves.add(new Move(asq, t));
                    }
                } else {
                    moves.add(new Move(asq, t));
                }
            }
        }
        return moves;
    }

    public List<Square> getPossibleTargets(Square src) {
        return getPossibleMoves().stream().filter(m -> m.getSrc().equals(src))
                .map(Move::getDst)
                .collect(Collectors.toList());
    }

    public MoveEvent calcCheckOrMate() {
        List<Move> allPossibleMoves = getPossibleMoves();
        if (isCheck) {
            if (allPossibleMoves.isEmpty()) {
                return MoveEvent.CHECKMATE;
            }
            return MoveEvent.CHECK;
        }
        if (allPossibleMoves.isEmpty()) {
            return MoveEvent.STALEMATE;
        }
        return MoveEvent.NONE;
    }

    public EnumSet<MoveEvent> makeMove(final Square src, final Square dst) {
        EnumSet<MoveEvent> events = EnumSet.noneOf(MoveEvent.class);

        storeMove(src, dst);
        Piece piece = src.getPiece();
        if (!piece.hasMoved()) {
            piece.setHasMoved(true);
        }
        if (piece.isPawn() && dst.getX() == (piece.isWhite() ? 0 : 7)) {
            promote(src, dst);
            events.add(MoveEvent.PROMOTION);
        }
        else if (piece.hasSameColor(dst.getPiece())) {
            castle(src, dst);
            events.add(MoveEvent.CASTLING);
        } else {
            move(src, dst);
            events.add(MoveEvent.MOVE);
        }
        // set king square
        if (dst.getPiece().isKing()) {
            setKingSq(dst);
        }
        // does check/mate occur if we move? - If yes, restore old state and return
        //turn = turn.toggle();
        if (calcCheckOrMate().isCheckOrMate()) {
            restoreMove();
            events.clear();
            events.add(MoveEvent.NONE);
            //turn = turn.toggle();
            return events;
        }
        // calc check/mate for enemy and return result
        turn = turn.toggle();
        events.add(calcCheckOrMate());
        return events;
    }

    // NOTE: we have to update board!? - No, because we operate with squares on board!
    private void promote(Square src, Square dst) {
        getAllyPiecesSq().remove(src);
        getAllyPiecesSq().add(dst);
        if (dst.isSettled()) {
            getEnemyPiecesSq().remove(dst);
        }
        src.setPiece(null);
        dst.setPiece(new Queen(turn));
    }

    private void castle(Square src, Square dst) {
        Piece piece = src.getPiece();;
        src.setPiece(dst.getPiece());
        dst.setPiece(piece);
        ((King) piece).setDidCastling(true);
    }

    private void move(Square src, Square dst) {
        getAllyPiecesSq().remove(src);
        getAllyPiecesSq().add(dst);
        if (dst.isSettled()) {
            getEnemyPiecesSq().remove(dst);
        }
        dst.setPiece(src.getPiece());
        src.setPiece(null);
    }

    private void storeMove(Square src, Square dst) {
        storedMoves.add(new Move(new Square(src), new Square(dst)));
    }

    public void restoreMove() {
        if (storedMoves.isEmpty()) return;
        int i = storedMoves.size()-1;
        Move move = storedMoves.get(i);
        storedMoves.remove(i);

        Square srcCp = move.getSrc();
        Square dstCp = move.getDst();
        Piece pieceCp = srcCp.getPiece();

        // reverse turn
        turn = turn.toggle();

        Square src = getSquareAt(srcCp.getX(), srcCp.getY());
        Square dst = getSquareAt(dstCp.getX(), dstCp.getY());

        // reverse castling (use copies, cause of rest attributes like hasMoved!)
        if (pieceCp.hasSameColor(dstCp.getPiece())) {
            Piece tmp = srcCp.getPiece();
            srcCp.setPiece(dstCp.getPiece());
            dstCp.setPiece(tmp);
        }
        // reverse move & promotion
        else {
            // restore enemy piece
            if (dstCp.isSettled()) {
                getEnemyPiecesSq().add(dst);
                dst.setPiece(dstCp.getPiece());
            } else {
                dst.setPiece(null);
            }
            src.setPiece(srcCp.getPiece());
            getAllyPiecesSq().remove(dst);
            getAllyPiecesSq().add(src);
        }
        if (pieceCp.isKing()) {
            setKingSq(src);
        }
    }

    public void setState(final Integer[][] newState) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = new Square(PIECE_MAP.get(newState[x][y]), x, y);
                state[x][y] = sq;

                Piece pc = sq.getPiece();
                if (pc == null) continue;
                if (pc.isKing()) {
                    setKingSq(sq);
                    continue;
                }
                if (pc.isBlack()) {
                    blackPiecesSq.add(sq);
                } else {
                    whitePiecesSq.add(sq);
                }
            }
        }
    }
}
