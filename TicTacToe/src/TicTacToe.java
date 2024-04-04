import java.awt.*;
import java.util.*;

public class TicTacToe {

    public static void main(String[] args) {
        AIplayer AI= new AIplayer();
        Board b = new Board();
        Point p = new Point(0, 0);
        Random rand = new Random();

        b.displayBoard(); // Display the board

        System.out.println("Who makes first move? (1)Computer (2)User: ");
        int choice = b.scan.nextInt(); // check the user input
        b.scan.nextLine();

        if (choice == 1) // computer goes first
        {
            AI.callMinimax(0, 1, b);
            for (PointsAndScores pas : AI.rootsChildrenScores) // Show scores of possible moves
                System.out.println("Point: " + pas.point + " Score: " + pas.score);

            // Place a move and show it
            b.placeAMove(AI.returnBestMove(), 1);
            b.displayBoard();
            System.out.println("AI did the move");
        }

        while (!b.isGameOver()) { // continue until the end of the game
            System.out.println("Your move: line (1-5) column (1-5)");
            Point userMove = new Point(b.scan.nextInt()-1, b.scan.nextInt()-1); // get user move

            while (b.getState(userMove)!=0) { // Wrong input checker
                System.out.println("Invalid move. Make your move again: ");
                userMove.x=b.scan.nextInt()-1;
                userMove.y=b.scan.nextInt()-1;
            }
            // Place and display user move
            b.placeAMove(userMove, 2);
            b.displayBoard();

            if (b.isGameOver()) { // check if game is over
                break;
            }

            AI.callMinimax(0, 1, b);
            for (PointsAndScores pas : AI.rootsChildrenScores) // show scores of possible moves
                System.out.println("Point: " + pas.point + " Score: " + pas.score);

            // Place and display AI move
            b.placeAMove(AI.returnBestMove(), 1);
            b.displayBoard();
        }

        // Conditions for endgame
        if (b.hasXWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasOWon()) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}