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

    public Square(Square other) {
        this.x = other.getX();
        this.y = other.getY();
        if (other.piece == null) {
            return;
        }
        switch (other.piece.getPieceType()) {
            case KING:
                this.piece = new King((King) other.piece);
                break;
            case QUEEN:
                this.piece = new Queen((Queen) other.piece);
                break;
            case ROOK:
                this.piece = new Rook((Rook) other.piece);
                break;
            case BISHOP:
                this.piece = new Bishop((Bishop) other.piece);
                break;
            case KNIGHT:
                this.piece = new Knight((Knight) other.piece);
                break;
            case PAWN:
                this.piece = new Pawn((Pawn) other.piece);
        }
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
        return x == square.x && y == square.y; // && piece.equals(square.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y); //,piece);
    }

    @Override
    public String toString() {
        return "Square{" +
                "piece=" + piece +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
