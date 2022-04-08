import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Target {

    static List<Position> getTargetsInCross(Board board, Position position, int radius) {
        return Stream.of(
                getTargetsInDirection(board, position, radius, 0, 1),
                getTargetsInDirection(board, position, radius, 1, 0),
                getTargetsInDirection(board, position, radius, 0, -1),
                getTargetsInDirection(board, position, radius, -1, 0)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static List<Position> getTargetsInDiagonal(Board board, Position position, int radius) {
        return Stream.of(
                getTargetsInDirection(board, position, radius, 1, 1),
                getTargetsInDirection(board, position, radius, 1, -1),
                getTargetsInDirection(board, position, radius, -1, -1),
                getTargetsInDirection(board, position, radius, -1, 1)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static public List<Position> getTargetsInDirection(Board board, Position position, int radius, int dirX, int dirY) {
        List<Position> targets = new ArrayList<>();
        int currX = position.getX() + dirX;
        int currY = position.getY() + dirY;
        // while in radius
        while (currX > -radius && currX < radius && currY > -radius && currY < radius) {
            targets.add(new Position(currX, currY));
            // our piece or enemy's piece in the way (if we need all positions, ignore this)
            if (board.getPieceAt(currX, currY) != null) break;
            currX += dirX;
            currY += dirY;
        }
        return targets;
    }
}
