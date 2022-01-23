package com.company;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import static com.company.Main.BORDER_SIZE;
import static com.company.Main.NUM_OF_UNIT;

class GameUnit extends StackPane {
    private int x, y;//Location in matrix
    private boolean isBomb;
    private Text unitText;
    private String neighboursBombsCount;
    private Rectangle rectangle;//units

    public GameUnit(int x, int y, boolean isBomb){
        this.x = x;
        this.y = y;
        this.isBomb = isBomb;
        unitText = new Text("x");
        unitText.setVisible(false);

        rectangle = new Rectangle(BORDER_SIZE - 1, BORDER_SIZE - 1);//Define the units on the board
        rectangle.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.BLUE)}));

        this.getChildren().addAll(rectangle, unitText);

        setOnMouseClicked(event -> check(event));

        setTranslateX(BORDER_SIZE * x);
        setTranslateY(BORDER_SIZE * y);
    }

    public boolean getIsBomb(){
        return this.isBomb;
    }

    public void setUnitText(String txt){
        this.unitText.setText(txt);
    }

    public void setNeighboursBombsCount(int count){
        this.neighboursBombsCount = String.valueOf(count);
    }

    void check(MouseEvent e){
        if (mineFieldSetUp.gameFinished) {
            this.setDisable(true);
            return;
        }

        if ((this.unitText.visibleProperty().getValue()==true)
                && (this.unitText.getText() != "x"))
            return;

        if ((e.getButton() == MouseButton.SECONDARY)){
            this.unitText.setVisible(oppositeBoolValue(this.unitText.visibleProperty().getValue()));
        }
        else{
            mineFieldSetUp.openedPieces++;
            if(isBomb){
                this.rectangle.setFill(Color.RED);
                mineFieldSetUp.gameEnd(true);
                return;
            }
            if(mineFieldSetUp.openedPieces == (NUM_OF_UNIT * NUM_OF_UNIT)- mineFieldSetUp.bombsInGame){
                mineFieldSetUp.gameEnd(true);
                return;
            }
            this.unitText.setText(this.neighboursBombsCount);
            this.unitText.setVisible(true);
            this.unitText.setDisable(true);
        }
    }

    private boolean oppositeBoolValue(boolean bool){
        if (bool == true) return false;
        else return true;
    }

}