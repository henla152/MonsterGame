public class Main {

    public static void main(String[] args) throws InterruptedException {

        Game game = new Game(60, 30);

        while (true) {
            //Init
            game.gameStart(Game.EASY);
            boolean gameOver = false;

            //Game loop
            while (!gameOver) {
                game.updateBoard();
                game.movePlayer();
                game.moveMonsters();
                gameOver = game.isGameOver();
            }
            game.gameOver();
        }
    }
}
