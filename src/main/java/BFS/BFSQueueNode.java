package BFS;


public class BFSQueueNode {
    public BFSNode node;
    public BFSNode previous;
    public BFSQueueNode() {
    }
    public BFSQueueNode(BFSNode node, BFSNode previous) {
        this.node = node;
        this.previous = previous;
    }
}
