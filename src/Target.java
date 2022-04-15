import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Target {

    static List<Square> getTargetsInCross(Board board, Square square) {
        List<Square> targets = getTargetsInDirection(board, square, Direction.N);
        targets.addAll(getTargetsInDirection(board, square, Direction.S));
        targets.addAll(getTargetsInDirection(board, square, Direction.W));
        targets.addAll(getTargetsInDirection(board, square, Direction.E));
        return targets;
    }

    static List<Square> getTargetsInDiagonal(Board board, Square square) {
        List<Square> targets = getTargetsInDirection(board, square, Direction.NE);
        targets.addAll(getTargetsInDirection(board, square, Direction.SE));
        targets.addAll(getTargetsInDirection(board, square, Direction.SW));
        targets.addAll(getTargetsInDirection(board, square, Direction.NW));
        return targets;
    }

    static public List<Square> getTargetsInDirection(Board board, Square square, Direction dir) {
        List<Square> targets = new ArrayList<>();

        int currX = square.getX() + dir.getX();
        int currY = square.getY() + dir.getY();

        Colour color = square.getPiece().getColor();

        while (currX >= 0 && currX < 8 && currY >= 0 && currY < 8) {
            Square sq = board.getSquareAt(currX, currY);
            targets.add(sq);
            // our piece or enemy's piece in the way
            if (sq.isSettled()) {
                // if our piece, remove it
                if (sq.getPiece().getColor() == color) {
                    targets.remove(targets.size()-1);
                }
                break;
            }
            currX += dir.getX();
            currY += dir.getY();
        }
        return targets;
    }

    // MAYBE RETURN LAST SQUARE
    static public Square getNextPieceSqInDirection(Board board, Square square, Direction dir) {
        int currX = square.getX() + dir.getX();
        int currY = square.getY() + dir.getY();

        Colour color = square.getPiece().getColor();

        while (currX >= 0 && currX < 8 && currY >= 0 && currY < 8) {
            Square sq = board.getSquareAt(currX, currY);
            if (sq.isSettled() && sq.getPiece().getColor() == color) {
                return sq;
            }
            currX += dir.getX();
            currY += dir.getY();
        }
        return null;
    }
}