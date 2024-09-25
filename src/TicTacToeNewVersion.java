import java.awt.*;
import java.util.Scanner;
import java.util.Random;

public class TicTacToeNewVersion {
    public static void main(String[] args) {
        // First set up the panel
        panelSetup(99);

        // Create a multidimensional array as the panel
        int[][] arrPanel = new int[3][3];

        char restartGame = 0;
        char goesFirst = 0;
        int gameRound = 0;
        int gameTurn = 1;

        //set arrayPanel all equals to 0 from the start of the game


        Random rand = new Random();
        Scanner scnr = new Scanner(System.in);

        // Outermost loop when user input R game start another run
        do {

            gameTurn = 1;

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    arrPanel[i][j] = 0;
                }
            }

            StdDraw.clear();

            panelSetup(99);


            // Ask who will go first, whether the user or computer
            do {
                StdDraw.setPenColor(Color.MAGENTA);
                StdDraw.text(50, 55, "Who Goes First?");
                StdDraw.text(50, 45, "User(U) or Computer(C)?");
                goesFirst = scnr.next().charAt(0);

            } while (goesFirst != 'U' && goesFirst != 'C');

            // Start with the outermost loop, game odd number draw circle, game even number draw cross
            for (gameTurn = 1; gameTurn <= 9; gameTurn++) {
                // Case when game turn is odd, draw circle
                if (gameTurn % 2 == 1) {
                    // Draw circle by user if input is 'U'
                    if (goesFirst == 'U') {
                        StdDraw.setPenColor(Color.blue);
                        while (!StdDraw.isMousePressed()) {
                        }
                        StdDraw.pause(300);

                        double mouseX = StdDraw.mouseX();
                        double mouseY = StdDraw.mouseY();

                        if (!drawCircle(mouseX, mouseY, arrPanel)) {
                            gameTurn--; // Decrement game turn if the box was occupied
                        }
                    }
                    // Draw circle by computer if input is 'C'
                    else {
                        StdDraw.setPenColor(Color.red);
                        double randX, randY;
                        do {
                            randX = rand.nextDouble() * 99 + 1;
                            randY = rand.nextDouble() * 99 + 1;
                        } while (!drawCircle(randX, randY, arrPanel));
                    }
                }
                // Case when game turn is even, draw cross
                else {
                    StdDraw.setPenColor(Color.red);
                    // Draw cross by computer if input is 'U'
                    if (goesFirst == 'U') {
                        double randX, randY;
                        do {
                            randX = rand.nextDouble() * 99 + 1;
                            randY = rand.nextDouble() * 99 + 1;
                        } while (!drawCross(randX, randY, arrPanel));
                    }
                    // Draw cross by user if input is 'C'
                    else {
                        StdDraw.setPenColor(Color.blue);
                        while (!StdDraw.isMousePressed()) {
                        }
                        StdDraw.pause(300);

                        double mouseX = StdDraw.mouseX();
                        double mouseY = StdDraw.mouseY();

                        if (!drawCross(mouseX, mouseY, arrPanel)) {
                            gameTurn--; // Decrement game turn if the box was occupied
                        }
                    }
                }
            }

            //return the winner


            // Ask for rematch or terminate
            do {
                StdDraw.setPenColor(Color.MAGENTA);
                StdDraw.text(50, 55, determineWinner(arrPanel) + "wins");
                StdDraw.text(50, 45, "Rematch(R) or Terminate(T)?");
                restartGame = scnr.next().charAt(0);
            } while (restartGame != 'R' && restartGame != 'T');



        } while (restartGame == 'R');
    }

    // Method of drawing a circle, return true if the box is successfully drawn, false if it's occupied
    public static boolean drawCircle(double mouseX, double mouseY, int[][] arrPanel) {
        // Find the center of the square where the mouse was clicked
        int row = (int) (mouseY / 33);
        int col = (int) (mouseX / 33);
        if (arrPanel[row][col] == 0) {
            double xCenter = (2 * col + 1) * 16.5;
            double yCenter = (2 * row + 1) * 16.5;

            arrPanel[row][col] = 1;

            // Draw the circle in the center of the square
            StdDraw.circle(xCenter, yCenter, 16.5);
            return true;
        } else {
            return false;
        }
    }

    // Method to draw a cross, return true if the box is successfully drawn, false if it's occupied
    public static boolean drawCross(double mouseX, double mouseY, int[][] arrPanel) {
        // Find the center of the square where the mouse was clicked
        int row = (int) (mouseY / 33);
        int col = (int) (mouseX / 33);
        if (arrPanel[row][col] == 0) {
            double xCenter = (2 * col + 1) * 16.5;
            double yCenter = (2 * row + 1) * 16.5;

            // Draw the cross in the center of the square
            StdDraw.line(xCenter - 16.5, yCenter + 16.5, xCenter + 16.5, yCenter - 16.5);
            StdDraw.line(xCenter - 16.5, yCenter - 16.5, xCenter + 16.5, yCenter + 16.5);
            arrPanel[row][col] = 2;
            return true;
        } else {
            return false;
        }
    }

    // Method to set up the panel
    public static void panelSetup(int max) {
        // First set canvas scale to [0 max] by [0 max] for the game
        StdDraw.setScale(0, max);

        // Set the radius of the pen
        StdDraw.setPenRadius(0.01);

        StdDraw.setPenColor(Color.black);

        // Draw nine lines to create a board for the game
        StdDraw.line(0, 33, 99, 33);
        StdDraw.line(0, 66, 99, 66);
        StdDraw.line(33, 0, 33, 99);
        StdDraw.line(66, 0, 66, 99);

        StdDraw.line(0, 0, 0, 99);
        StdDraw.line(0, 0, 99, 0);
        StdDraw.line(99, 0, 99, 99);
        StdDraw.line(0, 99, 99, 99);
    }


    public static char determineWinner(int[][] board) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] != 0) {
                    return (board[i][0] == 1) ? 'U' : 'C';
                }
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] != 0) {
                    return (board[0][i] == 1) ? 'U' : 'C';
                }
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] != 0) {
                return (board[0][0] == 1) ? 'U' : 'C';
            }
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] != 0) {
                return (board[0][2] == 1) ? 'U' : 'C';
            }
        }

        // Check for a tie
        boolean isTie = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    isTie = false;
                    break;
                }
            }
        }
        if (isTie) {
            return 'T'; // Tie
        }

        // No winner yet
        return ' ';
    }


}





