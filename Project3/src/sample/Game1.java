package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game1{
    long speed, speed1 = 10000;
    boolean player1;
    boolean player2;
    boolean p1L, p1R, p1D, p1U, p1F;
    boolean p2L, p2R, p2D, p2U, p2F;
    Scanner in = new Scanner(new File("D:\\1CourseProjects\\Project3\\src\\sample\\Map2.txt"));
    Map map;
    Pane pane;
    Game1() throws FileNotFoundException, InvalidMapException {
        map = new Map(in);
        pane = new Pane();
        draw();
        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
        map.players.get(0).image.requestFocus();
        map.players.get(1).image.requestFocus();
        threadForPlayers().start();
        //threadForPlayer2().start();ww
        threadForBot2().start();
        threadForBot1().start();


    }
    void draw(){
        for (int i = 0; i < map.players.size(); i++) {
            pane.getChildren().add(map.players.get(i).image);
        }
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getValueAt(new Position(i, j)) instanceof Tank) {
                } else {
                    if (map.getValueAt(new Position(i, j)) instanceof EmptyWall) {
                    } else {
                        pane.getChildren().add(map.getValueAt(new Position(i, j)).getImage());
                    }
                }
            }
        }
        setPaneForHp();
    }
    void setPaneForHp(){
        Label[] labels = new Label[4];
        for(int i = 0; i < map.players.size(); i++){
            String s = "";
            switch (i){
                case 0:
                    s = "Player" + i + "(GREEN):";
                    labels[i] = new Label(s, map.players.get(i).paneForHp);
                    labels[i].setTextFill(Color.GREEN);
                    break;
                case 1:
                    s = "Player" + i + "(RED):";
                    labels[i] = new Label(s, map.players.get(i).paneForHp);
                    labels[i].setTextFill(Color.RED);
                    break;
                case 2:
                    s = "Player" + i + "(GREY):";
                    labels[i] = new Label(s, map.players.get(i).paneForHp);
                    labels[i].setTextFill(Color.GREY);
                    break;
                case 3:
                    s = "Player" + i + "(YELLOW):";
                    labels[i] = new Label(s, map.players.get(i).paneForHp);
                    labels[i].setTextFill(Color.YELLOW);
                    break;
            }
            labels[i].setContentDisplay(ContentDisplay.BOTTOM);
            labels[i].setLayoutX(0);
        }
        VBox vBox = new VBox();
        vBox.setLayoutX(map.getSize() * 20);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(5 , 5 , 5 , 5));
        vBox.getChildren().addAll(labels);
        pane.getChildren().add(vBox);
        pane.getChildren().add(new Line(map.getSize() * 20, 0, map.getSize() * 20, map.getSize() * 20));
    }
    Thread threadForPlayers(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pane.requestFocus();
                        pane.setOnKeyPressed(e ->{
                            if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.SPACE) {
                                player2 = true;
                                if(e.getCode() == KeyCode.LEFT) p2L = true;
                                if(e.getCode() == KeyCode.RIGHT) p2R = true;
                                if(e.getCode() == KeyCode.DOWN) p2D = true;
                                if(e.getCode() == KeyCode.UP) p2U = true;
                                if(e.getCode() == KeyCode.SPACE) p2F = true;
                            }
                            if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.A || e.getCode() == KeyCode.W || e.getCode() == KeyCode.S || e.getCode() == KeyCode.F) {
                                player1 = true;
                                if(e.getCode() == KeyCode.A) p1L = true;
                                if(e.getCode() == KeyCode.D) p1R = true;
                                if(e.getCode() == KeyCode.S) p1D = true;
                                if(e.getCode() == KeyCode.W) p1U = true;
                                if(e.getCode() == KeyCode.F) p1F = true;
                            }
                            if(player1){
                                if(p1L){
                                    long wl1 = System.currentTimeMillis();
                                    if (wl1 - speed1 > 100 && map.players.get(0).getHp() > 0) {
                                        map.players.get(0).moveLeft();
                                        speed1 = wl1;
                                    }
                                }
                                else if(p1R){
                                    long wr1 = System.currentTimeMillis();
                                    if (wr1 - speed1 > 100 && map.players.get(0).getHp() > 0) {
                                        map.players.get(0).moveRight();
                                        speed1 = wr1;
                                    }
                                }
                                else if(p1U){
                                    long wu1 = System.currentTimeMillis();
                                    if (wu1 - speed1 > 100 && map.players.get(0).getHp() > 0) {
                                        map.players.get(0).moveUp();
                                        speed1 = wu1;
                                    }
                                }
                                else if(p1D){
                                    long ws1 = System.currentTimeMillis();
                                    if (ws1 - speed1 > 100 && map.players.get(0).getHp() > 0) {
                                        map.players.get(0).moveDown();
                                        speed1 = ws1;
                                    }
                                }
                                else if(p1F){
                                    long ws1 = System.currentTimeMillis();
                                    if (ws1 - speed1 > 100 && map.players.get(0).getHp() > 0) {
                                        map.players.get(0).fire();
                                        if (!pane.getChildren().contains(map.players.get(0).getB().bullet)) {
                                            pane.getChildren().add(map.players.get(0).getB().bullet);
                                        }
                                        speed1 = ws1;
                                    }
                                }
                            }
                            if(player2){
                                if(p2L){
                                    long wl1 = System.currentTimeMillis();
                                    if (wl1 - speed > 100 && map.players.get(1).getHp() > 0) {
                                        map.players.get(1).moveLeft();
                                        speed = wl1;
                                    }
                                }
                                else if(p2R){
                                    long wr1 = System.currentTimeMillis();
                                    if (wr1 - speed > 100 && map.players.get(1).getHp() > 0) {
                                        map.players.get(1).moveRight();
                                        speed = wr1;
                                    }
                                }
                                else if(p2U){
                                    long wu1 = System.currentTimeMillis();
                                    if (wu1 - speed > 100 && map.players.get(1).getHp() > 0) {
                                        map.players.get(1).moveUp();
                                        speed = wu1;
                                    }
                                }
                                else if(p2D){
                                    long ws1 = System.currentTimeMillis();
                                    if (ws1 - speed > 100 && map.players.get(1).getHp() > 0) {
                                        map.players.get(1).moveDown();
                                        speed = ws1;
                                    }
                                }
                                else if(p2F){
                                    long ws1 = System.currentTimeMillis();
                                    if (ws1 - speed > 100 && map.players.get(1).getHp() > 0) {
                                        map.players.get(1).fire();
                                        if (!pane.getChildren().contains(map.players.get(1).getB().bullet)) {
                                            pane.getChildren().add(map.players.get(1).getB().bullet);
                                            ws1 = speed;
                                        }
                                    }
                                }
                            }
                        });
                        pane.setOnKeyReleased(e ->{
                            if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.SPACE) {
                                player2 = false;
                                if(e.getCode() == KeyCode.LEFT) p2L = false;
                                if(e.getCode() == KeyCode.RIGHT) p2R = false;
                                if(e.getCode() == KeyCode.DOWN) p2D = false;
                                if(e.getCode() == KeyCode.UP) p2U = false;
                                if(e.getCode() == KeyCode.P) p2F = false;
                            }
                            if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.A || e.getCode() == KeyCode.W || e.getCode() == KeyCode.S || e.getCode() == KeyCode.F) {
                                player1 = false;
                                if(e.getCode() == KeyCode.A) p1L = false;
                                if(e.getCode() == KeyCode.D) p1R = false;
                                if(e.getCode() == KeyCode.S) p1D = false;
                                if(e.getCode() == KeyCode.W) p1U = false;
                                if(e.getCode() == KeyCode.F) p1F = false;
                            }
                        });

                    }
                });
            }
        });
    }

    Thread threadForBot1(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (map.players.get(2).getHp() > 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(map.players.get(2) instanceof BotPlayer1){
                                    ((BotPlayer1)map.players.get(2)).start(pane);
                                }
                            }
                        });
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){e.printStackTrace();}
            }
        });
    }
    Thread threadForBot2(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (map.players.get(3).getHp() > 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(map.players.get(3) instanceof BotPlayer2){
                                    ((BotPlayer2)map.players.get(3)).start(pane);
                                }
                            }
                        });
                        Thread.sleep(500);
                    }
                }catch (InterruptedException e){e.printStackTrace();}
            }
        });
    }
}
