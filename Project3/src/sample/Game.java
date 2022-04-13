package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Scanner;

public class Game {
    Scanner in = new Scanner(new File("C:\\Users\\Admin\\Project3\\src\\sample\\Map2.txt"));
    Pane pane;
    Map map;
    DataOutputStream toServer;
    DataInputStream fromServer;
    DataOutputStream toClient;
    DataInputStream fromClient;
    long speed, speed1 = 10000;
    //Game constructor for Client class
    Game(DataOutputStream toServer, DataInputStream fromServer) throws Exception {
        this.toServer = toServer;
        this.fromServer = fromServer;
        pane = new Pane();
        map = new Map(in);
        map.setForClients();
        draw();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(pane));
                        newStage.show();
                        pane.requestFocus();

                    }
                });
            }
        }).start();
    }
    //Game constructor for Server class
    Game(DataInputStream fromClient, DataOutputStream toClient) throws Exception {
        this.fromClient = fromClient;
        this.toClient = toClient;
        map = new Map(in);
        pane = new Pane();
        draw();
        if(map.players.get(2) instanceof BotPlayer1){
            ((BotPlayer1)(map.players.get(2))).setOutputs(toClient);
            ((BotPlayer1)(map.players.get(2))).hasClient = true;
        }
        if(map.players.get(3) instanceof BotPlayer2){
            ((BotPlayer2)(map.players.get(3))).setOutputs(toClient);
            ((BotPlayer2)(map.players.get(3))).hasClient = true;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(pane));
                        newStage.show();
                        pane.requestFocus();
                    }
                });
            }
        }).start();
    }
    //
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
    public void startForClients() throws IOException {
        map.players.get(2).moveUp();
        threadForOthersForClient().start();
        threadForPlayerForClient().start();
    }
    public void startForServer() throws IOException{
        threadForClientForServer().start();
        threadForPlayerForServer().start();
        threadForBot1().start();
        threadForBot2().start();

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
    Thread threadForClientForServer(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isAlive(1)) {
                        char c = fromClient.readChar();
                        switch (c) {
                            case 'R':
                                map.players.get(1).moveRight();
                                break;
                            case 'L':
                                map.players.get(1).moveLeft();
                                break;
                            case 'D':
                                map.players.get(1).moveDown();
                                break;
                            case 'U':
                                map.players.get(1).moveUp();
                                break;
                            case 'F':
                                map.players.get(1).fire();
                                if (!pane.getChildren().contains(map.players.get(1).getB().bullet)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            pane.getChildren().add(map.players.get(1).getB().bullet);
                                        }
                                    });
                                }
                                break;
                        }
                    }
                } catch (IOException x) {
                    x.printStackTrace();
                }
            }
        });
    }
    Thread threadForBot1(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isAlive(2)) {
                        synchronized (toClient) {
                            ((BotPlayer1) (map.players.get(2))).start();
                            if (map.players.get(2) instanceof BotPlayer1) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(!pane.getChildren().contains(map.players.get(2).getB().bullet))
                                            pane.getChildren().add(map.players.get(2).getB().bullet);
                                    }
                                });
                            }
                        }
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){}
            }
        });
    }
    Thread threadForBot2(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isAlive(3)) {
                        synchronized (map.players) {
                            ((BotPlayer2) (map.players.get(3))).start();
                            if (map.players.get(3) instanceof BotPlayer2) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(!pane.getChildren().contains(map.players.get(3).getB().bullet))
                                            pane.getChildren().add(map.players.get(3).getB().bullet);
                                    }
                                });
                            }
                        }
                        Thread.sleep(500);
                    }
                }catch (InterruptedException e){}
            }
        });
    }
    Thread threadForOthersForClient() {
        long speed, speed1 = 10000;
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (fromServer) {
                        while (true) {
                            try {
                                char c1;
                                c1 = fromServer.readChar();
                                System.out.print(c1);
                                Thread x;
                                if (c1 + "" != "") {
                                    x = threadForPlayersForClient(c1);
                                    x.start();
                                    x.join();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Thread.sleep(10);
                        }
                    }
                }catch (InterruptedException e){e.printStackTrace();}
                catch (Exception e){e.printStackTrace();}
            }
        });
    }
    Thread threadForPlayersForClient(char c) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                char c2;
                int c1 = Integer.parseInt(c + "");
                if (isAlive(c1)) {
                    try {
                        c2 = fromServer.readChar();
                        System.out.println(c2);
                        System.out.println("x");
                        switch (c2) {
                            case 'R':
                                map.players.get(c1).moveRight();
                                break;
                            case 'L':
                                map.players.get(c1).moveLeft();
                                break;
                            case 'D':
                                map.players.get(c1).moveDown();
                                break;
                            case 'U':
                                map.players.get(c1).moveUp();
                                break;
                            case 'F':
                                map.players.get(c1).fire();
                                if (!pane.getChildren().contains(map.players.get(c1).getB().bullet)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            pane.getChildren().add(map.players.get(c1).getB().bullet);
                                        }
                                    });
                                }
                                break;
                            case 'I':
                                map.players.get(c1).fireUp();
                                if (!pane.getChildren().contains(map.players.get(c1).getB().bullet)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            pane.getChildren().add(map.players.get(c1).getB().bullet);
                                        }
                                    });                                }
                                break;
                            case 'K':
                                map.players.get(c1).fireDown();
                                if (!pane.getChildren().contains(map.players.get(c1).getB().bullet)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            pane.getChildren().add(map.players.get(c1).getB().bullet);
                                        }
                                    });                                }
                                break;
                            case 'Z':
                                map.players.get(c1).fireRight();
                                if (!pane.getChildren().contains(map.players.get(c1).getB().bullet)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            pane.getChildren().add(map.players.get(c1).getB().bullet);
                                        }
                                    });                                }
                                break;
                            case 'J':
                                map.players.get(c1).fireLeft();
                                if (!pane.getChildren().contains(map.players.get(c1).getB().bullet)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            pane.getChildren().add(map.players.get(c1).getB().bullet);
                                        }
                                    });                                }
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    Thread threadForPlayerForClient(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pane.requestFocus();
                        pane.setOnKeyPressed(e -> {
                            synchronized (toServer) {
                                switch (e.getCode()) {
                                    case A:
                                        long w = System.currentTimeMillis();
                                        if (w - speed > 100) {
                                            map.players.get(1).moveLeft();
                                            try {
                                                toServer.writeChar('L');
                                            } catch (IOException m) {
                                            }
                                            ;
                                            speed = w;
                                        }
                                        break;
                                    case S:
                                        long ws = System.currentTimeMillis();
                                        if (ws - speed > 100) {
                                            map.players.get(1).moveDown();
                                            try {
                                                toServer.writeChar('D');
                                            } catch (IOException m) {
                                            }
                                            ;
                                            speed = ws;
                                        }
                                        break;
                                    case D:
                                        long wd = System.currentTimeMillis();
                                        if (wd - speed > 100) {
                                            map.players.get(1).moveRight();
                                            try {
                                                toServer.writeChar('R');
                                            } catch (IOException m) {
                                            }
                                            ;
                                            speed = wd;
                                        }
                                        break;
                                    case W:
                                        long ww = System.currentTimeMillis();
                                        if (ww - speed > 100) {
                                            map.players.get(1).moveUp();
                                            try {
                                                toServer.writeChar('U');
                                            } catch (IOException m) {
                                            }
                                            ;
                                            speed = ww;
                                        }
                                        break;
                                    case F:
                                        map.players.get(1).fire();
                                        try {
                                            toServer.writeChar('F');
                                        } catch (IOException m) {
                                        }
                                        ;
                                        try {
                                            pane.getChildren().add(map.players.get(1).getB().bullet);
                                        } catch (IllegalArgumentException z) {
                                        }
                                        break;
                                }
                            }
                        });
                    }
                });

            }
        });
    }
    Thread threadForPlayerForServer(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                            pane.requestFocus();
                            pane.setOnKeyPressed(e -> {
                                synchronized (toClient) {
                                    switch (e.getCode()) {
                                        case A:
                                            long w = System.currentTimeMillis();
                                            if (w - speed1 > 100) {
                                                map.players.get(0).moveLeft();
                                                try {
                                                    toClient.writeChar('0');
                                                    toClient.writeChar('L');
                                                } catch (IOException m) {
                                                }
                                                ;
                                                speed1 = w;
                                            }
                                            break;
                                        case S:
                                            long ws = System.currentTimeMillis();
                                            if (ws - speed1 > 100) {
                                                map.players.get(0).moveDown();
                                                try {
                                                    toClient.writeChar('0');
                                                    toClient.writeChar('D');
                                                } catch (IOException m) {
                                                }
                                                ;
                                                speed1 = ws;
                                            }
                                            break;
                                        case D:
                                            long wd = System.currentTimeMillis();
                                            if (wd - speed1 > 100) {
                                                map.players.get(0).moveRight();
                                                try {
                                                    toClient.writeChar('0');
                                                    toClient.writeChar('R');
                                                } catch (IOException m) {
                                                }
                                                ;
                                                speed1 = wd;
                                            }
                                            break;
                                        case W:
                                            long ww = System.currentTimeMillis();
                                            if (ww - speed1 > 100) {
                                                map.players.get(0).moveUp();
                                                try {
                                                    toClient.writeChar('0');
                                                    toClient.writeChar('U');
                                                } catch (IOException m) {
                                                }
                                                ;
                                                speed1 = ww;
                                            }
                                            break;
                                        case F:
                                            map.players.get(0).fire();
                                            try {
                                                toClient.writeChar('0');
                                                toClient.writeChar('F');
                                            } catch (IOException m) {
                                            }
                                            ;
                                            try {
                                                pane.getChildren().add(map.players.get(0).getB().bullet);
                                            } catch (IllegalArgumentException z) {
                                            }
                                            break;
                                    }
                                }
                            });
                    }
                });

            }
        });
    }
    boolean isAlive(int x){
        return map.players.get(x).getHp() > 0;
    }
}

