import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;

public class Render {

    public Terminal terminal = TerminalFacade.createTerminal(System.in,
            System.out, Charset.forName("UTF8"));


    public Render() {

        terminal.enterPrivateMode();
        terminal.setCursorVisible(false);
    }

    public void drawBoard(char[][] board) throws InterruptedException {

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                terminal.moveCursor(x, y);
                terminal.applyBackgroundColor(0,64,0);
//                terminal.applyForegroundColor(Terminal.Color.WHITE);
                char tmp = board[x][y];
                switch (tmp) {
                    case Game.WALL:
                        terminal.applyForegroundColor(Terminal.Color.WHITE);
                        break;
                    case Player.FACE:
                        terminal.applyForegroundColor(Terminal.Color.CYAN);
                        break;
                    case Monster.FACE:
                        terminal.applyForegroundColor(Terminal.Color.RED);
                }
                terminal.putCharacter(tmp);
            }
        }
    }

    public void gameOver(int sizeX, int sizeY) throws InterruptedException {

        String gameOver = "G A M E  O V E R !";
        String newGame = "Press ENTER to restart";

        terminal.applyBackgroundColor(Terminal.Color.BLACK);
        terminal.applyForegroundColor(Terminal.Color.RED);

        for (int i = 0; i < gameOver.length(); i++) {
            terminal.moveCursor(sizeX + 7 + i, sizeY / 2 - 2);
            terminal.putCharacter(gameOver.charAt(i));
            Thread.sleep(100);
        }

        terminal.applyForegroundColor(Terminal.Color.WHITE);
        for (int i = 0; i < newGame.length(); i++) {
            terminal.moveCursor(sizeX + 5 + i, sizeY / 2);
            terminal.putCharacter(newGame.charAt(i));
            Thread.sleep(20);
        }
    }

    public void clearBoard() {
        terminal.clearScreen();
    }
}
