package core;

public class Position {
    private int x = 0;
    private int y = 0;
    private boolean obstacle = false; //obstacle if true

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, boolean obstacle) {
        this(x, y);
        this.obstacle = obstacle;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isObstacle() {
        return obstacle;
    }
    
    public void setX(int x) {
        this.x = x; 
    }
    public void setY(int y) {
        this.y = y; 
    }
    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }
    
    public int distanceTo(Position position) {
        return (int) Math.sqrt(Math.pow(position.getX() - this.x, 2) + Math.pow(position.getY() - this.y, 2));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
