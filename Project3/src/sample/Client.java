package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

public class Client extends Application {
    DataInputStream fromServer;
    DataOutputStream toServer;
    @Override
    public void start(Stage stage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Socket socket = new Socket("localhost", 11000);
                            fromServer = new DataInputStream(socket.getInputStream());
                            toServer = new DataOutputStream(socket.getOutputStream());
                            Game game = new Game(toServer, fromServer);
                            Timeline z = new Timeline(new KeyFrame(Duration.millis(5000),e->{}));
                            z.setCycleCount(1);
                            z.play();
                            game.startForClients();
                        }
                        catch (IOException e){           e.printStackTrace();}
                        catch (Exception z){z.printStackTrace();}
                    }
                });

            }
        }).start();
    }
    public static void main(String[] args){
        launch(args);
    }
}
