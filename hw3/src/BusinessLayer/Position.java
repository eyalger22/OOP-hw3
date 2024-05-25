package src.BusinessLayer;

public class Position implements Comparable<Position>{

    private int x;
    private int y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position at(int x, int y) {

        return new Position(x,y);
    }

    public static Position at(Position p) {

        return new Position(p.getX(),p.getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Position o) {
        if (x< o.x)
            return -1;
        else if (x > o.x)
            return 1;
        else if (y < o.y)
            return -1;
        return 1;
    }

    public double range(Position p){//by Euclidean Distance:
        double diffX = Math.pow(x-p.getX(),2);
        double diffY = Math.pow(y-p.getY(),2);
        return Math.sqrt(diffX + diffY);
    }
    @Override
    public boolean equals(Object o){
        if (o instanceof Position){
            Position p = ((Position) o);
            return p.getX()==getX() && p.getY()==getY();
        }
        return false;
    }

    @Override
    public String toString(){
        return "("+x+","+y+")";
    }

}
