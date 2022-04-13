package sample;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Bullet {
    private long time;
    Circle bullet;
    PathTransition pt;
    int yt;
    int xt;
    Bullet(int x1, int y1, int x2, int y2){
        time = System.currentTimeMillis();
        Line path = new Line(x1, y1, x2, y2);
        bullet = new Circle();
        bullet.setStroke(Color.BLACK);
        bullet.setFill(Color.BLACK);
        bullet.setRadius(1.25);
        pt = new PathTransition();
        pt.setDuration(Duration.millis(5 * Math.abs((x1 - x2) + y1 - y2)));
        pt.setPath(path);
        pt.setNode(bullet);
        pt.setAutoReverse(false);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);
        xt = -1;
        yt = -1;
    }

    public Circle getBullet() {
        return bullet;
    }

    public PathTransition getPt() {
        return pt;
    }

    public long getTime() {
        return time;
    }

    public int getXt() {
        return xt;
    }

    public int getYt() {
        return yt;
    }
}
