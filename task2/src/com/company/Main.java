package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    static final int BORDER_SIZE = 40;
    static final int NUM_OF_UNIT = 8;
    GameUnit[][] gameUnits = new GameUnit[NUM_OF_UNIT][NUM_OF_UNIT];

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.setTitle("SHULA...");
        stage.setScene(new Scene(createGame(), BORDER_SIZE * NUM_OF_UNIT, BORDER_SIZE * NUM_OF_UNIT + 45));
        stage.show();
    }

    private Parent createGame(){
        BorderPane gameScene = new BorderPane();
        gameScene.setTop(createBorder());

        Pane pane = new Pane();
        pane.setPrefSize(NUM_OF_UNIT * BORDER_SIZE, NUM_OF_UNIT * BORDER_SIZE);

        for (int i = 0; i < NUM_OF_UNIT; i++){
            for (int j = 0; j< NUM_OF_UNIT; j++){
                gameUnits[i][j] = new GameUnit(i,j, mineFieldSetUp.plantBomb());//creat unit in border
                pane.getChildren().add(gameUnits[i][j]);//add unit to border pane
                //System.out.println(gameUnits[i][j] );
            }
        }

        for (int i = 0; i < NUM_OF_UNIT; i++){
            for (int j = 0; j< NUM_OF_UNIT; j++){
                gameUnits[i][j].setNeighboursBombsCount(mineFieldSetUp.countNeighboursBombs(i, j, gameUnits));
            }
        }
        gameScene.setCenter(pane);

        return gameScene;
    }

    private BorderPane createBorder(){
        BorderPane borderPane = new BorderPane();
        Text topTxt = new Text("Shula mines");
        InnerShadow innerShadow = new InnerShadow();
        topTxt.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        topTxt.setStroke(Color.WHITESMOKE);
        topTxt.setFill(Color.WHITESMOKE);
        borderPane.setCenter(topTxt);
        borderPane.setPadding(new Insets(10,10,10,10));
        borderPane.setStyle("-fx-background-color: #5d5b5b");

        return borderPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}