package solution;

import BFSHelpers.BFSHandler;
import BFSHelpers.BFSNode;
import BFSHelpers.BFSQueueNode;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Predicter {
    private Box[][] boxMap;

    public Predicter(Box[][] boxMap) {
        this.boxMap = boxMap;
    }

    Point predictNextBugPosition(Point bugPosition, Point myPosition) {
        BFSHandler handler = new BFSHandler(boxMap);
        HashMap<Point, BFSNode> nodes = handler.prepareBST(true);
        BFSNode start = nodes.get(bugPosition);
        start.visited = false;
        BFSQueueNode qnode = new BFSQueueNode();
        qnode.node = start;
        java.util.Queue<BFSQueueNode> bstNodeQueue = new LinkedList<>();
        bstNodeQueue.add(qnode);
        while (!bstNodeQueue.isEmpty()) {
            BFSQueueNode actualNode = bstNodeQueue.element();
            bstNodeQueue.remove();
            if (actualNode.node.position.equals(myPosition)) {
                BFSNode backNode = actualNode.node;
                actualNode.node.previous = actualNode.previous;
                if (backNode.previous == null)
                    return backNode.position;
                if (backNode.previous.previous == null) //do not simulate crashes
                    return backNode.previous.position;
                while (backNode.previous.previous != null) {
                    backNode = backNode.previous;
                }
                return backNode.position;
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
    return new Point(-1,-1);
    }

    public Box[][] predictBugs(Point myPosition) {
        Box[][] clonedBoxMap = new Box[this.boxMap.length][];
        for (int i = 0; i < this.boxMap.length; i++) {
            clonedBoxMap[i] = new Box[boxMap[i].length];
            for (int j = 0; j < this.boxMap[i].length; j++) {
                clonedBoxMap[i][j] = new Box(boxMap[i][j]);
            }
        }
        for (int x = 0; x < boxMap.length; x++)
            for (int y = 0; y < boxMap[x].length; y++) {
                if (boxMap[x][y].isBug()) {
                     clonedBoxMap[x][y].removeBug();
                     Point newBugPosition = this.predictNextBugPosition(new Point(x,y), myPosition);
                     clonedBoxMap[newBugPosition.x][newBugPosition.y].addBug();
                }
            }
        return clonedBoxMap;
    }

}
