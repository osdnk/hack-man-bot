package BFS;

import bot.BotState;
import java.awt.*;
import java.util.*;

public class BFSSearcher {
    private BotState state;
    public int distanceToBug(Point position) {
        String[][] fields = state.getField().getField();
        HashMap<Point, BFSNode> nodes = prepareBST(true);
        BFSNode start = nodes.get(position);
        start.visited = false;
        BFSQueueNode qnode = new BFSQueueNode();
        qnode.node = start;
        Queue<BFSQueueNode> bstNodeQueue = new LinkedList<>();
        bstNodeQueue.add(qnode);
        while (!bstNodeQueue.isEmpty()) {
            BFSQueueNode actualNode = bstNodeQueue.element();
            bstNodeQueue.remove();
            if (fields[actualNode.node.position.x][actualNode.node.position.y].indexOf('E') != -1) {
                BFSNode backNode = actualNode.node;
                actualNode.node.previous = actualNode.previous;
                int counter = -1;
                while (backNode != null) {
                    counter ++;
                    backNode = backNode.previous;
                }
               return counter;

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

        return 100;
    }
    public BFSSearcher(BotState state) {
        this.state = state;
    }

    public HashMap<Point, BFSNode> prepareBST (boolean withBugs) {
        String[][] fields = state.getField().getField();
        HashMap<Point, BFSNode> nodes = new HashMap<>();
        for (int y = 0; y < state.getField().getHeight(); y++) {
            for (int x = 0; x < state.getField().getWidth(); x++) {
                if (state.getField().isPointSafe(new Point(x, y), withBugs)) {
                    BFSNode node = new BFSNode(new Point(x,y));
                    if (state.getField().isPointSafe(new Point(x, y+1), withBugs)) {
                        node.neighbours.add(new Point(x, y+1));
                    }
                    if (state.getField().isPointSafe(new Point(x, y-1), withBugs)) {
                        node.neighbours.add(new Point(x, y-1));
                    }
                    if (state.getField().isPointSafe(new Point(x+1, y), withBugs)) {
                        node.neighbours.add(new Point(x+1, y));
                    }
                    if (state.getField().isPointSafe(new Point(x-1, y), withBugs)) {
                        node.neighbours.add(new Point(x-1, y));
                    }
                    if (fields[x][y].contains("Gl") && state.getField().isPointSafe(new Point(state.getField().getWidth()-1, y), withBugs)) {
                        node.neighbours.add(new Point(state.getField().getWidth()-1, y));
                    }
                    if (fields[x][y].contains("Gr") && state.getField().isPointSafe(new Point(0, y), withBugs)) {
                        node.neighbours.add(new Point(0, y));
                    }
                    nodes.put(new Point(x, y), node);
                }
            }
        }
        return nodes;
    }

}
