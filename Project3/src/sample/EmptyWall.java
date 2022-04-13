package sample;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;

public class EmptyWall implements Wall {
    EmptyWall(Position pos){

    }
    @Override
    public boolean canTankPass() {
        return true;
    }

    @Override
    public boolean canBulletPass() {
        return true;
    }

    @Override
    public boolean canWallBreak() {
        return false;
    }

    @Override
    public ImageView getImage() {
        return null;
    }
}
