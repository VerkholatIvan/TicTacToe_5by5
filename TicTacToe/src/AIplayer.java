import java.util.*;

class AIplayer {
    List<Point> availablePoints; // list to store points on the board
    List<PointsAndScores> rootsChildrenScores; // list to store points which are associated with possible moves
    Board b = new Board(); // The game board
    private static final int max_depth = 6;
    Random rand = new Random();

    public AIplayer() {} // AIplayer constructor


    // method to return best move from AI
    // The method works next way. Its goes through possible moves and if AI as several best moves it takes the random one.
    // Unfortunately it still takes time, but may a bit fasten the choose.
    public Point returnBestMove() {
        int MAX = -100000;
        List<Point> possibleMoves = new ArrayList<>();

        for (PointsAndScores i : rootsChildrenScores)
        {
            if (MAX < i.score)
            {
                MAX = i.score;
                possibleMoves.clear();
                possibleMoves.add(i.point);
            }

            else if (MAX == i.score)
            {
                possibleMoves.add(i.point);
            }
        }
        return possibleMoves.get(rand.nextInt(possibleMoves.size())); // take random move out of possible best ones
    }


    // method to return the minimum value
    public int returnMin(List<Integer> list) { // method to take out the minimum value from the given list
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i); // update minimum value if smaller is found
                index = i; // update the index of minimum value
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) { // method to take out the maximum value from the given list
        int max = Integer.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i); // update maximum value if bigger is found
                index = i; // update the index of maximum value
            }
        }
        return list.get(index);
    }


    // method to initiate the minimax algorithm
    public void callMinimax(int depth, int turn, Board b){
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn, b); // calling the minimax algorithm
    }


    // minmimax algoritm implementation to find the best move
    public int minimax(int depth, int turn, Board b) {
        if (depth == max_depth || b.isGameOver())
            return HeuristicValue(b);

        if (b.hasXWon()) return 1;
        if (b.hasOWon()) return -1;
        List<Point> pointsAvailable = b.getAvailablePoints();
        if (pointsAvailable.isEmpty()) return 0;

        List<Integer> scores = new ArrayList<>();

        // get the available point
        for (Point point : pointsAvailable)
        {
            if (turn == 1)
            { // If its "X" player turn
                b.placeAMove(point, 1); // place player's move on a board
                int currentScore = minimax(depth + 1, 2, b);
                scores.add(currentScore); // add score to the scores list
                if (depth == 0) // If at root level
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point)); // store score and point



            } else if (turn == 2) { // if its "O" player turn
                b.placeAMove(point, 2); // place player's move on a board
                scores.add(minimax(depth + 1, 1, b));
            }
            b.placeAMove(point, 0);
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores); // Return the maximum or minimum score based on the player's turn
    }

    public int HeuristicValue(Board board) {
        if (board.hasXWon()) { // X has won
            return 10;
        } else if (board.hasOWon()) { // O has won
            return  -10;
        } else { // Draw
            return  0;
        }
    }

}