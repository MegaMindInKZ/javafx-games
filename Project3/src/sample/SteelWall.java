package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class SteelWall implements Wall {
    private ImageView image;
    SteelWall(Position pos){
        Image z = new Image((new File("D:\\1CourseProjects\\Project3\\src\\sample\\SteelWall.png").toURI().toString()));
        image = new ImageView(z);
        image.setFitWidth(20);
        image.setFitHeight(20);
        image.setX(pos.getX() * 20);
        image.setY(pos.getY() * 20);

    }
    @Override
    public boolean canBulletPass() {
        return false;
    }
    @Override
    public boolean canTankPass(){
        return false;
    }
    @Override
    public boolean canWallBreak() {
        return false;
    }
    @Override
    public ImageView getImage(){
        return image;
    }
}
