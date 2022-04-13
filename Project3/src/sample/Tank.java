package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Tank extends MyPlayer implements Wall{
    private Position pos;
    Map map;
    ImageView image;
    char direction;
    private Bullet b;
    private int hp;
    int c = 0;
    long time = 10000;
    boolean z = true;
    long w = 10000;
    Pane paneForHp;
    ImageView imageView1 = new ImageView(new Image(new File("D:\\1CourseProjects\\Project3\\src\\sample\\HP.png").toURI().toString()));
    ImageView imageView2 = new ImageView(new Image(new File("D:\\1CourseProjects\\Project3\\src\\sample\\HP.png").toURI().toString()));
    ImageView imageView3 = new ImageView(new Image(new File("D:\\1CourseProjects\\Project3\\src\\sample\\HP.png").toURI().toString()));
    ArrayList<Tank> enemies = new ArrayList<>();
    Tank(Position pos) throws InvalidMapException{
        hp = 3;
        Image k = new Image((new File("C:\\Users\\Admin\\Project3\\src\\sample\\Tank1.jpg").toURI().toString()));
        image = new ImageView(k);
        this.pos = pos;
        image.setX(pos.getX() * 20);
        image.setY(pos.getY() * 20);
        image.setFitHeight(20);
        image.setFitWidth(20);
        direction = 'U';
    }
    Tank(Position pos, ImageView image){
        this.image = image;
        hp = 3;
        this.pos = pos;
        image.setX(pos.getX() * 20);
        image.setY(pos.getY() * 20);
        image.setFitHeight(20);
        image.setFitWidth(20);
        direction = 'U';
        setPaneForHp();
    }
    @Override
    public void setMap(Map map){
        this.map = map;
    }

    @Override
    public void moveUp() {
        direction = 'U';
        image.setRotate(0);
        if(canMove(new Position(pos.getX(), (pos.getY()) - 1))){
            map.walls[pos.getX()][pos.getY()] = new EmptyWall(pos);
            map.walls[pos.getX()][pos.getY() - 1] = this;
            pos.setY(pos.getY() - 1);
            image.setY(pos.getY() * 20);
        }
        else{
        }

    }
    @Override
    public void moveDown(){
        direction = 'D';
        image.setRotate(180);
        if(canMove(new Position(pos.getX(), (pos.getY() + 1)))){
            map.walls[pos.getX()][pos.getY()] = new EmptyWall(pos);
            map.walls[pos.getX()][pos.getY() + 1] = this;
            pos.setY(pos.getY() + 1);
            image.setY(pos.getY() * 20);

        }
        else{
        }

    }
    @Override
    public void moveRight(){
        direction = 'R';
        image.setRotate(90);;
        if(canMove(new Position((pos.getX() + 1), pos.getY()))){
            map.walls[pos.getX()][pos.getY()] = new EmptyWall(pos);
            map.walls[pos.getX() + 1][pos.getY()] = this;
            pos.setX(pos.getX() + 1);
            image.setX(pos.getX() * 20);
        }
        else {
        }

    }
    @Override
    public void moveLeft(){
        direction = 'L';
        image.setRotate(270);
        if(canMove(new Position((pos.getX() - 1), pos.getY()))){
            map.walls[pos.getX()][pos.getY()] = new EmptyWall(pos);
            map.walls[pos.getX() - 1][pos.getY()] = this;
            pos.setX(pos.getX() - 1);
            image.setX(pos.getX() * 20);
        }
        else{
        }
    }
    public boolean canMove(Position p){
        long j = System.currentTimeMillis();
        if(j - w > 100) {
            if (p.getY() >= map.getSize() || p.getX() >= map.getSize() || p.getY() < 0 || p.getX() < 0) {
                return false;
            } else {
                return map.getValueAt(p).canTankPass();
            }
        }
        else{
            return false;
        }
    }
    void fire() {
        long s = System.currentTimeMillis();
        if(s - time > 1000) {
            b = new Bullet(this);
            time = s;
            for(int i = 0; i < enemies.size(); i++){
            }
        }
        else{
            z = false;
        }
    }
    int getHp(){
        return hp;
    }
    void decreaseHp(){
        hp--;
        if(hp == 2){
            paneForHp.getChildren().remove(imageView3);
        }
        else if(hp == 1){
            paneForHp.getChildren().remove(imageView2);
        }
        else if(hp == 0){
            paneForHp.getChildren().remove(imageView1);
            Text text = new Text("Player is dead!");
            text.setX(0);
            text.setY(0);
            paneForHp.getChildren().add(text);
        }

    }
    Bullet getB(){
        return b;
    }
    Map getMap(){
        return map;
    }
    @Override
    public ImageView getImage(){
        return image;
    }

    @Override
    public boolean canTankPass() {
        return false;
    }

    @Override
    public boolean canBulletPass() {
        return false;
    }

    @Override
    public boolean canWallBreak() {
        return true;
    }
    public Position getPosition(){
        return pos;
    }
    public void fireUp(){
        direction = 'U';
        image.setRotate(0);
        fire();
    }
    public void fireDown(){
        direction = 'D';
        image.setRotate(180);
        fire();
    }
    public void fireLeft(){
        direction = 'L';
        image.setRotate(270);
        fire();
    }
    public void fireRight() {
        direction = 'R';
        image.setRotate(90);
        fire();
    }
    public void setPaneForHp(){
        paneForHp = new Pane();
        imageView1.setX(0);
        imageView1.setFitHeight(30);
        imageView1.setFitWidth(30);
        imageView1.setY(0);
        imageView2.setX(30);
        imageView2.setFitHeight(30);
        imageView2.setFitWidth(30);
        imageView2.setY(0);
        imageView3.setX(60);
        imageView3.setFitHeight(30);
        imageView3.setFitWidth(30);
        imageView3.setY(0);
        paneForHp.getChildren().addAll(imageView1, imageView2, imageView3);
    }
}
