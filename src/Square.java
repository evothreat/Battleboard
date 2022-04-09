import java.util.Objects;

public class Square {

    private Piece piece;
    private final int x;
    private final int y;

    public Square(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSettled() {
        return piece != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return x == square.x && y == square.y && piece.equals(square.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, x, y);
    }
}
