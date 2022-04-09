public class Square {

    private Piece piece;
    private final Position position;

    public Square(Piece piece, Position position) {
        this.piece = piece;
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isSettled() {
        return piece != null;
    }
}
