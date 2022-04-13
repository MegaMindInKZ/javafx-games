package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Tree implements Wall{
    private ImageView image;
    Tree(Position pos){
        Image z = new Image((new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\Tree.png").toURI().toString()));
        image = new ImageView(z);
        image.setFitWidth(20);
        image.setFitHeight(20);
        image.setX(pos.getX() * 20);
        image.setY(pos.getY() * 20);

    }
    @Override
    public boolean canBulletPass() {
        return true;
    }
    @Override
    public boolean canTankPass(){
        return true;
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
