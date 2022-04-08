import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Target {

    static List<Position> getTargetsInCross(Board board, Position position, Color color) {
        return Stream.of(
                getTargetsInDirection(board, position, color, 0, 1),
                getTargetsInDirection(board, position, color, 1, 0),
                getTargetsInDirection(board, position, color, 0, -1),
                getTargetsInDirection(board, position, color, -1, 0)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static List<Position> getTargetsInDiagonal(Board board, Position position, Color color) {
        return Stream.of(
                getTargetsInDirection(board, position, color, 1, 1),
                getTargetsInDirection(board, position, color, 1, -1),
                getTargetsInDirection(board, position, color, -1, -1),
                getTargetsInDirection(board, position, color, -1, 1)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static public List<Position> getTargetsInDirection(Board board, Position position, Color color, int dirX, int dirY) {
        List<Position> targets = new ArrayList<>();
        int currX = position.getX() + dirX;
        int currY = position.getY() + dirY;
        while (currX >= 0 && currX < 8 && currY >= 0 && currY < 8) {
            targets.add(new Position(currX, currY));
            // our piece or enemy's piece in the way
            if (board.getPieceAt(currX, currY) != null) {
                // if our piece, remove it
                if (board.getPieceAt(currX, currY).getColor() == color) {
                    targets.remove(targets.size()-1);
                }
                break;
            }
            currX += dirX;
            currY += dirY;
        }
        return targets;
    }
}
