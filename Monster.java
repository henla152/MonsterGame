public class Monster {

    private int x;
    private int y;
    public static final char FACE = 'X';

    public Monster(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void move(int x, int y){
        setX(x);
        setY(y);
    }
    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    private void setX(int x) {

        this.x = x;
    }

    private void setY(int y) {

        this.y = y;
    }
}
