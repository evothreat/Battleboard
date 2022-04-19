import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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

    private final ArrayDeque<Move> history;

    public Board(final Integer[][] state, final Colour turn) {
        this.state = new Square[8][8];
        this.turn = turn;

        blackPiecesSq = new ArrayList<>();
        whitePiecesSq = new ArrayList<>();

        history = new ArrayDeque<>();

        setState(state != null ? state : DEFAULT_BOARD_STATE);
    }

    public Colour getTurn() {
        return turn;
    }

    private void setKingSq(Square square) {
        if (turn == Colour.BLACK) {
            blackKingSq = square;
        } else {
            whiteKingSq = square;
        }
    }

    public Square getBlackKingSq() {
        return blackKingSq;
    }

    public Square getWhiteKingSq() {
        return whiteKingSq;
    }

    public List<Square> getBlackPiecesSq() {
        return blackPiecesSq;
    }

    public List<Square> getWhitePiecesSq() {
        return whitePiecesSq;
    }

    public List<Square> getAllyPiecesSq() {
        return turn == Colour.BLACK ? blackPiecesSq : whitePiecesSq;
    }

    public Square getAllyKingSq() {
        return turn == Colour.BLACK ? blackKingSq : whiteKingSq;
    }

    public List<Square> getEnemyPiecesSq() {
        return turn == Colour.BLACK ? whitePiecesSq : blackPiecesSq;
    }

    public Square getEnemyKingSq() {
        return turn == Colour.BLACK ? whiteKingSq : blackKingSq;
    }

    public Square getSquareAt(final int x, final int y) {
        return 8 > x && x >= 0 && 8 > y && y >= 0 ? state[x][y] : null;
    }

    public boolean isValidMove(Square src, Square dst) {
        makeMove(src, dst);
        turn = turn.toggle();
        boolean res = !isCheck();
        restoreMove();
        return res;
    }

    public List<Move> getKingValidMoves() {
        Square kingSq = getAllyKingSq();
        List<Move> moves = new ArrayList<>();
        for (Square t : kingSq.getPiece().getValidTargets(this, kingSq)) {
            if (isValidMove(kingSq, t)) {
                moves.add(new Move(kingSq, t));
            }
        }
        if (canCastleRight()) {
            moves.add(new Move(kingSq, getSquareAt(kingSq.getX(), 7)));
        }
        if (canCastleLeft()) {
            moves.add(new Move(kingSq, getSquareAt(kingSq.getX(), 0)));
        }
        return moves;
    }

    public List<Move> getValidMoves(Square source) {
        if (source != null && source.getPiece().isKing()) {
            return getKingValidMoves();
        }
        inCheck = false;
        Square kingSq = getAllyKingSq();
        List<Square> enemyTargets = new ArrayList<>();
        for (Square esq : getEnemyPiecesSq()) {
            Piece enemy = esq.getPiece();
            if (enemy.getValidTargets(this, esq).contains(kingSq)) {
                inCheck = true;
                enemyTargets.add(esq);
                if (!enemy.isKnight()) {
                    enemyTargets.addAll(Target.getTargetsInDirection(this, esq, Direction.from2Squares(esq, kingSq)));
                    enemyTargets.remove(enemyTargets.size()-1);
                }
                break;
            }
        }
        // NOTE: for optimization purpose, we filter steps that will anyway cause check...
        List<Move> moves;
        List<Square> allyPiecesSq;
        if (source != null) {
            moves = new ArrayList<>();
            allyPiecesSq = List.of(source);
        } else {
            moves = getKingValidMoves();
            allyPiecesSq = getAllyPiecesSq();
        }
        for (Square asq : allyPiecesSq) {
            for (Square t : asq.getPiece().getValidTargets(this, asq)) {
                if (inCheck) {
                    if (enemyTargets.contains(t) && isValidMove(asq, t)) {
                        moves.add(new Move(asq, t));
                    }
                } else if (isValidMove(asq, t)) {
                    moves.add(new Move(asq, t));
                }
            }
        }
        return moves;
    }

    public List<Square> getPossibleTargets(Square src) {
        List<Square> targets = new ArrayList<>();
        for (Move m : getValidMoves(src)) {
            targets.add(m.getDst());
        }
        return targets;
    }

    public MateType calcCheckOrMate() {
        List<Move> validMoves = getValidMoves(null);
        if (validMoves.isEmpty()) {
            if (inCheck) {
                return MateType.CHECKMATE;
            }
            return MateType.STALEMATE;
        }
        if (inCheck) {
            return MateType.CHECK;
        }
        return MateType.NONE;
    }

    public boolean isCheck() {
        Square kingSq = getAllyKingSq();
        for (Square esq : getEnemyPiecesSq()) {
            if (esq.getPiece().getValidTargets(this, esq).contains(kingSq)) {
                return true;
            }
        }
        return false;
    }

    public MoveType makeMove(final Square src, final Square dst) {
        storeMove(src, dst);
        Piece piece = src.getPiece();
        if (!piece.hasMoved()) {
            piece.setHasMoved(true);
        }
        MoveType moveType;
        if (piece.isPawn() && dst.getX() == (piece.isWhite() ? 0 : 7)) {
            promote(src, dst);
            moveType = MoveType.PROMOTION;
        }
        else if (piece.hasSameColor(dst.getPiece())) {
            castle(src, dst);
            moveType = MoveType.CASTLING;
        } else {
            move(src, dst);
            moveType = MoveType.MOVE;
        }
        turn = turn.toggle();
        return moveType;
    }

    private boolean isEnPassant(Square src, Square dst) {
        if (history.isEmpty()) return false;

        boolean w = src.getPiece().isWhite();
        if (src.getX() != (w ? 3 : 4)) return false;

        Move move = history.peek();
        assert move != null;
        Square srcLast = move.getSrc();
        Square dstLast = move.getDst();

        int dist = (int) Math.hypot(srcLast.getX() - dstLast.getX(),
                                    srcLast.getY() - dstLast.getY());
        // 1. last piece is pawn
        // 2. last pawn did 2 steps in one move
        // 3. last pawn is next to our piece
        // 4. destination of our piece is behind last pawn
        // 5. last pawn has opposite color                  (not needed)
        // 6. our piece is on 4 or 5th row
        return srcLast.getPiece().isPawn() && dist == 2 &&
               (getSquareAt(dstLast.getX(), dstLast.getY() - 1).equals(src) ||
                getSquareAt(dstLast.getX(), dstLast.getY() + 1).equals(src)) &&
               getSquareAt(w ? srcLast.getX() - 1 : srcLast.getX() + 1, srcLast.getY()).equals(dst);
    }

    // NOTE: we have to update board!? - No, because we operate with squares on board!
    private void promote(Square src, Square dst) {
        replaceSquare(getAllyPiecesSq(), src, dst);
        if (dst.isSettled()) {
            getEnemyPiecesSq().remove(dst);
        }
        src.setPiece(null);
        dst.setPiece(new Queen(turn));
    }

    private void castle(Square src, Square dst) {
        Piece king = src.getPiece();
        Piece rook = dst.getPiece();
        rook.setHasMoved(true);
        boolean isLeft = dst.getY() == 0;
        Square newKingSq = getSquareAt(src.getX(), isLeft ? src.getY()-2 : src.getY()+2);
        Square newRookSq = getSquareAt(src.getX(), isLeft ? src.getY()-1 : src.getY()+1);
        newKingSq.setPiece(king);
        newRookSq.setPiece(rook);
        src.setPiece(null);
        dst.setPiece(null);
        replaceSquare(getAllyPiecesSq(), dst, newRookSq);
        setKingSq(newKingSq);
    }

    private void move(Square src, Square dst) {
        if (src.getPiece().isKing()) {
            setKingSq(dst);
        } else {
            replaceSquare(getAllyPiecesSq(), src, dst);
        }
        if (dst.isSettled()) {
            getEnemyPiecesSq().remove(dst);
        }
        dst.setPiece(src.getPiece());
        src.setPiece(null);
    }

    private void replaceSquare(List<Square> squares, Square src, Square dst) {
        squares.set(squares.indexOf(src), dst);
    }

    private void storeMove(Square src, Square dst) {
        history.push(new Move(new Square(src), new Square(dst)));
    }

    public void restoreMove() {
        if (history.isEmpty()) return;
        Move move = history.pop();

        Square srcCp = move.getSrc();
        Square dstCp = move.getDst();
        Piece pieceCp = srcCp.getPiece();

        turn = pieceCp.getColor();

        Square src = getSquareAt(srcCp.getX(), srcCp.getY());
        Square dst = getSquareAt(dstCp.getX(), dstCp.getY());

        // reverse castling (use copies, cause of rest attributes like hasMoved!)
        if (pieceCp.hasSameColor(dstCp.getPiece())) {
            boolean isLeft = dst.getY() == 0;
            Square kingSq = getSquareAt(src.getX(), isLeft ? src.getY()-2 : src.getY()+2);
            Square rookSq = getSquareAt(src.getX(), isLeft ? src.getY()-1 : src.getY()+1);
            // we use piece copies...
            src.setPiece(pieceCp);
            dst.setPiece(dstCp.getPiece());
            kingSq.setPiece(null);
            rookSq.setPiece(null);
            replaceSquare(getAllyPiecesSq(), rookSq, dst);
            setKingSq(src);
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
            src.setPiece(pieceCp);
            if (pieceCp.isKing()) {
                setKingSq(src);
            } else {
                replaceSquare(getAllyPiecesSq(), dstCp, src);
            }
        }
    }

    public boolean canCastleRight() {
        return canCastle(Direction.N);
    }

    public boolean canCastleLeft() {
        return canCastle(Direction.S);
    }

    private boolean canCastle(Direction dir) {
        if (getAllyKingSq().getPiece().hasMoved()) return false;
        Square kingSq = getAllyKingSq();
        Square sq = Target.getNextSettledInDirection(this, kingSq, dir);
        // no need to check if same color, cause enemy's rook hasMoved condition would fail
        if (sq != null && sq.getPiece().isRook() && !sq.getPiece().hasMoved()) {
            return isValidMove(kingSq, getSquareAt(kingSq.getX(), kingSq.getY() + dir.getY())) &&
                   isValidMove(kingSq, getSquareAt(kingSq.getX(), kingSq.getY() + dir.getY() * 2));
        }
        return false;
    }

    public void setState(final Integer[][] newState) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = new Square(PieceConverter.intToPiece(newState[x][y]), x, y);
                state[x][y] = sq;

                Piece pc = sq.getPiece();
                if (pc == null) continue;
                if (pc.isKing()) {
                    if (pc.isBlack()) {
                        blackKingSq = sq;
                    } else {
                        whiteKingSq = sq;
                    }
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
