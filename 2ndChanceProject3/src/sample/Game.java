package sample;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    long speed = 10000;
    Map map;
    ArrayList<Tank> players = new ArrayList<>();
    Game(Scanner input) throws InvalidMapException {
        map = new Map(input);
        Tank newPlayer = new Tank(map.getTankLocation());
        players.add(newPlayer);
        newPlayer.setMap(map);
        Pane p1 = new Pane();
        ImageView[][] images = new ImageView[map.getSize()][map.getSize()];
        Image hpi = new Image((new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\HP.png").toURI().toString()));
        ImageView hp1 = new ImageView(hpi);
        hp1.setFitHeight(20);
        hp1.setFitWidth(20);
        hp1.setX(0);
        ImageView hp2 = new ImageView(hpi);
        hp2.setFitHeight(20);
        hp2.setFitWidth(20);
        hp2.setX(20);
        ImageView hp3 = new ImageView(hpi);
        hp3.setFitHeight(20);
        hp3.setFitWidth(20);
        hp3.setX(40);
        Image bi = new Image((new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\black_background.png")).toURI().toString());
        ImageView background = new ImageView(bi);
        background.setFitWidth(60);
        background.setFitHeight(map.getSize() * 20);
        background.setY(10);
        p1.getChildren().add(background);
        p1.getChildren().add(hp1);
        p1.getChildren().add(hp2);
        p1.getChildren().add(hp3);
        Pane paneForMap = new Pane();
        paneForMap.getChildren().add(newPlayer.getImage());
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if(map.getValueAt(new Position(i, j)) != null) {
                    images[i][j] = map.walls[i][j].getImage();
                    paneForMap.getChildren().add(images[i][j]);
                }
            }
        }
        paneForMap.setMaxHeight(map.getSize() * 20);
        paneForMap.setMaxWidth(map.getSize() * 20);
        BorderPane newPane = new BorderPane();
        newPane.setLeft(paneForMap);
        newPane.setRight(p1);
        newPlayer.getImage().setOnKeyPressed(e -> {
            System.out.println("x");
            long s = System.currentTimeMillis();
            if(s - speed > 100) {
                switch (e.getCode()) {
                    case W:
                        newPlayer.moveUp();
                        break;
                    case S:
                        newPlayer.moveDown();
                        break;
                    case A:
                        newPlayer.moveLeft();
                        break;
                    case D:
                        newPlayer.moveRight();
                        break;
                    case SPACE:
                        newPlayer.fire();
                        if (newPlayer.z) {
                            Circle x = newPlayer.getB().bullet;
                            paneForMap.getChildren().add(x);
                            newPlayer.getB().pt.play();
                            newPlayer.getB().pt.setOnFinished(wtd -> {
                                paneForMap.getChildren().remove(x);
                                if (newPlayer.getB().xt != -1 || newPlayer.getB().yt != -1) {
                                    if (map.getValueAt(new Position(newPlayer.getB().getXt(), newPlayer.getB().getYt())) instanceof BrickWall) {
                                        if (((BrickWall) (map.getValueAt(new Position(newPlayer.getB().getXt(), newPlayer.getB().getYt())))).getHp() <= 0) {
                                            paneForMap.getChildren().remove(images[newPlayer.getB().xt][newPlayer.getB().yt]);
                                        }
                                    }
                                }
                            });
                        }
                }
                speed = s;
            }
        });
        Scene newScene = new Scene(newPane, map.getSize() * 20 + 60,map.getSize() * 20);
        Stage newStage = new Stage();
        newStage.getIcons().add(new Image(new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\586f7f34dd3b5.jpg").toURI().toString()));
        newStage.setScene(newScene);
        newStage.show();
        newPlayer.getImage().requestFocus();


    }
    void addPlayer(Tank x){
        players.add(x);
    }
}
