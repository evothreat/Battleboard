import java.util.List;

public class Rook extends Piece {

    private boolean hasMoved;

    public Rook(Color color) {
        super(color, PieceType.ROOK);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInCross(board, square, getColor());
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
