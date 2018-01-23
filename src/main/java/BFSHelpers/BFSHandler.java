package BFSHelpers;

import solution.Box;

import java.awt.*;
import java.util.*;

public class BFSHandler {
    private Box[][] fields;
    public BFSHandler(Box[][] fields) {
        this.fields = fields;
    }

    public int countDistance(BFSQueueNode actualNode) {
        BFSNode backNode = actualNode.node;
        actualNode.node.previous = actualNode.previous;
        int counter = -1;
        while (backNode != null) {
            counter ++;
            backNode = backNode.previous;
        }
        return counter;
    }

    private boolean checkAccesiveOrSafety(int x, int y, boolean ignoreBugs) {
        return x >= 0 && y >= 0 && x < fields.length && y < fields[0].length && (ignoreBugs ? fields[x][y].isAccesible() : fields[x][y].isSafe());
    }

    public HashMap<Point, BFSNode> prepareBST (boolean ignoreBugs) {
        HashMap<Point, BFSNode> nodes = new HashMap<>();
        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y <fields[x].length; y++) {
                if (checkAccesiveOrSafety(x,y,ignoreBugs)) {
                    BFSNode node = new BFSNode(new Point(x,y));
                    if (checkAccesiveOrSafety(x,y+1,ignoreBugs)) {
                        node.neighbours.add(new Point(x, y+1));
                    }
                    if (checkAccesiveOrSafety(x,y-1,ignoreBugs)) {
                        node.neighbours.add(new Point(x, y-1));
                    }
                    if (checkAccesiveOrSafety(x+1,y,ignoreBugs)) {
                        node.neighbours.add(new Point(x+1, y));
                    }
                    if (checkAccesiveOrSafety(x-1,y,ignoreBugs)) {
                        node.neighbours.add(new Point(x-1, y));
                    }
                    if (fields[x][y].isGateLeft && checkAccesiveOrSafety(fields.length-1,y,ignoreBugs)) {
                        node.neighbours.add(new Point(fields.length-1, y));
                    }
                    if (fields[x][y].isGateRight && checkAccesiveOrSafety(0,y,ignoreBugs)) {
                        node.neighbours.add(new Point(0, y));
                    }
                    nodes.put(new Point(x, y), node);
                }
            }
        }
        return nodes;
    }

}
