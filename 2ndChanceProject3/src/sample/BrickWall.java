package sample;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class BrickWall implements Wall {
    private ImageView image;
    private int hp;
    private boolean canBulletPassx;
    private boolean canTankPassx;
    BrickWall(Position pos){
        Image z = new Image((new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\BrickWall.png").toURI().toString()));
        image = new ImageView(z);
        image.setFitWidth(20);
        image.setFitHeight(20);
        image.setX(pos.getX() * 20);
        image.setY(pos.getY() * 20);
        hp = 4;
        canBulletPassx = false;
        canTankPassx = false;

    }
    @Override
    public boolean canBulletPass() {
        return canBulletPassx;
    }
    @Override
    public boolean canTankPass(){
        return canTankPassx;
    }
    @Override
    public boolean canWallBreak() {
        return true;
    }
    @Override
    public ImageView getImage(){
        return image;
    }
    int getHp(){
        return hp;
    }
    void setHp(int hp){
        this.hp = hp;
        if(hp <= 0){
            canTankPassx = true;
            canBulletPassx = true;
        }
    }
}
