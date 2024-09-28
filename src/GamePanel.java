import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private GameBoard gameBoard;
    private final int cellSize = 25;  // Each cell is 30x30 pixels
    private final int scoreHeight = 50;  // Space reserved for the score display
    private int score;
    private int level;
    private int initialLevel;
    private int lines;
    private String playerTag;
    private String playerType;
    private boolean gameOver;
    private Color currentColor;
    private int partialMoveStep;
    private int totalSteps;
    private String FontType = "HelveticaNeue";
    private GameLoop gameLoop;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private HighScoreManager highScoreManager;

    public GamePanel(GameLoop gameLoop, JPanel cardPanel, CardLayout cardLayout, HighScoreManager highScoreManager) {
        this.gameLoop = gameLoop;
        this.gameLoop = gameLoop;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.highScoreManager = highScoreManager;
        this.gameBoard = new GameBoard(10, 20, System.currentTimeMillis());
        this.score = 0;
        this.level = 1;
        this.lines = 0;
        this.initialLevel = 1;
        this.playerTag = "Player1";
        this.gameOver = false;
        this.partialMoveStep = 0;
        this.totalSteps = 24;
        setFocusable(true);
        requestFocusInWindow();

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setPartialMove(int step, int totalSteps) {
        this.partialMoveStep = step;
        this.totalSteps = totalSteps;
    }

    private void drawPiece(Graphics g, int[][] piece, int x, int y, Color color) {
        g.setColor(color);
        int xOffset = getXOffset();
        int yOffset = getYOffset();
        int margin = 1;

        // Calculate the partial offset for smoother movement
        int partialYOffset = (int) ((cellSize * partialMoveStep) / (double) totalSteps);

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != 0) {
                    int xPos = xOffset + (x + j) * cellSize + margin;
                    int yPos = yOffset + (y + i) * cellSize + margin + partialYOffset;

                    int size = cellSize - 2 * margin;
                    g.fillRect(xPos, yPos, size, size);

                    // Add additional styling for a 3D effect
                    int smallSize = (size + 22) / 2;
                    int smallXPos = xPos + (size - smallSize) / 2;
                    int smallYPos = yPos + (size - smallSize) / 2;
                    g.setColor(Color.black.brighter().darker().brighter().brighter());
                    g.fillRect(smallXPos, smallYPos, smallSize, smallSize);

                    smallSize = (size + 18) / 2;
                    smallXPos = xPos + (size - smallSize) / 2;
                    smallYPos = yPos + (size - smallSize) / 2;
                    g.setColor(color.darker().darker());
                    g.fillRect(smallXPos, smallYPos, smallSize, smallSize);
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawComplexBackground(g);
        drawBoard(g);
        drawScore(g);
        drawNextPiece(g);

        TetrisPiece currentPiece = gameBoard.getCurrentPiece();
        if (currentPiece != null) {
            drawPiece(g, currentPiece.getCurrentShape(), currentPiece.getX(), currentPiece.getY(), currentPiece.getColor());
        }

        if (gameOver) {
            displayGameOver(g);
            checkHighScoreAndUpdate();
        }
    }

    private void displayGameOver(Graphics g) {
        g.setColor(new Color(255, 0, 0, 122));
        g.setFont(new Font("Monospaced", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String gameOverText = "GAME OVER";
        int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
        int y = getHeight() / 2;
        g.drawString(gameOverText, x, y);
    }

    private void checkHighScoreAndUpdate() {
        List<HighScore> highScores = highScoreManager.getHighScores();

        // Check if the current score qualifies for top 10
        if (highScores.size() < 10 || score > highScores.get(highScores.size() - 1).getScore()) {
            // Ask for the player's name
            String playerName = JOptionPane.showInputDialog(null, "Enter your name:", "New High Score!", JOptionPane.PLAIN_MESSAGE);

            if (playerName != null && !playerName.trim().isEmpty()) {
                // Add the new high score
                GameConfig config = new GameConfig(
                        gameBoard.getWidth(),          // fieldWidth
                        gameBoard.getHeight(),         // fieldHeight
                        1,                             // initLevel (adjust as needed)
                        true,                          // isMusicOn
                        true,                          // isSoundOn
                        false,                         // isExtendMode
                        1,                             // playerOneType
                        0                              // playerTwoType (optional)
                );
                highScoreManager.addScore(playerName, score, config);

                // Save the updated high scores to JSON
                highScoreManager.saveHighScores();

                // Show the high score screen after updating
                showHighScores();
            }
        }
    }


    private void showHighScores() {
        HighScoreScreen highScoreScreen = new HighScoreScreen(cardPanel, cardLayout);
        cardPanel.add(highScoreScreen, "High Scores");
        cardLayout.show(cardPanel, "High Scores");
    }

    // Drawing methods (for the Tetris game)
    private void drawBoard(Graphics g) {
        int xOffset = getXOffset();
        int yOffset = getYOffset();
        int[][] board = gameBoard.getBoard();
        Color[][] colors = gameBoard.getColors();

        g.setColor(Color.GRAY);
        for (int x = 0; x <= board[0].length; x++) {
            g.drawLine(xOffset + x * cellSize, yOffset, xOffset + x * cellSize, yOffset + board.length * cellSize);
        }
        for (int y = 0; y <= board.length; y++) {
            g.drawLine(xOffset, yOffset + y * cellSize, xOffset + board[0].length * cellSize, yOffset + y * cellSize);
        }

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] != 0) {
                    int xPos = xOffset + x * cellSize;
                    int yPos = yOffset + y * cellSize;
                    g.setColor(colors[y][x]);
                    g.fillRect(xPos, yPos, cellSize, cellSize);
                    drawBlock3D(g, xPos, yPos, colors[y][x]);
                }
            }
        }
    }

    private void drawBlock3D(Graphics g, int x, int y, Color color) {
        int size = cellSize;
        int smallSize = (size + 22) / 2;
        int smallXPos = x + (size - smallSize) / 2;
        int smallYPos = y + (size - smallSize) / 2;

        g.setColor(Color.black.brighter().darker().brighter().brighter());
        g.fillRect(smallXPos, smallYPos, smallSize, smallSize);

        smallSize = (size + 18) / 2;
        smallXPos = x + (size - smallSize) / 2;
        smallYPos = y + (size - smallSize) / 2;

        g.setColor(color.darker().darker());
        g.fillRect(smallXPos, smallYPos, smallSize, smallSize);
    }

    private void drawNextPiece(Graphics g) {
        TetrisPiece nextPiece = gameBoard.getNextPiece();
        if (nextPiece != null) {
            int[][] shape = nextPiece.getCurrentShape();
            Color color = nextPiece.getColor();

            int xOffset = 40;
            int yOffset = 220;
            int adjustY = getYOffset();
            yOffset += adjustY;

            g.setColor(color);
            int margin = 1;
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] != 0) {
                        g.fillRect(
                                xOffset + j * cellSize + margin,
                                yOffset + i * cellSize + margin,
                                cellSize - 2 * margin,
                                cellSize - 2 * margin
                        );
                    }
                }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font(FontType, Font.BOLD, 15));
            g.drawString("Next Piece:", 14, 210 + adjustY);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(FontType, Font.BOLD, 13));
        int yOffset = getYOffset();
        g.drawString("Score: " + score, 14, 180 + yOffset);
        g.drawString("Level: " + level, 14, 150 + yOffset);
        g.drawString("Lines Erased: " + lines, 14, 120 + yOffset);
        g.drawString("Player: " + playerTag, 14, 90 + yOffset);
    }

    private void drawComplexBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int y1Offset = getYOffset();

        Color[] curr = getColorScheme(level);
        Color color1 = curr[0];
        Color color2 = curr[1];
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.white.darker());
        g2d.fillRect(6, y1Offset, 150, 500);
        g2d.setColor(Color.black.darker());
        g2d.fillRect(9, y1Offset + 4, 148, 498);

        g2d.setColor(Color.gray.brighter().darker().brighter().darker());
        g2d.fillRect(9, y1Offset + 4, 145, 495);
    }

    private int getXOffset() {
        int panelWidth = getWidth();
        return (panelWidth - gameBoard.getBoard()[0].length * cellSize) / 2;
    }

    private int getYOffset() {
        return scoreHeight;  // Position the board just below the score
    }

    private static Color[] getColorScheme(int level) {
        return switch (level) {
            case 1 -> new Color[]{new Color(3, 2, 252), new Color(140, 255, 244)};
            case 2 -> new Color[]{new Color(42, 0, 213), new Color(140, 237, 255)};
            case 3 -> new Color[]{new Color(99, 0, 158), new Color(140, 196, 255)};
            case 4 -> new Color[]{new Color(161, 1, 93), new Color(140, 156, 255)};
            case 5 -> new Color[]{new Color(216, 0, 39), new Color(165, 140, 255)};
            case 6 -> new Color[]{new Color(254, 0, 2), new Color(180, 130, 255)};
            case 7 -> new Color[]{new Color(254, 0, 2).darker(), new Color(180, 130, 255).darker()};
            case 8 -> new Color[]{new Color(254, 0, 2).darker().darker(), new Color(180, 130, 255).darker().darker()};
            case 9 -> new Color[]{new Color(254, 0, 2).darker().darker().darker(), new Color(180, 130, 255).darker().darker().darker()};
            default -> new Color[]{new Color(254, 0, 2).darker().darker().darker().darker(), new Color(180, 130, 255).darker().darker().darker().darker()};
        };
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setInitialLevel(int level) {
        this.initialLevel = level;
    }

    public void setPlayerTag(String playerTag) {
        this.playerTag = playerTag;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
}
