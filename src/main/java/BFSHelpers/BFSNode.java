package BFSHelpers;


import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class BFSNode {
    public Point position;
    public BFSNode previous;
    public boolean visited;
    public List<Point> neighbours = new LinkedList<>();
    public BFSNode(Point position) {
        this.position = position;
        this.visited = false;
    }
}
