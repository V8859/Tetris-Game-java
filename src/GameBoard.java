import java.awt.*;

public class GameBoard {
    private int width, height;
    private int[][] board; // 2D array representing the board
    private TetrisPiece currentPiece;
    private int TotalScore;
    private Color[][] colors;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[height][width];
        this.colors = new Color[height][width];
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
        }

        // Move all rows above down by one
        for (int r = row; r > 0; r--) {
            board[r] = board[r - 1].clone();
        }
        board[0] = new int[width]; // Clear the top row
    }

    public void clearFullLines() {
        int multiplier = 0;
        int BaseScore = 100;
        for (int row = 0; row < height; row++) {
            if (checkLineFull(row)) {
                multiplier++;
                clearLine(row);
            }
        }
        this.TotalScore += BaseScore * multiplier;
    }

    public int getScore(){
        return TotalScore;
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

    public boolean spawnNewPiece() {
        String[] pieceTypes = {"I", "O", "T", "L", "J", "S", "Z"};
        String randomType = pieceTypes[(int) (Math.random() * pieceTypes.length)];
        TetrisPiece newPiece = TetrisPiece.createPiece(randomType);
        newPiece.resetPosition(width / 2 - 1, 0); // Start in the middle top of the board
        this.currentPiece = newPiece;
        if (!canPlacePiece(currentPiece)) {return false;}
        return true;
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
