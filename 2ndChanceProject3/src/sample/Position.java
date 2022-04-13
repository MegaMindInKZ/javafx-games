package sample;
public class Position {
    private int x;
    private int y;
    Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    void setX(int x){
        this.x = x;
    }
    void setY(int y){
        this.y = y;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
    boolean equals(Position t){
        if(t.getX() == x && t.getY() == y){
            return true;
        }
        else{
            return false;
        }
    }
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
