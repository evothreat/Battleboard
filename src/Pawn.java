import java.util.ArrayList;
import java.util.List;


// after move check whether pawn became queen!
public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        List<Square> targets = new ArrayList<>();
        int x = square.getX();
        int y = square.getY();
        boolean w = getColor().isWhite();
        // index out of range not possible, cause pawn becomes queen
        Square sq1 = board.getSquareAt(w ? x-1 : x+1, y);
        if (!sq1.isSettled()) {
            targets.add(sq1);
        }
        Square sq2 = board.getSquareAt(w ? x-2 : x+2, y);
        if (x == (w ? 6 : 1) && !sq2.isSettled()) {
            targets.add(sq2);
        }
        Square sq3 = board.getSquareAt(x-1, w ? y+1 : y-1);
        if (sq3.isSettled() && !hasSameColor(sq3.getPiece())) {
            targets.add(sq3);
        }
        Square sq4 = board.getSquareAt(w ? x-1 : x+1, y+1);
        if (sq4.isSettled() && !hasSameColor(sq1.getPiece())) {
            targets.add(sq4);
        }
        return targets;
    }
}