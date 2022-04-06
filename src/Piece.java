import java.util.List;

abstract public class Piece {
    private Color color;

    public Color getColor() {
        return color;
    }

    abstract List<Position> getLegalMoves(Board board, Position position);
    abstract PieceType getPieceType();
}
