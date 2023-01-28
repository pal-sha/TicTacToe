import java.sql.PreparedStatement;
import java.util.*;

public class tictactoe {

    // Lists to hold player position and computer position values
    static ArrayList<Integer> playerPosition = new ArrayList<Integer>();
    static ArrayList<Integer> computerPosition = new ArrayList<Integer>();

    // Creating lists for all potential wins
    static List TopRow = Arrays.asList(1, 2, 3);
    static List MiddleRow = Arrays.asList(4, 5, 6);
    static List BottomRow = Arrays.asList(7, 8, 9);

    static List LeftColumn = Arrays.asList(1, 4, 7);
    static List MiddleColumn = Arrays.asList(2, 5, 8);
    static List RightColumn = Arrays.asList(3, 6, 9);

    static List LeftDiagonal = Arrays.asList(1, 5, 9);
    static List RightDiagonal = Arrays.asList(7, 5, 3);

    public static void main(String[] args) {

        // five because each column is separated by a line so 3 columns + 2 lines
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}};

        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println("Welcome to TicTacToe!");
        System.out.println("Your symbol is 'X' and the computer is '0'.");
        System.out.println("Enter number from 1 to 9 to play a move and make sure selected spot is not already filled.");
        System.out.println("Enjoy!");
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");

        System.out.println("-+-+-+ GAME BOARD -+-+-+");
        printBoard(gameBoard);
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+");

        int result = 0;
        while (result != 1 && result != 2 && result != 3) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your move [1-9]: ");
            int playPosition = sc.nextInt();
            while (playerPosition.contains(playPosition) || computerPosition.contains(playPosition)) {
                System.out.println("Position is already filled. Enter a valid position: ");
                playPosition = sc.nextInt();
            }

            System.out.println("Placing symbol at " + playPosition);
            playerMove(gameBoard, playPosition, "Player");

            result = checkWinner();

            Random rand = new Random();
            int CompPosition = rand.nextInt(9) + 1; // range is between 1 and 9
            while (playerPosition.contains(CompPosition) || computerPosition.contains(CompPosition)) {
                //System.out.println("Position is already filled. Enter a valid position: ");
                CompPosition = rand.nextInt(9) + 1;
            }

            playerMove(gameBoard, CompPosition, "Computer");

            System.out.println("-+-+-+ GAME BOARD -+-+-+");
            printBoard(gameBoard);
            System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+");

            result = checkWinner();
        }

        System.out.println("Thank you for playing! :D");
    }

    // Method to print out game board
    public static void printBoard (char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char col: row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }

    // Method to place player and computer move
    public static void playerMove(char[][] gameBoard, int position, String user){

        char symbol = ' ';

        if (user.equals("Player")) {
            symbol = 'X';
            playerPosition.add(position);
        } else if (user.equals("Computer")) {
            symbol = 'O';
            computerPosition.add(position);
        }

        switch(position) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }
    }

    // Method to check if any winning conditions have been met
    public static int checkWinner(){
        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);

        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);

        List leftDiag = Arrays.asList(1, 5, 9);
        List rightDiag = Arrays.asList(7, 5, 3);

        List<List> winConditions = new ArrayList<List>();
        winConditions.add(topRow);
        winConditions.add(midRow);
        winConditions.add(botRow);
        winConditions.add(leftCol);
        winConditions.add(midCol);
        winConditions.add(rightCol);
        winConditions.add(leftDiag);
        winConditions.add(rightDiag);

        for (List list : winConditions) {
            if (playerPosition.containsAll(list)) {
                System.out.println("You win!");
                return 1;
            } else if (computerPosition.containsAll(list)) {
                System.out.println("Sorry, you lost :(");
                return 2;
            } else if (playerPosition.size() + computerPosition.size() == 9) {
                System.out.println("Its a tie!");
                return 3;
            }
        }

        return 4;
    }

    // Method to implement Computer AI
    public static void compMove(){

        // if in PLAYER LIST, in top row || middle row || bottom row || left column || middle column || right column || left diagonal || right diagonal
            // 2 OUT OF 3 numbers are filled
                // computer move = the unfilled position in that list
            // 0 OUT OF 3 || 1 OUT OF 3 numbers are filled
                // if in COMPUTER LIST, in top row || middle row || bottom row || left column || middle column || right column || left diagonal || right diagonal
                    // 2 OUT OF 3 numbers are filled
                        // computer move = the unfilled position in that list
                    // 1 OUT OF 3 numbers are filled
                        // computer move = one or the other unfilled position in that list
                    // 0 OUT OF 3 numbers are filled
                        // computer move = randomly generated number of position that is not yet filled

        List<List> solvedConditions = new ArrayList<List>();
        solvedConditions.add(TopRow);
        solvedConditions.add(MiddleColumn);
        solvedConditions.add(BottomRow);
        solvedConditions.add(LeftColumn);
        solvedConditions.add(MiddleColumn);
        solvedConditions.add(RightColumn);
        solvedConditions.add(LeftDiagonal);
        solvedConditions.add(RightDiagonal);

    }

//    public static int checkPosition() {
//
//        // CHECKING FOR TOP ROW
//        // Returns:
//            // 3 if 3 is unfilled
//            // 2 if 2 is unfilled
//            // 1 if 1 is unfilled
//        if (playerPosition.contains(TopRow.contains(1)) && playerPosition.contains(TopRow.contains(2))) {
//            return 3;
//        } else if (playerPosition.contains(TopRow.contains(1)) && playerPosition.contains(TopRow.contains(3))){
//            return 2;
//        } else if (playerPosition.contains(TopRow.contains(2)) && playerPosition.contains(TopRow.contains(3))) {
//            return 1;
//        }
//
//        // CHECKING FOR MIDDLE ROW
//        // Returns:
//            // 6 if 6 is unfilled
//            // 5 if 5 is unfilled
//            // 4 if 4 is unfilled
//        if (playerPosition.contains(MiddleRow.contains(4)) && playerPosition.contains(MiddleRow.contains(5))) {
//            return 6;
//        } else if (playerPosition.contains(MiddleRow.contains(4)) && playerPosition.contains(MiddleRow.contains(6))) {
//            return 5;
//        } else if (playerPosition.contains(MiddleRow.contains(5)) && playerPosition.contains(MiddleRow.contains(6))) {
//            return 4;
//        }
//
//
//
//        return 0;
//    }
}