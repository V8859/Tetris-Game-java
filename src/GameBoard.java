public class GameBoard {
    private int width, height;
    private int[][] board; // 2D array representing the board
    private TetrisPiece currentPiece;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[height][width];
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
        int pieceX = piece.getX();
        int pieceY = piece.getY();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    board[pieceY + i][pieceX + j] = 1; // Mark the board with the piece
                }
            }
        }
    }

    public boolean checkLineFull(int row) {
        for (int col = 0; col < width; col++) {
            if (board[row][col] == 0) {
                return false;
            }
        }
        return true;
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
        for (int row = 0; row < height; row++) {
            if (checkLineFull(row)) {
                clearLine(row);
            }
        }
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
            currentPiece.moveDown(); // Place the piece
            placePiece(currentPiece);
            clearFullLines();
            return false;
        }
        return true;
    }

    public void rotatePiece() {
        currentPiece.rotate();
        if (!canPlacePiece(currentPiece)) {
            // Undo rotation if it collides
            for (int i = 0; i < 3; i++) {
                currentPiece.rotate();
            }
        }
    }

    public void spawnNewPiece() {
        String[] pieceTypes = {"I", "O", "T", "L", "J", "S", "Z"};
        String randomType = pieceTypes[(int) (Math.random() * pieceTypes.length)];
        TetrisPiece newPiece = TetrisPiece.createPiece(randomType);
        newPiece.resetPosition(width / 2 - 1, 0);
        this.currentPiece = newPiece;
    }

}
