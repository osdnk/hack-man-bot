package solution;

import BFSHelpers.BFSHandler;
import BFSHelpers.BFSNode;
import BFSHelpers.BFSQueueNode;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BugHandler {
    private Box[][] fields;
    public BugHandler(Box[][] fields) {
        this.fields = fields;
    }
    public int distanceToBug(Point position) {
        BFSHandler bfs = new BFSHandler(fields);
        HashMap<Point, BFSNode> nodes = bfs.prepareBST(true);
        BFSNode start = nodes.get(position);
        start.visited = false;
        BFSQueueNode qnode = new BFSQueueNode();
        qnode.node = start;
        Queue<BFSQueueNode> bstNodeQueue = new LinkedList<>();
        bstNodeQueue.add(qnode);
        while (!bstNodeQueue.isEmpty()) {
            BFSQueueNode actualNode = bstNodeQueue.element();
            bstNodeQueue.remove();
            if (fields[actualNode.node.position.x][actualNode.node.position.y].isBug()) {
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
