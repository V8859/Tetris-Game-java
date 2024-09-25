import java.awt.*;

public class TetrisAI {
    private BoardEvaluator evaluator = new BoardEvaluator();

    public Move findBestMove(GameBoard gameBoard, TetrisPiece piece) {
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        // Simulate all rotations and column placements
        for (int rotation = 0; rotation < 4; rotation++) {
            for (int col = 0; col < gameBoard.getBoard()[0].length; col++) {
                TetrisPiece simulatedPiece = piece.clone();  // Simulate piece
                simulatedPiece.rotate(rotation);  // Rotate simulated piece
                int[][] simulatedBoard = simulateDrop(gameBoard.getBoard(), simulatedPiece, col);
                int score = evaluator.evaluateBoard(simulatedBoard);  // Evaluate the board state

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = new Move(col, rotation);
                }
            }
        }
        System. out. println(bestMove);
        return bestMove;
    }

    private int[][] simulateDrop(int[][] board, TetrisPiece piece, int col) {
        int[][] simulatedBoard = copyBoard(board);
        int dropRow = getDropRow(simulatedBoard, piece, col);
        placePiece(simulatedBoard, piece, col, dropRow);
        return simulatedBoard;
    }

    private int getDropRow(int[][] board, TetrisPiece piece, int col) {
        int row = 0;
        // Ensure row doesn't go below zero, and we stop when the piece can't be placed
        while (canPlacePiece(board, piece, col, row)) {
            row++;
        }
        return row - 1;  // Return the last valid row where the piece can be placed
    }

    private boolean canPlacePiece(int[][] board, TetrisPiece piece, int col, int row) {
        for (Point p : piece.getShape()) {
            int x = col + p.x;
            int y = row + p.y;
            if (x < 0 || x >= board[0].length || y < 0 || y >= board.length || board[y][x] != 0) {
                return false;
            }
        }
        return true;
    }

    private void placePiece(int[][] board, TetrisPiece piece, int col, int row) {
        int[][] shape = piece.getCurrentShape();
        Color color = piece.getColor();
        int pieceX = piece.getX();
        int pieceY = piece.getY();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardX = pieceX + j;
                    int boardY = pieceY + i;

                    // Check if the piece is within bounds before placing it
                    if (boardX >= 0 && boardX < row && boardY >= 0 && boardY < col) {
                        board[boardY][boardX] = 1;  // Mark the board with the piece
                    }
                }
            }
        }
    }

    private int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int y = 0; y < board.length; y++) {
            System.arraycopy(board[y], 0, newBoard[y], 0, board[0].length);
        }
        return newBoard;
    }
}
