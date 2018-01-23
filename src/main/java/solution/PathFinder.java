package solution;

import BFSHelpers.BFSNode;
import BFSHelpers.BFSQueueNode;
import BFSHelpers.BFSHandler;
import bot.BotState;
import move.Move;
import move.MoveType;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PathFinder {
    private BotState state;
    private BFSHandler bfs;
    private Box[][] map;
    private Box[][] oldMap;
    public PathFinder(Box[][] map, Box[][] oldMap, BotState state) {
        this.map = map;
        this.oldMap = oldMap;
        this.bfs = new BFSHandler(map);
        this.state = state;
    }
    public Move findProperPath(Point position, SearchType type) {
        HashMap<Point, BFSNode> nodes = bfs.prepareBST(false);

        BFSNode start = nodes.get(position);
        Optional<Point> justDontPanicPosition = Optional.empty();
        BFSQueueNode qnode = new BFSQueueNode();
        qnode.node = start;
        java.util.Queue<BFSQueueNode> bstNodeQueue = new LinkedList<>();
        bstNodeQueue.add(qnode);
        while (!bstNodeQueue.isEmpty()) {
            BFSQueueNode actualNode = bstNodeQueue.element();
            bstNodeQueue.remove();
            if (map[actualNode.node.position.x][actualNode.node.position.y].isProper(type)) {
                BFSNode backNode = actualNode.node;
                actualNode.node.previous = actualNode.previous;
                if (backNode.previous == null)
                    continue;

                List<MoveType> moves = new ArrayList<>();

                while (backNode.previous.previous != null) {
                    moves.add(this.findMoveType(backNode.position, backNode.previous.position));
                    backNode = backNode.previous;
                }
                BugHandler bugNewMap = new BugHandler(map);

                int newDistanceToBug = bugNewMap.distanceToBug(backNode.previous.position);
                boolean withBomb = moves.size() > 3 && moves.get(moves.size()-1) != moves.get(moves.size()-2) && newDistanceToBug < 4;

                if (!justDontPanicPosition.isPresent()) {
                    justDontPanicPosition = Optional.of(backNode.position);
                }
                BugHandler bugOldMap = new BugHandler(oldMap);
                OpponentHandler opponentHandler = new OpponentHandler(map, state);
                int distanceO = opponentHandler.numberOfStepsTo(actualNode.node.position);
                int distanceM = moves.size()+1;
                if (newDistanceToBug > 2 || bugOldMap.distanceToBug(backNode.position) >= newDistanceToBug) {
                   if (!(distanceM > distanceO && distanceO < 3 ))
                         return new Move (findMoveType(backNode.position, backNode.previous.position), state.getPlayers().get(state.getMyName()).getBombs() > 0 && withBomb ? 2 : -1);
                }

            }
            if (actualNode.node.visited)
                continue;
            actualNode.node.visited = true;
            actualNode.node.previous = actualNode.previous;
            for (Point point : actualNode.node.neighbours) {
                if (!nodes.get(point).visited) {
                    bstNodeQueue.add(new BFSQueueNode(nodes.get(point), actualNode.node));
                }
            }
        }

        if (justDontPanicPosition.isPresent()) {
            Point safePosition = justDontPanicPosition.get();
            return new Move(findMoveType(safePosition, start.position));
        }

        return new Move (MoveType.PASS);
    }
    private MoveType findMoveType(Point position1, Point position2) {
        if (position1.x - position2.x == 1 || position2.x == map.length-1 && position1.x==0)
            return MoveType.RIGHT;
        if (position1.x - position2.x == -1 || position2.x == 0 && position1.x==map.length-1)
            return MoveType.LEFT;
        if (position1.y - position2.y == 1)
            return MoveType.DOWN;
        if (position1.y - position2.y == -1)
            return MoveType.UP;
        return MoveType.PASS;
    }
}
