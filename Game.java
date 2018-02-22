import com.googlecode.lanterna.input.Key;

import java.util.ArrayList;

public class Game {

    private char[][] board;
    private ArrayList<Monster> monsterList = new ArrayList<>();
    private final char WALL = '\u0023';
    private Render render = new Render();
    private Player player;

    public static final int EASY = 2;
    public static final int HARD = 4;
    public static final int NIGHTMARE = 10;


    public Game(int length, int height) {

        this.board = new char[length][height];
        drawBorders();
    }

    private void drawBorders() {

        for (int i = 0; i < board.length; i++) {
            board[i][0] = WALL;
            board[i][board[0].length - 1] = WALL;
        }

        for (int i = 0; i < board[0].length; i++) {
            board[0][i] = WALL;
            board[board.length - 1][i] = WALL;
        }

    }

    public void updateBoard() throws InterruptedException {

        render.drawBoard(board);

    }

    public void gameStart(int mode) {

        player = new Player(board.length / 2, board[0].length / 2);
        board[player.getX()][player.getY()] = Player.FACE;

        int randX;
        int randY;
        int maxX = board.length - 3;
        int minX = 2;
        int maxY = board[0].length - 3;
        int minY = 2;
        for (int i = 0; i < mode; i++) {
            do {
                randX = (int) (Math.random() * ((maxX - minX) + 1) + minX);
                randY = (int) (Math.random() * ((maxY - minY) + 1) + minX);
            } while (board[randX][randY] != '\u0000');

            monsterList.add(new Monster(randX, randY));
            board[randX][randY] = Monster.FACE;
        }

    }

    public void movePlayer() throws InterruptedException {

        Key key;
        do {
            Thread.sleep(5);
            key = render.terminal.readInput();
        } while (key == null);

        switch (key.getKind()) {
            case ArrowDown:
                if (checkIfEmpty(player.getX(), player.getY() + 1)) {
                    moveCharacter(player.getX(), player.getY(), player.getX(), player.getY() + 1);
                    player.setY(player.getY() + 1);
                }
                break;
            case ArrowUp:
                if (checkIfEmpty(player.getX(), player.getY() - 1)) {
                    moveCharacter(player.getX(), player.getY(), player.getX(), player.getY() - 1);
                    player.setY(player.getY() - 1);
                }
                break;
            case ArrowLeft:
                if (checkIfEmpty(player.getX() - 1, player.getY())) {
                    moveCharacter(player.getX(), player.getY(), player.getX() - 1, player.getY());
                    player.setX(player.getX() - 1);
                }
                break;
            case ArrowRight:
                if (checkIfEmpty(player.getX() + 1, player.getY())) {
                    moveCharacter(player.getX(), player.getY(), player.getX() + 1, player.getY());
                    player.setX(player.getX() + 1);
                }
                break;
        }

    }

    private boolean checkIfEmpty(int x, int y) {

        return board[x][y] == '\u0000';
    }

    public void moveCharacter(int oldX, int oldY, int newX, int newY) {

        char tmpChar = board[oldX][oldY];
        board[oldX][oldY] = '\u0000';
        board[newX][newY] = tmpChar;
    }
}
