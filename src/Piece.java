import java.util.List;

abstract public class Piece {
    private Color color;

    public Color getColor() {
        return color;
    }
    public boolean hasSameColor(Piece other) {
        return other != null && color == other.getColor();
    }

    abstract List<Position> getValidTargets(Board board, Position position);
    abstract PieceType getPieceType();
}
