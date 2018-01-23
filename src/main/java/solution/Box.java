package solution;

public class Box {
    public boolean isGateLeft;
    public boolean isGateRight;
    private boolean isBug;
    private boolean isCodeSnippet;
    private boolean isWall;
    private boolean isFree;
    private boolean isBomb;
    private boolean isPlayer;
    private boolean isSpawnPoint;
    public Box (Box another) {
        this.isBug = another.isBug;
        this.isCodeSnippet = another.isCodeSnippet;
        this.isWall = another.isWall;
        this.isFree = another.isFree;
        this.isBomb = another.isBomb;
        this.isPlayer = another.isPlayer;
        this.isGateLeft= another.isGateLeft;
        this.isGateRight= another.isGateRight;
        this.isSpawnPoint= another.isSpawnPoint;
    }

    public Box(String description) {
        this.isBug = description.contains("E");
        this.isBomb = description.contains("B");
        this.isWall = description.equals("x");
        this.isFree = description.equals(".");
        this.isCodeSnippet = description.contains("C");
        this.isGateLeft = description.contains("Gl");
        this.isGateRight = description.contains("Gr");
        this.isPlayer = description.contains("P");
        this.isSpawnPoint = description.contains("S");
    }

    public boolean isBug() {
        return isBug;
    }

    public boolean isProper(SearchType type) {
        switch (type){
            case Bomb: return isBomb;
            case Snippet: return isCodeSnippet;
            case Free: return isFree;
            default: return true;
        }
    }


    public boolean isAccesible() {
        return !this.isWall;
    }
    public boolean isSafe(){
        return !this.isWall && !this.isBug;
    }
    public void removeBug() {
        this.isBug = false;
    }
    public void addBug() {
        this.isBug = true;
    }


}
