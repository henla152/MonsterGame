public class Main {

    public static void main(String[] args) throws InterruptedException {

        //Init
        Game game = new Game(60, 30);
        game.gameStart(Game.NIGHTMARE);

        //Game loop
        while (true) {
            game.updateBoard();
            game.movePlayer();


        }
    }
}
