package sample;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    static String[] x;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        String[] args = getArgs();
        //BorderPane pane = new BorderPane();
        //File file = new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\586f7f34dd3b5.jpg");
        //Image image = new Image(file.toURI().toString());
        //ImageView imageView = new ImageView(image);
        // pane.setPadding(new Insets(5, 5 , 5, 5));
        //pane.setBottom(imageView);
        //BorderPane borderPane = new BorderPane();
        //borderPane.setPadding(new Insets(5, 5 , 5, 5));
        //borderPane.setLeft(new Label("Enter map's location: "));
        //TextField textField = new TextField();
        //textField.setAlignment(Pos.BOTTOM_RIGHT);
        //borderPane.setCenter(textField);
        //pane.setTop(borderPane);
        //textField.setOnAction(e -> {
        try {
            Scanner input = new Scanner(new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\Map2.txt"));
            Game game = new Game(input);
            //primaryStage.close();
        } catch (FileNotFoundException w) {
            w.printStackTrace();
        } catch (InvalidMapException e) {
            e.printStackTrace();
        }
        //});
        //Scene scene = new Scene(pane);

        //primaryStage.setTitle("Tank");
        //primaryStage.getIcons().add(new Image(new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\586f7f34dd3b5.jpg").toURI().toString()));
        //primaryStage.setScene(scene);
        //primaryStage.show();
        /*Scanner input = new Scanner(new File("C:\\Users\\Admin\\2ndChanceProject2\\src\\sample\\Map1.txt"));
        Tank game = new Tank(input);
        Pane x = new Pane();
        x.getChildren().add(game.getImage());
        ImageView z = game.getImage();
        z.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.A){
                z.setX(game.getImage().getX() - 10);
            }
            else if(e.getCode() == KeyCode.D){
                x.getChildren().remove(z);
            }
        });
        Scene s = new Scene(x);
        primaryStage.setScene(s);
        primaryStage.show();
        z.requestFocus();*/

    }
    public static void main(String[] args) {
        setArgs(args);
        launch(args);
    }
    public static void setArgs(String[] w){
        x = w;
    }
    public static String[] getArgs(){
        return x;
    }
}
