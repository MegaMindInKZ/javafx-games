package sample;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Bullet {
    private long time;
    Circle bullet;
    boolean isEnded;
    Tank tank;
    Timeline timeline;
    int x;
    int y;
    boolean isAllowed;
    Bullet(Tank tank) {
        this.tank = tank;
        bullet = new Circle();
        bullet.setRadius(1.25);
        isEnded = false;
        isAllowed = true;
        bullet.setStroke(Color.BLACK);
        bullet.setFill(Color.BLACK);
        x = tank.getPosition().getX();
        y = tank.getPosition().getY();
        char b = tank.direction;
        timeline = new Timeline(new KeyFrame(Duration.millis(75), e ->{
            switch (b){
                case 'U':
                    bullet.setCenterY(y * 20);
                    bullet.setCenterX(x * 20 + 10);
                    fireUp(); break;
                case 'D':
                    bullet.setCenterY((y + 1) * 20);
                    bullet.setCenterX( x* 20 + 10);
                    fireDown(); break;
                case 'R':
                    bullet.setCenterX((x + 1) * 20);
                    bullet.setCenterY(y * 20 + 10);
                    fireRight(); break;
                case 'L':
                    bullet.setCenterX(x * 20);
                    bullet.setCenterY(y * 20 + 10);
                    fireLeft(); break;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        isEnded = true;
    }
    private void fireUp(){
        y--;
        if(y >= 0){
            if(tank.map.getValueAt(new Position(x, y)).canBulletPass()){
                bullet.setCenterY(bullet.getCenterY() - 20);
            }
            else {
                if (tank.map.getValueAt(new Position(x, y)) instanceof BrickWall) {
                    ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((BrickWall) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                } else if (tank.map.getValueAt(new Position(x, y)) instanceof Tank) {
                    ((Tank) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((Tank) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((Tank) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                }
                timeline.jumpTo(Duration.ZERO);
                timeline.stop();
                bullet.setVisible(false);
            }
        }
    }
    private void fireDown(){
        y++;
        if(y < tank.map.getSize()){
            if(tank.map.getValueAt(new Position(x, y)).canBulletPass()){
                bullet.setCenterY(bullet.getCenterY() + 20);
            }
            else {
                if (tank.map.getValueAt(new Position(x, y)) instanceof BrickWall) {
                    ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((BrickWall) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                } else if (tank.map.getValueAt(new Position(x, y)) instanceof Tank) {
                    ((Tank) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((Tank) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((Tank) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                }
                timeline.jumpTo(Duration.ZERO);
                timeline.stop();
                bullet.setVisible(false);
            }
        }
    }
    private void fireLeft(){
        x--;
        if(x >= 0){
            if(tank.map.getValueAt(new Position(x, y)).canBulletPass()){
                bullet.setCenterX(bullet.getCenterX() - 20);
            }
            else {
                if (tank.map.getValueAt(new Position(x, y)) instanceof BrickWall) {
                    ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((BrickWall) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                } else if (tank.map.getValueAt(new Position(x, y)) instanceof Tank) {
                    ((Tank) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((Tank) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((Tank) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                }
                timeline.jumpTo(Duration.ZERO);
                timeline.stop();
                bullet.setVisible(false);
            }
        }
    }
    private void fireRight(){
        int x = this.x + 1;
        if(x < tank.map.getSize()){
            if(tank.map.getValueAt(new Position(x, y)).canBulletPass()){
                bullet.setCenterX(bullet.getCenterX() + 20);
                this.x++;
            }
            else {
                if (tank.map.getValueAt(new Position(x, y)) instanceof BrickWall) {
                    ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((BrickWall) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((BrickWall) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                } else if (tank.map.getValueAt(new Position(x, y)) instanceof Tank) {
                    ((Tank) (tank.map.getValueAt(new Position(x, y)))).decreaseHp();
                    if (((Tank) (tank.map.getValueAt(new Position(x, y)))).getHp() == 0) {
                        ((Tank) (tank.map.getValueAt(new Position(x, y)))).image.setVisible(false);
                        tank.map.walls[x][y] = new EmptyWall(new Position(x, y));
                    }
                }
                timeline.jumpTo(Duration.ZERO);
                timeline.stop();
                bullet.setVisible(false);
            }
        }
    }
}
