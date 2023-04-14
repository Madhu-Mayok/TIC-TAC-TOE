package com.example.tictactoefeb;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {
    private Label playerX,playerO;
    private Button buttons[][] = new Button[3][3];
    private boolean playerXturn = true;
    private int playerXscore =0 ,playerOscore =0;
    private BorderPane createContent()
    {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        //Title
        Label title = new Label("Tic Tac Toe");
        title.setStyle("-fx-font-size : 28pt; -fx-font-weight: bold");
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER); // to place the title at the center of board

        //Game Board

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // adds horizontal gap between components in gridpane
        gridPane.setVgap(10);// adds vertical gap between components in gridpane
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(); // creating button
                button.setPrefSize(100,100); // setting size to button
                button.setStyle("-fx-font-size : 20pt; -fx-font-weight: bold");
                button.setOnAction(event -> buttonClicked(button)); // when button clicked x or o will be added to button

                buttons[i][j] = button; // adding button to buttons array also
                gridPane.add(button,j,i); // adding button to grid pane..grid pane is like a 2d mat, but in gridpane indexing is reverse ..not like 2d
                // gridPane(j,i)---- arr(i,j)
            }
        }

        root.setCenter(gridPane); // plaing the grid pane at center of the layout

        //scoreBoard
        HBox scoreBoard = new HBox();
        scoreBoard.setAlignment(Pos.CENTER); // aligning the scoreboard to the center

        playerX = new Label("Player X : 0");
        playerX.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold");
        playerO = new Label("   Player O : 0");
        playerO.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold");

        scoreBoard.getChildren().addAll(playerX,playerO); // adding lables to hbox
        root.setBottom(scoreBoard); // placing hbox at the bottom of the layout

        return root;
    }

    private void buttonClicked ( Button button) // when button clicked x or o will be displayed on button based on turn
    {
        if(button.getText().equals("")) // if button is not added with x or o
        {
            if(playerXturn)
                button.setText("X");
            else
                button.setText("O");

            playerXturn = !playerXturn; // changing player turn
            checkWinner(); // after clicking button there may be a winner.. so checking
        }
    }

    //to check winner
    private void checkWinner()
    {
        for (int row = 0; row < 3; row++) {
            //if text on button not empty and entire row buttons have same value ie either x or o

            if(! buttons[row][0].getText().isEmpty() && buttons[row][0].getText().equals(buttons[row][1].getText()) && buttons[row][1].getText().equals(buttons[row][2].getText()))
            {
                // we have  a winner
                String winner = buttons[row][0].getText();
                showWinnerDialog(winner); // show the winnner
                updateScore(winner); // update the score
                resetBoard(); // reset the board
                return; // no need to check other winning conditions
            }
        }

        // winner condition for columns

        for (int col = 0; col < 3; col++) {
            //if text on button not empty and entire col buttons have same value ie either x or o
            if(! buttons[0][col].getText().isEmpty() && buttons[0][col].getText().equals(buttons[1][col].getText()) && buttons[1][col].getText().equals(buttons[2][col].getText()))
            {
                // we have  a winner
                String winner = buttons[0][col].getText();
                showWinnerDialog(winner); // show the winnner
                updateScore(winner); // update the score
                resetBoard(); // reset the board
                return;
            }
        }
        //winner condition for diagnols
        //if text on button not empty and entire diagnol buttons have same value ie either x or o
        if(! buttons[0][0].getText().isEmpty() && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText()))
        {
            // we have  a winner
            String winner = buttons[0][0].getText();
            showWinnerDialog(winner); // show the winnner
            updateScore(winner); // update the score
            resetBoard(); // reset the board
            return;
        }
        if(! buttons[0][2].getText().isEmpty() && buttons[2][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[0][2].getText()))
        {
            // we have  a winner
            String winner = buttons[0][2].getText();
            showWinnerDialog(winner); // show the winnner
            updateScore(winner); // update the score
            resetBoard(); // reset the board
            return;
        }

        // Tie condition
        boolean tie = true;
        // checking if all buttons are filled
        for(Button [] row : buttons)
        {
            for(Button button : row)
            {
                if(button.getText().isEmpty()) {
                    tie = false;
                    break;
                }
            }
        }

        if(tie) // if there is tie .. show its a tie and reset the board
        {
            showTieDialog();
            resetBoard();
        }
    }
    // to show winner in dialog box
    private void showWinnerDialog(String winner)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WINNER"); // title for alert dialog box
        alert.setContentText("Congratulations "+ winner + " !! you won the game!!");
        alert.setHeaderText(""); // otherwise it shows Message on top of dialouge box..if you dont want write this line
        alert.showAndWait();
    }
    //to show tie in dialog box
    private void showTieDialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TIE"); // title for alert dialog box
        alert.setContentText("GAME OVER !! It's a tie.");
        alert.setHeaderText(""); // otherwise it shows Message on top of dialouge box..if you dont want write this line
        alert.showAndWait();
    }
    // to update the score

    private void updateScore(String winner)
    {
        if(winner.equals("X"))
        {
            playerXscore++;
            playerX.setText("Player X : "+playerXscore);
        }
        else {
            playerOscore++;
            playerO.setText("   Player O : "+playerOscore);
        }
    }
    //to reset the board

    private void resetBoard()
    {
        for(Button [] row : buttons)
        {
            for(Button button : row)
            {
                button.setText("");
            }
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("TIC-TAC-TOE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}