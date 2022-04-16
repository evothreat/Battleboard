import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
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

    private boolean inCheck;

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
        if (turn == Colour.BLACK) {
            blackKingSq = square;
        } else {
            whiteKingSq = square;
        }
    }

    private void setKingSq(Colour color, Square square) {
        if (color == Colour.BLACK) {
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

    public List<Move> getPossibleMoves(Square source) {
        inCheck = false;

        Square kingSq = getKingSq();
        // find attacker and their targets
        List<Square> enemyTargets = new ArrayList<>();
        for (Square esq : getEnemyPiecesSq()) {
            Piece enemy = esq.getPiece();
            if (enemy.getValidTargets(this, esq).contains(kingSq)) {
                if (enemy.isKnight()) {
                    enemyTargets.add(esq);
                } else {
                    enemyTargets.add(esq);
                    enemyTargets.addAll(Target.getTargetsInDirection(this, esq, Direction.from2Squares(esq, kingSq)));
                }
                inCheck = true;
                break;
            }
        }
        // NOTE: for optimization purpose, we filter steps that will anyway cause check...

        if (source != null) {
            List<Move> moves = new ArrayList<>();
            for (Square t : source.getPiece().getValidTargets(this, source)) {
                if (inCheck) {
                    if (enemyTargets.contains(t)) {
                        moves.add(new Move(source, t));
                    }
                } else {
                    moves.add(new Move(source, t));
                }
            }
            return moves;
        }
        List<Move> moves = kingSq.getPiece().getValidTargets(this, kingSq).stream()
                .map(sq -> new Move(kingSq, sq))
                .collect(Collectors.toList());

        for (Square asq : getAllyPiecesSq()) {
            for (Square t : asq.getPiece().getValidTargets(this, asq)) {
                if (inCheck) {
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
        List<Square> targets = new ArrayList<>();
        for (Move m : getPossibleMoves(src)) {
            if (makeMove(m.getSrc(), m.getDst())) {
                restoreMove();
                targets.add(m.getDst());
            }
        }
        return targets;
    }

    public MateType calcCheckOrMate() {
        List<Move> possibleMoves = getPossibleMoves(null);
        // if in check
        if (inCheck) {
            // if no available moves - checkmate
            if (possibleMoves.isEmpty()) {
                return MateType.CHECKMATE;
            }
            // test whether all moves are valid, if any is valid - check
            for (Move m : possibleMoves) {
                if (makeMove(m.getSrc(), m.getDst())) {
                    restoreMove();
                    return MateType.CHECK;
                }
            }
            // no moves were valid - checkmate
            return MateType.CHECKMATE;
        }
        // no check & no possible moves
        if (possibleMoves.isEmpty()) {
            return MateType.STALEMATE;
        }
        return MateType.NONE;
    }

    public boolean isCheck() {
        Square kingSq = getKingSq();
        for (Square esq : getEnemyPiecesSq()) {
            if (esq.getPiece().getValidTargets(this, esq).contains(kingSq)) {
                return true;
            }
        }
        return false;
    }

    public boolean makeMove(final Square src, final Square dst) {
        storeMove(src, dst);
        Piece piece = src.getPiece();
        if (!piece.hasMoved()) {
            piece.setHasMoved(true);
        }
        if (piece.isPawn() && dst.getX() == (piece.isWhite() ? 0 : 7)) {
            promote(src, dst);
        }
        else if (piece.hasSameColor(dst.getPiece())) {
            castle(src, dst);
        } else {
            move(src, dst);
        }
        if (isCheck()) {
            restoreMove();
            return false;
        }
        turn = turn.toggle();
        return true;
    }

    // NOTE: we have to update board!? - No, because we operate with squares on board!
    private void promote(Square src, Square dst) {
        getAllyPiecesSq().remove(src);
        getAllyPiecesSq().add(dst);
        if (dst.isSettled()) {
            getEnemyPiecesSq().remove(dst);
        }
        src.setPiece(null);
        dst.setPiece(new Queen(turn, turn.sign() * PieceUtil.QUEEN_WEIGHT));
    }

    private void castle(Square src, Square dst) {
        Piece piece = src.getPiece();;
        src.setPiece(dst.getPiece());
        dst.setPiece(piece);
        ((King) piece).setDidCastling(true);
        setKingSq(piece.getColor(), dst);
    }

    private void move(Square src, Square dst) {
        if (src.getPiece().isKing()) {
            setKingSq(src.getPiece().getColor(), dst);
        } else {
            getAllyPiecesSq().remove(src);
            getAllyPiecesSq().add(dst);
        }
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
        turn = pieceCp.getColor();

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
            if (pieceCp.isKing()) {
                // may cause problems, so pay attention...
                setKingSq(src);
            } else {
                getAllyPiecesSq().remove(dst);
                getAllyPiecesSq().add(src);
            }
        }
    }

    public void setState(final Integer[][] newState) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = new Square(PieceUtil.intToPiece(newState[x][y]), x, y);
                state[x][y] = sq;

                Piece pc = sq.getPiece();
                if (pc == null) continue;
                if (pc.isKing()) {
                    setKingSq(pc.getColor(), sq);
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
