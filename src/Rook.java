import java.util.List;

public class Rook extends Piece {

    public Rook(Colour color) {
        super(color, PieceType.ROOK);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInCross(board, square, getColor());
    }
}
