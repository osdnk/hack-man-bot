package BFS;

import bot.BotState;
import move.Move;
import move.MoveType;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PathFinder {
    private BotState state;
    private BFSSearcher bfs;
    public PathFinder(BotState state) {
        this.state = state;
        this.bfs = new BFSSearcher(state);
    }
    public Move findPath (Point position) {
        String[][] fields = state.getField().getField();
        HashMap<Point, BFSNode> nodes = bfs.prepareBST(false);


        BFSNode start = nodes.get(position);
        start.visited = false;
        BFSQueueNode qnode = new BFSQueueNode();
        qnode.node = start;
        java.util.Queue<BFSQueueNode> bstNodeQueue = new LinkedList<>();
        Optional<Point> justDontPanicPosition = Optional.empty();
        bstNodeQueue.add(qnode);
        while (!bstNodeQueue.isEmpty()) {
            BFSQueueNode actualNode = bstNodeQueue.element();
            bstNodeQueue.remove();
            if (fields[actualNode.node.position.x][actualNode.node.position.y].indexOf('C') != -1) {
                BFSNode backNode = actualNode.node;
                actualNode.node.previous = actualNode.previous;
                if (backNode.previous == null)
                    continue;

                List<MoveType> moves = new ArrayList<>();

                while (backNode.previous.previous != null) {
                    moves.add(this.findMoveType(backNode.position, backNode.previous.position));
                    backNode = backNode.previous;
                }

                boolean withBomb = moves.size() > 2 && moves.get(moves.size()-1) != moves.get(moves.size()-2);

                int actualDistanceToBug = bfs.distanceToBug(backNode.previous.position);

                if (!justDontPanicPosition.isPresent()) {
                    justDontPanicPosition = Optional.of(backNode.position);
                }


                if (actualDistanceToBug > 4 || bfs.distanceToBug(backNode.position) >= actualDistanceToBug) {
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
        if (position1.x - position2.x == 1 || position2.x == state.getField().getWidth()-1 && position1.x==0)
            return MoveType.RIGHT;
        if (position1.x - position2.x == -1 || position2.x == 0 && position1.x==state.getField().getWidth()-1)
            return MoveType.LEFT;
        if (position1.y - position2.y == 1)
            return MoveType.DOWN;
        if (position1.y - position2.y == -1)
            return MoveType.UP;
        return MoveType.PASS;
    }
}
