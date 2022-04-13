package sample;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
public class TicTacToeClient extends Application
implements TicTacToeConstants {
    private boolean myTurn = false;
    private char myToken = ' ';
    private char otherToken = ' ';
    private Cell[][] cell = new Cell[3][3];
    private Label lblTitle = new Label();
    private Label lblStatus = new Label();
    private int rowSelected;
    private int columnSelected;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private boolean continueToPlay = true;
    private boolean waiting = true;
    private String host = "localhost";
    private GridPane pane;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(i, j), j, i);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(lblTitle);
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);
        Scene scene = new Scene(borderPane, 320, 350);
        primaryStage.setTitle("TicTacToeClient"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(host, 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new Thread(() -> {
            try {
                int player = fromServer.readInt();
                if (player == PLAYER1) {
                    myToken = 'X';
                    otherToken = 'O';
                    Platform.runLater(() -> {
                        lblTitle.setText("Player 1 with token 'X'");
                        lblStatus.setText("Waiting for player 2 to join");
                    });
                    fromServer.readInt(); // Whatever read is ignored
                    Platform.runLater(() ->
                            lblStatus.setText("Player 2 has joined. I start first"));
                    myTurn = true;
                } else if (player == PLAYER2) {
                    myToken = 'O';
                    otherToken = 'X';
                    Platform.runLater(() -> {
                        lblTitle.setText("Player 2 with token 'O'");
                        lblStatus.setText("Waiting for player 1 to move");
                    });
                }
                while (continueToPlay) {
                    if (player == PLAYER1) {
                        waitForPlayerAction(); // Wait for player 1 to move
                        sendMove(); // Send the move to the server
                        receiveInfoFromServer(); // Receive info from the server
                    } else if (player == PLAYER2) {
                        receiveInfoFromServer(); // Receive info from the server
                        waitForPlayerAction(); // Wait for player 2 to move
                        sendMove(); // Send player 2's move to the server
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    private void sendMove() throws IOException {
        toServer.writeInt(rowSelected); // Send the selected row
        toServer.writeInt(columnSelected); // Send the selected column
    }

    private void receiveInfoFromServer() throws IOException {
// Receive game status
        int status = fromServer.readInt();
        if (status == PLAYER1_WON) {
            continueToPlay = false;
            if (myToken == 'X') {
                Platform.runLater(() -> {
                    pane.getChildren().clear();
                    pane.getChildren().add(new Label("I won! (X)"));
                    lblStatus.setText("");
                });
            } else if (myToken == 'O') {
                Platform.runLater(() ->{
                    pane.getChildren().clear();
                    pane.getChildren().add(new Label("Player1 won! (X)"));
                    lblStatus.setText("");
                });
                receiveMove();
            }
        } else if (status == PLAYER2_WON) {
// Player 2 won, stop playing
            continueToPlay = false;
            if (myToken == 'O') {
                Platform.runLater(() -> {
                    pane.getChildren().clear();
                    pane.getChildren().add(new Label("I won! (O)"));
                    lblStatus.setText("");
                });
            } else if (myToken == 'X') {
                Platform.runLater(() ->{
                    pane.getChildren().clear();
                    pane.getChildren().add(new Label("Player2 won! (O)"));
                    lblStatus.setText("");
                });
                receiveMove();
            }
        } else if (status == DRAW) {
            continueToPlay = false;
            Platform.runLater(() ->{
                pane.getChildren().clear();
                pane.getChildren().add(new Label("Game is over, no winner!"));
                lblStatus.setText("");
            });
            if (myToken == 'O') {
                receiveMove();
            }
        } else {
            receiveMove();
            Platform.runLater(() -> lblStatus.setText("My turn"));
            myTurn = true; // It is my turn
        }
    }

    private void receiveMove() throws IOException {
        int row = fromServer.readInt();
        int column = fromServer.readInt();
        Platform.runLater(() -> cell[row][column].setToken(otherToken));
    }

    public class Cell extends Pane {
        private int row;
        private int column;
        private char token = ' ';

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
            this.setPrefSize(2000, 2000); // What happens without this?
            setStyle("-fx-border-color: black"); // Set cell's border
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;
            repaint();
        }

        protected void repaint() {
            if (token == 'X') {
                Line line1 = new Line(10, 10,
                        this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10, this.getHeight() - 10,
                        this.getWidth() - 10, 10);
                line2.startYProperty().bind(
                        this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                this.getChildren().addAll(line1, line2);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(
                        this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(
                        this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(
                        this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(
                        this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);
                getChildren().add(ellipse); // Add the ellipse to the pane
            }
        }

        private void handleMouseClick() {
            if (token == ' ' && myTurn) {
                setToken(myToken); // Set the player's token in the cell
                myTurn = false;
                rowSelected = row;
                columnSelected = column;
                lblStatus.setText("Waiting for the other player to move");
                waiting = false; // Just completed a successful move
            }
        }
    }
}