import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;

public class Render {

    public Terminal terminal = TerminalFacade.createTerminal(System.in,
            System.out, Charset.forName("UTF8"));


    public Render(){
        terminal.enterPrivateMode();
        terminal.setCursorVisible(false);
    }
    public void drawBoard(char[][] board) throws InterruptedException {


        for(int x=0;x<board.length;x++){
            for(int y=0; y<board[0].length;y++){
                terminal.moveCursor(x,y);
                terminal.applyForegroundColor(Terminal.Color.WHITE);
                terminal.putCharacter(board[x][y]);

            }
        }
    }
}
