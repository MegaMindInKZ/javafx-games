package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    DataOutputStream toClient;
    DataInputStream fromClient;
    Game game;
    @Override
    public void start(Stage stage) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket serverSocket = new ServerSocket(11000);
                    Socket socket1 = serverSocket.accept();
                    toClient = new DataOutputStream(socket1.getOutputStream());
                    fromClient = new DataInputStream(socket1.getInputStream());
                    game = new Game(fromClient, toClient);
                    Timeline z = new Timeline(new KeyFrame(Duration.millis(5000), e->{}));
                    z.setCycleCount(1);
                    z.play();
                    game.startForServer();
                }
                catch (IOException e){           }
                catch (Exception z){
                    try {
                        game.startForServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    public static void main(String[] args){
        launch(args);
    }
}
