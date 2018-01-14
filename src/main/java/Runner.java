import bot.BotParser;
import bot.BotStarter;

public class Runner {
    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
}
