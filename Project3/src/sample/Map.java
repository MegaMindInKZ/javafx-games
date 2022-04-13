package sample;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    Wall[][] walls;
    private int size;
    ArrayList<Tank> players = new ArrayList<>();
    Position[] positions = new Position[4];
    private ImageView tank1 = new ImageView(new Image(new File("D:\\1CourseProjects\\Project3\\src\\sample\\Tank1.jpg").toURI().toString()));
    private ImageView tank2 = new ImageView(new Image(new File("D:\\1CourseProjects\\Project3\\src\\sample\\Tank2.jpg").toURI().toString()));
    private ImageView tank3 = new ImageView(new Image(new File("D:\\1CourseProjects\\Project3\\src\\sample\\Tank3.jpg").toURI().toString()));
    private ImageView tank4 = new ImageView(new Image(new File("D:\\1CourseProjects\\Project3\\src\\sample\\Tank4.jpg").toURI().toString()));

    Map(Scanner input) throws InvalidMapException{
        size = input.nextInt();
        walls = new Wall[size][size];
        if(size == 0){
            throw new InvalidMapException("Map size can not be zero");
        }
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(!input.hasNext()){
                    throw  new InvalidMapException("Not enough map elements");
                }
                char c = input.next().charAt(0);
                if(c == '1'){
                    positions[0] = new Position(j, i);
                    Tank player = new Tank(new Position(j, i), tank1);
                    walls[j][i] = player;
                    players.add(player);
                }
                else if(c == '2'){
                    positions[1] = new Position(j, i);
                    Tank player = new Tank(new Position(j, i), tank2);
                    walls[j][i] = player;
                    players.add(player);
                }
                else if(c == '3'){
                    positions[2] = new Position(j, i);
                    Tank player = new BotPlayer1(new Position(j, i), tank3);
                    walls[j][i] = player;
                    players.add(player);
                }
                else if(c == '4'){
                    positions[3] = new Position(j, i);
                    Tank player = new BotPlayer2(new Position(j, i), tank4);
                    walls[j][i] = player;
                    players.add(player);

                }
                else if(c == 'S'){
                    walls[j][i] = new SteelWall(new Position(j, i));
                }
                else if (c == 'B') {
                    walls[j][i] = new BrickWall(new Position(j, i));
                }
                else if(c == 'T'){
                    walls[j][i] = new Tree(new Position(j, i));
                }
                else if (c == 'W') {
                    walls[j][i] = new Water(new Position(j, i));
                }
                else if(c == '0'){
                    walls[j][i] = new EmptyWall(new Position(j, i));
                }
            }
        }
        setEnemies();

    }
    void setEnemies(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).map = this;
            for(int j = 0; j < players.size(); j++) {
                if (i != j) {
                    players.get(i).enemies.add(players.get(j));
                }

            }
        }
    }
    Wall getValueAt(Position pos){
        return walls[pos.getX()][pos.getY()];
    }
    int getSize(){
        return size;
    }
    void setForClients(){
        Tank tank2 = new Tank(positions[1], this.tank2);
        Tank tank3 = new Tank(positions[2], this.tank3);
        Tank tank4 = new Tank(positions[3], this.tank4);
        tank2.map = this;
        tank3.map = this;
        tank4.map = this;
        players.remove(1);
        players.remove(1);
        players.remove(1);
        players.add(tank2);
        players.add(tank3);
        players.add(tank4);
    }
}
