package sample;

public interface Player {
    void moveUp();
    void moveDown();
    void moveRight();
    void moveLeft();
    void setMap(Map map);
    Position getPosition();
}

