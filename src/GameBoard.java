import java.awt.*;
import java.util.Random;

public class GameBoard {
    private int width, height;
    private int[][] board; // 2D array representing the board
    private TetrisPiece currentPiece;
    private TetrisPiece nextPiece;
    private int TotalScore;
    private Color[][] colors;
    private Random random;
    private int lines;

    public GameBoard(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.board = new int[height][width];
        this.colors = new Color[height][width];
        this.random  = new Random(seed);
        this.nextPiece = generateRandomPiece();
        spawnNewPiece();  // Spawn the first piece when the game board is created
    }


    public boolean canPlacePiece(TetrisPiece piece) {
        int[][] shape = piece.getCurrentShape();
        int pieceX = piece.getX();
        int pieceY = piece.getY();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardX = pieceX + j;
                    int boardY = pieceY + i;

                    // Check if the piece is out of bounds or collides with existing blocks
                    if (boardX < 0 || boardX >= width || boardY >= height || board[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placePiece(TetrisPiece piece) {
        int[][] shape = piece.getCurrentShape();
        Color color  = piece.getColor();
        int pieceX = piece.getX();
        int pieceY = piece.getY();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    board[pieceY + i][pieceX + j] = 1; // Mark the board with the piece
                    colors[pieceY+i][pieceX+j]= color;
                }
            }
        }
    }

    public void clearLine(int row) {
        for (int col = 0; col < width; col++) {
            board[row][col] = 0; // Clear the row
            colors[row][col] = null; // Clear the colors
        }

        // Move all rows above down by one
        for (int r = row; r > 0; r--) {
            board[r] = board[r - 1].clone();
            colors[r] = colors[r - 1].clone(); // Clone the colors array as well
        }
        board[0] = new int[width]; // Clear the top row
        colors[0] = new Color[width]; // Clear the colors of the top row
    }

    public void clearFullLines() {
        int multiplier = 0;
        int baseScore = 100;
        for (int row = 0; row < height; row++) {
            if (checkLineFull(row)) {
                multiplier++;
                lines++;
                clearLine(row);
            }
        }
        System.out.println(multiplier);
        if (multiplier == 1){
            this.TotalScore += baseScore;
        }else if(multiplier == 2){
            this.TotalScore += 300;
        } else if (multiplier == 3) {
            this.TotalScore += 600;
        } else if (multiplier == 4) {
            this.TotalScore += 1000;
        }
    }

    public int getScore(){
        return TotalScore;
    }
    public int getLines(){
        return lines;
    }

    public boolean checkLineFull(int row) {
        for (int col = 0; col < width; col++) {
            if (board[row][col] == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        // Check if any blocks are in the top row
        for (int col = 0; col < width; col++) {
            if (board[0][col] != 0) {
                return true;
            }
        }
        return false;
    }

    // Generates a random Tetris piece
    private TetrisPiece generateRandomPiece() {
        String[] pieceTypes = {"I", "O", "T", "L", "J", "S", "Z"};
        String randomType = pieceTypes[random.nextInt(pieceTypes.length)];
        return TetrisPiece.createPiece(randomType);
    }

    // Spawn the next piece as the current piece and generate a new next piece
    public boolean spawnNewPiece() {
        this.currentPiece = this.nextPiece;  // The current piece becomes the next piece
        this.currentPiece.resetPosition(width / 2 - 1, 0);  // Start in the middle top of the board
        this.nextPiece = generateRandomPiece();
        if (!canPlacePiece(currentPiece)) {
            return false;  // Game over condition
        }
        return true;
    }

    public TetrisPiece getNextPiece() {
        return nextPiece;
    }

    public boolean movePieceLeft() {
        currentPiece.moveLeft();
        if (!canPlacePiece(currentPiece)) {
            currentPiece.moveRight(); // Undo move
            return false;
        }
        return true;
    }

    public boolean movePieceRight() {
        currentPiece.moveRight();
        if (!canPlacePiece(currentPiece)) {
            currentPiece.moveLeft(); // Undo move
            return false;
        }
        return true;
    }

    public boolean movePieceDown() {
        currentPiece.moveDown();
        if (!canPlacePiece(currentPiece)) {
            currentPiece.moveUp(); // Undo move
            placePiece(currentPiece);
            clearFullLines();
            return false;
        }
        return true;
    }

    public void rotatePiece() {
        TetrisPiece currentPiece = this.currentPiece;

        // Save the current state
        int originalRotation = currentPiece.getCurrentRotation();
        int originalX = currentPiece.getX();
        int originalY = currentPiece.getY();

        currentPiece.rotate();

        // Check if the rotation is valid
        if (!canPlacePiece(currentPiece)) {
            // Wall kick attempts: try moving the piece left, right, and up
            boolean successfulKick = false;
            int[][] wallKickOffsets = {
                    {1, 0},  // Kick right
                    {-1, 0}, // Kick left
                    {0, 1},  // Kick down (moving up in the grid)
                    {0, -1},  // Kick up
                    {2, 0}  //special case I push right twice
            };

            for (int[] offset : wallKickOffsets) {
                currentPiece.setX(originalX + offset[0]);
                currentPiece.setY(originalY + offset[1]);

                if (canPlacePiece(currentPiece)) {
                    successfulKick = true;
                    break;
                }
            }

            // If all wall kicks failed, undo the rotation
            if (!successfulKick) {
                currentPiece.setRotation(originalRotation);
                currentPiece.setX(originalX);
                currentPiece.setY(originalY);
            }
        }
    }
    public boolean canMovePieceDown() {
        currentPiece.moveDown();
        boolean canMove = canPlacePiece(currentPiece);
        currentPiece.moveUp(); // Restore the original position
        return canMove;
    }

    public TetrisPiece getCurrentPiece() {
        return currentPiece;
    }

    public int[][] getBoard() {
        return board;
    }

    public Color[][] getColors(){
        return colors;
    }
}
