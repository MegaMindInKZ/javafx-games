package sample;

import javafx.scene.image.ImageView;

public interface Wall {
    abstract boolean canTankPass();
    abstract boolean canBulletPass();
    abstract boolean canWallBreak();
    ImageView getImage();

}
