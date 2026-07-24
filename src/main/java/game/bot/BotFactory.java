package game.bot;

public class BotFactory {

    public static Bot createEasyBot() {
        return new EasyBot();
    }

    public static Bot createMediumBot() {
        return new MediumBot();
    }

    public static Bot createImpossibleBot() {
        return new ImpossibleBot();
    }

}