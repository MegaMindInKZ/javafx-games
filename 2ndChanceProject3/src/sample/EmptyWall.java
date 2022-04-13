package sample;

import javafx.scene.image.ImageView;

public class EmptyWall implements Wall{
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
        return true;
    }

    @Override
    public ImageView getImage() {
        return null;
    }
}
