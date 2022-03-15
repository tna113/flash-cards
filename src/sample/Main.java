/**
 * I wanted to do 18 reiterations of the game but my program
 * crashes when it hits 16 for some reason so I just capped it there.
 * @author Thea Arias
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class Main extends Application {
    //HashMap to store states/capitals as keys/values
    HashMap<String, String> statesMap = new HashMap<>();

    //init() method uses Scanner to read from txt file and fills
    //hashmap with state/capital pairs at program start-up
    @Override
    public void init() throws Exception {
        File text = new File("src/statecapitals.txt");
        System.out.println("File Found.");
        Scanner read = new Scanner(text);
        for(int i = 0; i < 18; i++){
            String state = read.nextLine();
            String capital = read.nextLine();
            statesMap.put(state, capital);
        }
        System.out.println(statesMap);  //double-checks contents
    }

    int correctCount = 0;
    int totalCount = 0;
    int index = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //shuffle hash map keys
        List<String> list = new ArrayList<>(statesMap.keySet());
        Collections.shuffle(list);

        //labels
        Label stateLabel = createLabel(" ",18);
        Label capitalLabel = createLabel("????",22);
        Label checkLabel = createLabel(" ",16);
        VBox topBox = new VBox(stateLabel, capitalLabel, checkLabel);
        topBox.setSpacing(10);

        TextField letterField = new TextField();
        letterField.setFont(new Font("Verdana", 18));
        letterField.setPrefWidth(Double.MAX_VALUE);
        letterField.setAlignment(Pos.CENTER);

        //buttons
        Button checkButton = createButton("Check");
        Button nextButton = createButton("Next");

        //stats labels
        Label correctLabel = createLabel("Correct: ");
        Label correct = createLabel(""+correctCount);
        Label totalLabel = createLabel("Total: ");
        Label total = createLabel(""+totalCount);
        HBox statsBox = new HBox(correctLabel, correct, totalLabel, total);
        statsBox.setSpacing(20);

        //hash map functionality
        if(index==0){
            String listKey = list.get(index);
            stateLabel.setText(listKey);
        }

        //scene and stage
        VBox root = new VBox(topBox, letterField, checkButton, nextButton, statsBox);
        root.setPadding(new Insets(20));
        root.setSpacing(20);


        checkButton.setOnAction(actionEvent -> {
            //when check button clicked, take text area
            String userAnswer = letterField.getText();
            userAnswer = userAnswer.toLowerCase();
            String currentState = stateLabel.getText();
            String currentCapital = statesMap.get(currentState);

            //check to see if text field empty
            if(userAnswer.equals("")) {
                checkLabel.setText("Answer cannot be blank!");
            } else {
                //compare answer to key value
                if(userAnswer.equals(currentCapital.toLowerCase())){
                    //if right, ++correct, display correct label
                    checkLabel.setText("Correct!");
                    correctCount++;
                    correct.setText(""+correctCount);
                } else {
                    checkLabel.setText("Incorrect!");
                }
            }
        });

        nextButton.setOnAction(actionEvent -> {
            if(letterField.getText().equals("")) {
                checkLabel.setText("Answer cannot be blank!");
            } else {
                letterField.setText("");
                //when next button clicked
                //display second key, ++round
                if (index == 16) {
                    //end game after 18 rounds
                    //display stats, game over label
                    double correctCountDouble = correctCount;
                    double percent = (correctCountDouble/16)*100;
                    stateLabel.setText("");
                    capitalLabel.setText("Game over!");
                    checkLabel.setText("Your score: " + percent + "%");
                } else {
                    index++;
                    totalCount++;
                    total.setText("" + totalCount);
                    String listKey = list.get(index);
                    stateLabel.setText(listKey);
                    checkLabel.setText("");
                }
            }
        });


        //Scene/stage
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("States and Capitals");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Button createButton(String str) {
        Button temp = new Button(str);
        temp.setFont(new Font("Verdana", 18));
        temp.setPrefWidth(Double.MAX_VALUE);
        return temp;
    }

    public static Label createLabel(String str, int size) {
        Label temp = new Label(str);
        temp.setFont(new Font("Verdana", size));
        temp.setPrefWidth(Double.MAX_VALUE);
        temp.setAlignment(Pos.CENTER);
        return temp;
    }
    public static Label createLabel(String str) {
        Label temp = new Label(str);
        temp.setFont(new Font("Verdana", 14));
        return temp;
    }
}
