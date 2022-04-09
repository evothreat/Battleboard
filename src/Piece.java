import java.util.List;

abstract public class Piece {

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public boolean hasSameColor(Piece other) {
        return other != null && color == other.getColor();
    }

    abstract List<Square> getValidTargets(Board board, Position position);
    abstract PieceType getPieceType();
}
