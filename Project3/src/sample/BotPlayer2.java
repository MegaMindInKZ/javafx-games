package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class BotPlayer2 extends Tank{
    DataOutputStream toClient;
    boolean hasClient = false;
    public BotPlayer2(Position pos, ImageView image) {
        super(pos, image);
    }
    public void setOutputs(DataOutputStream toClient){
        this.toClient = toClient;
    }
    public void start(){
        if(getHp() > 0) {
            if (whereFire() != 'N') {
                switch (whereFire()) {
                    case 'D':
                        super.direction = 'D';
                        super.image.setRotate(180);
                        super.fire();
                        sendMove('K');
                        break;
                    case 'U':
                        super.direction = 'U';
                        super.image.setRotate(0);
                        super.fire();
                        sendMove('I');
                        break;
                    case 'R':
                        super.direction = 'R';
                        super.image.setRotate(90);
                        super.fire();
                        sendMove('Z');
                        break;
                    case 'L':
                        super.direction = 'L';
                        super.image.setRotate(270);
                        super.fire();
                        sendMove('J');
                        break;
                }
            } else {
                move();
            }
        }
    }
    public void start(Pane pane) {
        if (whereFire() != 'N') {
            switch (whereFire()) {
                case 'D':
                    super.direction = 'D';
                    super.image.setRotate(180);
                    super.fire();
                    sendMove('K');
                    break;
                case 'U':
                    super.direction = 'U';
                    super.image.setRotate(0);
                    super.fire();
                    sendMove('I');
                    break;
                case 'R':
                    super.direction = 'R';
                    super.image.setRotate(90);
                    super.fire();
                    sendMove('J');
                    break;
                case 'L':
                    super.direction = 'L';
                    super.image.setRotate(270);
                    super.fire();
                    sendMove('Z');
                    break;
            }
            try {
                pane.getChildren().add(super.getB().bullet);
            }catch(IllegalArgumentException z){}
        }
        else{
            move();
        }
    }
    private void move(){
        Tank enemy = nearTank();
        int dx = enemy.getPosition().getX() - super.getPosition().getX();
        int dy = enemy.getPosition().getX() - super.getPosition().getX();
        char b = whereMove(dx,dy);
        switch (b){
            case 'R': moveRight();
                sendMove(b);
                break;
            case 'D': moveDown();
                sendMove(b);
                break;
            case 'U': moveUp();
                sendMove(b);
                break;
            case 'L': moveLeft();
                sendMove(b);
                break;
        }
    }
    public synchronized void sendMove(char c){
        if(hasClient) {
            try {
                toClient.writeChar('3');
                toClient.writeChar(c);
            } catch (IOException e) {e.printStackTrace();
            }
        }
    }
    private Tank nearTank(){
        int x = 0;
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getHp() <= 0) {
                continue;
            }
            x++;
        }
        Path[] paths = new Path[x];
        int y = 0;
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getHp() <= 0) {
                continue;
            }
            paths[y] = new Path(this, enemies.get(i));
            y++;
        }
        Arrays.sort(paths);
        return paths[paths.length - 1].tank2;
    }
    private char whereMove(int x, int y){
        char c = '0';
        int xy = Math.abs(x) - Math.abs(y);
        if(x == 0){
            if(map.getSize() > super.getPosition().getX() + 1 && map.getValueAt(new Position(super.getPosition().getX() + 1, super.getPosition().getY())).canTankPass()){
                c = 'R';
            }
            else if(0 <= super.getPosition().getX() - 1 && map.getValueAt(new Position(super.getPosition().getX() - 1, super.getPosition().getY())).canTankPass()){
                c = 'L';
            }

        }
        else if(y == 0){
            if(map.getSize() > super.getPosition().getY() + 1 &&map.getValueAt(new Position(super.getPosition().getX(), super.getPosition().getY() + 1)).canTankPass()){
                c = 'D';
            }
            else if(0 <= super.getPosition().getY() - 1 && map.getValueAt(new Position(super.getPosition().getX(), super.getPosition().getY() - 1)).canTankPass()){
                c = 'U';
            }
        }
        else if(xy >= 0){
            if(y > 0 && map.getSize() > super.getPosition().getY() + 1 && map.getValueAt(new Position(super.getPosition().getX(), super.getPosition().getY() + 1)).canTankPass()){
                c = 'D';
            }
            else if(y < 0 && 0 <= super.getPosition().getY() - 1 && map.getValueAt(new Position(super.getPosition().getX(), super.getPosition().getY() - 1)).canTankPass()){
                c = 'U';
            }
            else if(x > 0 && map.getSize() > super.getPosition().getX() + 1 && map.getValueAt(new Position(super.getPosition().getX() + 1, super.getPosition().getY())).canTankPass()){
                c = 'R';
            }
            else if(0 <= super.getPosition().getX() - 1){
                c = 'L';
            }

        }
        else if(xy < 0){
            if(x > 0 && map.getSize() > super.getPosition().getX() + 1 && map.getValueAt(new Position(super.getPosition().getX() + 1, super.getPosition().getY())).canTankPass()){
                c = 'R';
            }
            else if(x < 0 && 0 <= super.getPosition().getX() - 1 && map.getValueAt(new Position(super.getPosition().getX() - 1, super.getPosition().getY())).canTankPass()){
                c = 'L';
            }
            else if(y > 0 && map.getSize() > super.getPosition().getY() + 1 && map.getValueAt(new Position(super.getPosition().getX(), super.getPosition().getY() + 1)).canTankPass()){
                c = 'D';
            }
            else if(y < 0 && 0 <= super.getPosition().getY() - 1 && map.getValueAt(new Position(super.getPosition().getX(), super.getPosition().getY() - 1)).canTankPass()){
                c = 'U';
            }

        }
        return c;
    }

    private char whereFire(){
        char c;
        if(hasTankDownSide()){ c = 'D'; }
        else if(hasTankLeftSide()){ c = 'L'; }
        else if(hasTankRightSide()){ c = 'R'; }
        else if(hasTankUpSide()){ c = 'U'; }
        else{ c = 'N'; }
        return c;
    }
    private boolean hasTankRightSide(){
        boolean has = false;
        for(int i = super.getPosition().getX() + 1; i < super.map.walls.length; i++){
            if(super.map.getValueAt(new Position(i, super.getPosition().getY())) instanceof Tank){
                has = true;
                break;
            }
            else if(!super.map.getValueAt(new Position(i, super.getPosition().getY())).canBulletPass()){
                has = false;
                break;
            }
        }
        return has;
    }
    private boolean hasTankLeftSide(){
        boolean has = false;
        for(int i = super.getPosition().getX() - 1; i >= 0; i--){
            if(super.map.getValueAt(new Position(i, super.getPosition().getY())) instanceof Tank){
                has = true;
                break;
            }
            else if(!super.map.getValueAt(new Position(i, super.getPosition().getY())).canBulletPass()){
                has = false;
                break;
            }
        }
        return has;
    }
    private boolean hasTankUpSide(){
        boolean has = false;
        for(int i = super.getPosition().getY() - 1; i >= 0; i--){
            if(super.map.getValueAt(new Position(super.getPosition().getX(), i)) instanceof Tank){
                has = true;
                break;
            }
            else if(!super.map.getValueAt(new Position(super.getPosition().getX(), i)).canBulletPass()){
                has = false;
                break;
            }
        }
        return has;
    }
    private boolean hasTankDownSide(){
        boolean has = false;
        for(int i = super.getPosition().getY() + 1; i < super.map.getSize(); i++){
            if((super.map.getValueAt(new Position(super.getPosition().getX(), i)) instanceof Tank)){
                has = true;
                break;
            }
            else if(!super.map.getValueAt(new Position(super.getPosition().getX(), i)).canBulletPass()){
                has = false;
                break;
            }
        }
        return has;
    }
}
