package BFS;


import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class BFSNode {
    public Point position;
    public BFSNode previous;
    public boolean visited = false;
    public List<Point> neighbours = new LinkedList<Point>();
    public BFSNode(Point position) {
        this.position = position;
    }
}
