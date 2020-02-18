package com.example.two_dicepig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random rand = new Random();
    int firstDiceValue;
    int secondDiceValue;
    int firstPlayerTotalValue;
    int secondPlayerTotalValue;
    int turnTotalInt;
    ImageView diceImage;
    boolean playerOnesTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextViews that will change
        final TextView playerOneValue = findViewById(R.id.playerOneTotalValue);
        final TextView playerTwoValue = findViewById(R.id.playerTwoTotalValue);
        final TextView currentPlayer = findViewById(R.id.currentPlayerValue);
        final TextView turnTotalValue = findViewById(R.id.turnTotalValue);
        final TextView currentPlayerText = findViewById(R.id.currentPlayerText);

        final Button rollButton = findViewById(R.id.rollButtonID);
        final Button holdButton = findViewById(R.id.holdButtonID);

        /*
        Description: This method will handle the button click event for the
                     roll button. The method will generate two random numbers
                     between 1-6. The images of the dice will update with the
                     corresponding values. There are 4 cases that can occur:
                     1) Double 1s: The current player's total score is set
                        to 0 and it becomes the next player's turn.
                     2) Single 1: The current player's turn score is set to
                        0 and it becomes the next player's turn.
                     3) Doubles (not 1s): The value gets added to the turn
                        total as normal but the current player is obligated
                        to roll again. The hold button is disabled until
                        roll is pressed again.
                     4) Non-identical pair: The sum of the two values is added
                        to the turn total.
        Pre-conditions: Roll button is clicked by the user. Takes View as parameter.
        Post-conditions: Score and current player turn is updated as detailed above.
        */
        rollButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firstDiceValue = rand.nextInt(6) + 1;
                secondDiceValue = rand.nextInt(6) + 1;

                //re-enable hold button if disabled from doubles
                if (!holdButton.isEnabled()) {
                    holdButton.setEnabled(true);
                }

                //set left image
                diceImage = findViewById(R.id.diceLeft);
                switch (firstDiceValue) {
                    case 1:
                        diceImage.setImageResource(R.drawable.diceone);
                        break;
                    case 2:
                        diceImage.setImageResource(R.drawable.dicetwo);
                        break;
                    case 3:
                        diceImage.setImageResource(R.drawable.dicethree);
                        break;
                    case 4:
                        diceImage.setImageResource(R.drawable.dicefour);
                        break;
                    case 5:
                        diceImage.setImageResource(R.drawable.dicefive);
                        break;
                    case 6:
                        diceImage.setImageResource(R.drawable.dicesix);
                        break;
                    default:
                        System.out.println("ERROR: firstDiceValue generation failure");
                }

                //set right image
                diceImage = findViewById(R.id.diceRight);
                switch (secondDiceValue) {
                    case 1:
                        diceImage.setImageResource(R.drawable.diceone);
                        break;
                    case 2:
                        diceImage.setImageResource(R.drawable.dicetwo);
                        break;
                    case 3:
                        diceImage.setImageResource(R.drawable.dicethree);
                        break;
                    case 4:
                        diceImage.setImageResource(R.drawable.dicefour);
                        break;
                    case 5:
                        diceImage.setImageResource(R.drawable.dicefive);
                        break;
                    case 6:
                        diceImage.setImageResource(R.drawable.dicesix);
                        break;
                    default:
                        System.out.println("ERROR: secondDiceValue generation failure");
                }

                //set score
                if (firstDiceValue == 1 && secondDiceValue == 1) {  //snake eyes, set total to 0 and switch turns
                    //update turn total
                    turnTotalInt = 0;
                    turnTotalValue.setText(Integer.toString(turnTotalInt));

                    //set total values and switch turns
                    if (playerOnesTurn) {   //P1 turn
                        firstPlayerTotalValue = 0;
                        playerOneValue.setText(Integer.toString(firstPlayerTotalValue));

                        playerOnesTurn = false;
                        currentPlayer.setText("P2");
                    }
                    else {    //P2 turn
                        secondPlayerTotalValue = 0;
                        playerTwoValue.setText(Integer.toString(secondPlayerTotalValue));

                        playerOnesTurn = true;
                        currentPlayer.setText("P1");
                    }
                }
                else if (firstDiceValue == 1 || secondDiceValue == 1) { //rolled a 1, not snake eyes
                    //update turn total
                    turnTotalInt = 0;
                    turnTotalValue.setText(Integer.toString(turnTotalInt));

                    //switch turns
                    if (playerOnesTurn) {   //P1 turn
                        playerOnesTurn = false;
                        currentPlayer.setText("P2");
                    }
                    else {    //P2 turn
                        playerOnesTurn = true;
                        currentPlayer.setText("P1");
                    }
                }
                else if (firstDiceValue == secondDiceValue) {   //doubles, not snake eyes
                    //update turn total
                    turnTotalInt += firstDiceValue + secondDiceValue;
                    turnTotalValue.setText(Integer.toString(turnTotalInt));

                    //disable hold button
                    holdButton.setEnabled(false);
                }
                else {  //non-identical pair
                    //update turn total
                    turnTotalInt += firstDiceValue + secondDiceValue;
                    turnTotalValue.setText(Integer.toString(turnTotalInt));
                }

            }//end onClick method
        }); //end rollButtonListener method


        /*
        Description:
        Pre-conditions:
        Post-conditions:
        */
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (playerOnesTurn) { //P1 turn
                firstPlayerTotalValue += turnTotalInt;
                playerOneValue.setText(Integer.toString(firstPlayerTotalValue));
                turnTotalInt = 0;
                turnTotalValue.setText(Integer.toString(turnTotalInt));

                //win condition
                if (firstPlayerTotalValue >= 50) {
                    currentPlayerText.setText("Winner:");
                    holdButton.setEnabled(false);
                    rollButton.setEnabled(false);
                }
                else {
                    currentPlayer.setText("P2");
                    playerOnesTurn = false;
                }
            }
            else { //P2 turn
                secondPlayerTotalValue += turnTotalInt;
                playerTwoValue.setText(Integer.toString(secondPlayerTotalValue));
                turnTotalInt = 0;
                turnTotalValue.setText(Integer.toString(turnTotalInt));

                //win condition
                if (secondPlayerTotalValue >= 50) {
                    currentPlayerText.setText("Winner:");
                    holdButton.setEnabled(false);
                    rollButton.setEnabled(false);
                }
                else {
                    currentPlayer.setText("P1");
                    playerOnesTurn = true;
                }
            }

            }//end onClick method
        });//end holdButtonListener method

    }//end onCreate


}
