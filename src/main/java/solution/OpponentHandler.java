package solution;

import BFSHelpers.BFSHandler;
import BFSHelpers.BFSNode;
import BFSHelpers.BFSQueueNode;
import bot.BotState;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class OpponentHandler {
    private Box[][] map;
    private BotState state;
    public OpponentHandler(Box[][] map, BotState state){
        this.map = map;
        this.state = state;
    }
    public int numberOfStepsTo(Point position) {
        BFSHandler bfs = new BFSHandler(map);
        Point opponentPosition = state.getField().getOpponentPosition();
        HashMap<Point, BFSNode> nodes = bfs.prepareBST(false);
        if (!nodes.containsKey(opponentPosition))
            return 100;
        BFSNode start = nodes.get(opponentPosition);
        start.visited = false;
        BFSQueueNode qnode = new BFSQueueNode();
        qnode.node = start;
        Queue<BFSQueueNode> bstNodeQueue = new LinkedList<>();
        bstNodeQueue.add(qnode);
        while (!bstNodeQueue.isEmpty()) {
            BFSQueueNode actualNode = bstNodeQueue.element();
            bstNodeQueue.remove();
            if (actualNode.node.position.equals(position)) {
                return bfs.countDistance(actualNode);
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
}
