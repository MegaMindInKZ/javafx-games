package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class Tank extends MyPlayer {
    private Position pos;
    private Map map;
    private ImageView image;
    private char direction;
    private Bullet b;
    private int hp;
    int c = 0;
    long time = 10000;
    boolean z = true;
    Tank(Position pos) throws InvalidMapException{
        hp = 3;
        Image k = new Image((new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\dsBuffer.jpg").toURI().toString()));
        image = new ImageView(k);
        this.pos = pos;
        image.setX(pos.getX() * 20);
        image.setY(pos.getY() * 20);
        image.setFitHeight(20);
        image.setFitWidth(20);
        direction = 'U';
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
            pos.setY(pos.getY() - 1);
            image.setY(pos.getY() * 20);
            System.out.println("UP!");

        }
        else{
            System.out.println("Invalid Position!");
        }

    }
    @Override
    public void moveDown(){
        direction = 'D';
        image.setRotate(180);
        if(canMove(new Position(pos.getX(), (pos.getY() + 1)))){
            pos.setY(pos.getY() + 1);
            image.setY(pos.getY() * 20);
            System.out.println("DOWN!");

        }
        else{
            System.out.println("Invalid Position!");
        }

    }
    @Override
    public void moveRight(){
        direction = 'R';
        image.setRotate(90);;
        if(canMove(new Position((pos.getX() + 1), pos.getY()))){
            pos.setX(pos.getX() + 1);
            image.setX(pos.getX() * 20);
            System.out.println("Right!");
        }
        else {
            System.out.println("Invalid Position!");
        }

    }
    @Override
    public void moveLeft(){
        direction = 'L';
        image.setRotate(270);
        if(canMove(new Position((pos.getX() - 1), pos.getY()))){
            pos.setX(pos.getX() - 1);
            image.setX(pos.getX() * 20);
            System.out.println("LEFT!");
        }
        else{
            System.out.println("Invalid Position!");
        }
    }
    public boolean canMove(Position p){
        if(p.getY() >= map.getSize() || p.getX() >= map.getSize() || p.getY() < 0 || p.getX() < 0){
            return false;
        }
        else{
            if(map.getValueAt(p) == null){
                return true;
            }
            if(map.getValueAt(p).canTankPass()){
                return true;
            }
            else{
                return false;
            }
        }
    }
    void fire() {
        long s = System.currentTimeMillis();
        if(s - time > 1000) {
            System.out.println("fire!");
            int x1;
            int y1;
            int x2;
            int y2;
            int xt;
            int yt;
            if (direction == 'U') {
                x1 = pos.getX() * 20 + 10;
                y1 = pos.getY() * 20;
                x2 = x1 + 1;
                y2 = 0;
                xt = -1;
                yt = -1;


                for (int j = pos.getY(); j >= 0; j--) {
                    if (map.getValueAt(new Position(pos.getX(), j)) != null && !map.getValueAt(new Position(pos.getX(), j)).canBulletPass()) {
                        if (map.getValueAt(new Position(pos.getX(), j)) instanceof BrickWall) {
                            yt = j;
                            xt = pos.getX();
                            if (((BrickWall) (map.getValueAt(new Position(pos.getX(), j)))).getHp() != 0) {
                                ((BrickWall) (map.getValueAt(new Position(pos.getX(), j)))).setHp(((BrickWall) map.getValueAt(new Position(pos.getX(), j))).getHp() - 1);
                            }
                        }
                        y2 = (j + 1) * 20;
                        break;
                    }
                }

            } else if (direction == 'D') {
                int x = pos.getX();
                int y = pos.getY();
                x1 = x * 20 + 10;
                y1 = (y + 1) * 20;
                x2 = x1 + 1;
                y2 = (map.getSize()) * 20;
                xt = -1;
                yt = -1;
                for (int j = y; j < map.getSize(); j++) {
                    if (map.getValueAt(new Position(x, j)) != null && !map.getValueAt(new Position(x, j)).canBulletPass()) {
                        if (map.getValueAt(new Position(x, j)) instanceof BrickWall) {
                            yt = j;
                            xt = x;
                            if (((BrickWall) (map.getValueAt((new Position(x, j))))).getHp() != 0) {
                                ((BrickWall) (map.getValueAt(new Position(x, j)))).setHp(((BrickWall) map.getValueAt(new Position(x, j))).getHp() - 1);
                            }
                        }
                        y2 = j * 20;
                        break;
                    }
                }
            } else if (direction == 'R') {
                int x = pos.getX();
                int y = pos.getY();
                x1 = (x + 1) * 20;
                y1 = y * 20 + 10;
                y2 = y1 + 1;
                x2 = (map.getSize()) * 20;
                yt = -1;
                xt = -1;
                for (int i = x; i < map.getSize(); i++) {
                    if (map.getValueAt(new Position(i, y)) != null && !map.getValueAt(new Position(i, y)).canBulletPass()) {
                        if (map.getValueAt(new Position(i, y)) instanceof BrickWall) {
                            xt = i;
                            yt = y;
                            if (((BrickWall) (map.getValueAt(new Position(i, y)))).getHp() != 0) {
                                ((BrickWall) (map.getValueAt(new Position(i, y)))).setHp(((BrickWall) map.getValueAt(new Position(i, y))).getHp() - 1);
                            }
                        }
                        x2 = i * 20;
                        break;
                    }
                }
            } else {
                int x = pos.getX();
                int y = pos.getY();
                x1 = x * 20;
                y1 = y * 20 + 10;
                y2 = y1 + 1;
                x2 = 0;
                xt = -1;
                yt = -1;
                for (int i = x; i >= 0; i--) {
                    if (map.getValueAt(new Position(i, y)) != null && !map.getValueAt(new Position(i, y)).canBulletPass()) {
                        if (map.getValueAt(new Position(i, y)) instanceof BrickWall) {
                            xt = i;
                            yt = y;
                            if (((BrickWall) (map.getValueAt(new Position(i, y)))).getHp() != 0) {
                                ((BrickWall) (map.getValueAt(new Position(i, y)))).setHp(((BrickWall) map.getValueAt(new Position(i, y))).getHp() - 1);
                            }
                        }
                        x2 = (i + 1) * 20;
                        break;
                    }
                }
            }
            b = new Bullet(x1, y1, x2, y2);
            z = true;
            b.yt = yt;
            b.xt = xt;
            c++;
            time = s;
        }
        else{
            z = false;
        }
    }
    int getHp(){
        return hp;
    }
    void setHp(int hp){
        this.hp = hp;
    }
    Bullet getB(){
        return b;
    }
    Map getMap(){
        return map;
    }
    ImageView getImage(){
        return image;
    }

}
