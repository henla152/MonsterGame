public class Monster {

    public static final char FACE = '\u25cf';

    private int x;
    private int y;

    public Monster(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void setPos(int x, int y) {

        setX(x);
        setY(y);
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }
}
