import com.googlecode.lanterna.input.Key;

import java.util.ArrayList;

import static com.googlecode.lanterna.input.Key.Kind.Enter;


public class Game {

    public final static char WALL = '\u0023';

    private char[][] board;
    private ArrayList<Monster> monsterList;
    private ArrayList<Environment> environments;
    private Render render;
    private Player player;
    private int updateCounter = 0;
    public static int monsterCounter = 0;

    public static final int EASY = 4;
    public static final int HARD = 8;
    public static final int NIGHTMARE = 16;


    public Game(int length, int height) {

        this.board = new char[length][height];
        this.render = new Render();
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

        int randX;
        int randY;
        int maxX = board.length - 3;
        int minX = 2;
        int maxY = board[0].length - 3;
        int minY = 2;

        updateCounter++;
        if (updateCounter % 10 == 0) {
            do {
                randX = (int) (Math.random() * ((maxX - minX) + 1) + minX);
                randY = (int) (Math.random() * ((maxY - minY) + 1) + minX);
            } while (board[randX][randY] != '\u0000');

            monsterList.add(new Monster(randX, randY));
            board[randX][randY] = Monster.FACE;
            monsterCounter++;
        }
        render.drawBoard(board);
    }

    public void gameStart(int mode) {

        this.monsterList = new ArrayList<>();
        this.environments = new ArrayList<>();

        player = new Player(board.length / 2, board[0].length / 2);
        board[player.getX()][player.getY()] = Player.FACE;

        int randX;
        int randY;
        int maxX = board.length - 3;
        int minX = 2;
        int maxY = board[0].length - 3;
        int minY = 2;

        for (int i = 0; i < 9; i++) {
            do {
                randX = (int) (Math.random() * ((maxX - minX) + 1) + minX);
                randY = (int) (Math.random() * ((maxY - minY) + 1) + minX);
            } while (board[randX][randY] != '\u0000');

            environments.add(new Environment(randX, randY));
            board[randX][randY] = WALL;
            board[randX + 1][randY + 1] = WALL;
            environments.add(new Environment(randX + 1, randY + 1));
            board[randX + 1][randY] = WALL;
            environments.add(new Environment(randX + 1, randY));
            board[randX][randY + 1] = WALL;
            environments.add(new Environment(randX, randY + 1));

        }

        for (int i = 0; i < mode; i++) {
            do {
                randX = (int) (Math.random() * ((maxX - minX) + 1) + minX);
                randY = (int) (Math.random() * ((maxY - minY) + 1) + minX);
            } while (board[randX][randY] != '\u0000');

            monsterList.add(new Monster(randX, randY));
            board[randX][randY] = Monster.FACE;
            monsterCounter++;
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

    public void moveMonsters() {

        for (Monster m :
                monsterList) {
            int oldX = m.getX();
            int oldY = m.getY();
            int newX = oldX;
            int newY = oldY;

            if (m.getX() > player.getX()) newX--;
            if (m.getX() < player.getX()) newX++;
            if (m.getY() > player.getY()) newY--;
            if (m.getY() < player.getY()) newY++;

            if (board[newX][newY] != Monster.FACE && board[newX][newY] != WALL) {
                m.setPos(newX, newY);
                moveCharacter(oldX, oldY, newX, newY);
            }
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

    public boolean isGameOver() {

        for (Monster m :
                monsterList) {
            if (player.getX() == m.getX() && player.getY() == m.getY()) {
                return true;
            }
        }
        return false;
    }

    private void resetGame() {

        board[player.getX()][player.getY()] = '\u0000';
        for (Monster m :
                monsterList) {
            board[m.getX()][m.getY()] = '\u0000';
        }

        for (Environment e :
                environments) {
            board[e.getX()][e.getY()] = '\u0000';
        }

        monsterCounter = 0;
    }

    public void gameOver() throws InterruptedException {

        render.gameOver(board.length, board[0].length);

        Key key;
        do {
            key = render.terminal.readInput();
        } while (key == null || key.getKind() != Enter);

        if (key.getKind() == Enter) {
            render.clearBoard();
            this.resetGame();
        } else {
            System.exit(0);
        }
    }
}
