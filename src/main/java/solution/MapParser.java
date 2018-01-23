package solution;


public class MapParser {
    private String[][] fields;
    public MapParser(String [][]fields){
        this.fields = fields;
    }
    public Box[][] parse (){
        Box[][] boxes = new Box[fields.length][fields[0].length];
        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y < fields[x].length; y++) {
                boxes[x][y] = new Box(fields[x][y]);
            }
        }
        return  boxes;
    }
}
