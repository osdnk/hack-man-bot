package solution;

import bot.BotState;
import move.Move;
import move.MoveType;

import java.awt.*;

public class StepMaker {
    private Box[][] parsedMap;
    private BotState state;
    public StepMaker(BotState state) {
        this.state = state;
        MapParser mapParser = new MapParser(state.getField().getField());
        this.parsedMap = mapParser.parse();
    }
    public Move makeStep(Point myPosition) {
         Predicter predicter = new Predicter(parsedMap);
         Box [][] predictedMap = predicter.predictBugs(myPosition);
         PathFinder pathFinder = new PathFinder(predictedMap, parsedMap, state);
        Move suggest = pathFinder.findProperPath(myPosition, SearchType.Snippet);
        if (suggest.moveType != MoveType.PASS)
            return suggest;
        suggest = pathFinder.findProperPath(myPosition, SearchType.Bomb);
        if (suggest.moveType != MoveType.PASS)
            return suggest;
        return  pathFinder.findProperPath(myPosition, SearchType.Free);
    }
}
