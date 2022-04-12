import java.util.List;

public class Bishop extends Piece {

    public Bishop(Colour color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInDiagonal(board, square, getColor());
    }
}
