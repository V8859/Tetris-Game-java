public class BoardEvaluator {
    public int evaluateBoard(int[][] board) {
        int heightScore = getHeight(board);
        int holesScore = getHoles(board);
        int linesCleared = getClearedLines(board);
        int bumpinessScore = getBumpiness(board);
        int adjuster = 1;
        if (linesCleared == 1){
            adjuster = 100;
        } else if (linesCleared == 2) {
            adjuster = 300;

        } else if (linesCleared ==3) {
            adjuster = 600;

        } else if (linesCleared >= 4) {
            adjuster = 1000;
        }
//        System.out.println(heightScore);
        return (-4 * heightScore) + ((5 * linesCleared)+(adjuster)) - (15 * holesScore) - (2 * bumpinessScore);
    }

    private int getHeight(int[][] board) {
        int height = 0;
//        System.out.println(board[0].length + " Width");
//        System.out.println(board.length + " Height");
        for (int x = 0; x < board[0].length; x++) {
            for (int y = 0; y < board.length; y++) {
                if (board[y][x] != 0) {
                    height = Math.max(height, board.length - y);
                    break;
                }
            }
        }
        return height;
    }

    private int getHoles(int[][] board) {
        int holes = 0;
        for (int x = 0; x < board[0].length; x++) {
            boolean foundBlock = false;
            for (int y = 0; y < board.length; y++) {
                if (board[y][x] != 0) {
                    foundBlock = true;
                } else if (foundBlock && board[y][x] == 0) {
                    holes++;
                }
            }
        }
        return holes;
    }

    private int getClearedLines(int[][] board) {
        int clearedLines = 0;
        for (int y = 0; y < board.length; y++) {
            boolean isLineFull = true;
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] == 0) {
                    isLineFull = false;
                    break;
                }
            }
            if (isLineFull) {
                clearedLines++;
            }
        }
        return clearedLines;
    }

    private int getBumpiness(int[][] board) {
        int bumpiness = 0;
        for (int x = 0; x < board[0].length - 1; x++) {
            int colHeight1 = getColumnHeight(board, x);
            int colHeight2 = getColumnHeight(board, x + 1);
            bumpiness += Math.abs(colHeight1 - colHeight2);
        }
        return bumpiness;
    }

    private int getColumnHeight(int[][] board, int col) {
        for (int y = 0; y < board.length; y++) {
            if (board[y][col] != 0) {
                return board.length - y;
            }
        }
        return 0;
    }
}
