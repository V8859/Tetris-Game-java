import javax.swing.JFrame;
import javax.swing.JPanel;

public class TetrisApp {

    public static void tetrisApp(int boardWidth, int boardHeight) {
        GameBoard gameBoard = new GameBoard(boardWidth, boardHeight);
        GamePanel gamePanel = new GamePanel(gameBoard);
        GameLoop gameLoop = new GameLoop(gameBoard, gamePanel);  // Pass both gameBoard and gamePanel

        // Set up the JFrame for the game
        JFrame frame = new JFrame("Tetris");
        frame.add(gamePanel);
        frame.setSize(400, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add key bindings for player input
        gamePanel.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        gamePanel.getActionMap().put("moveLeft", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("left");
            }
        });

        gamePanel.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        gamePanel.getActionMap().put("moveRight", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("right");
            }
        });

        gamePanel.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("DOWN"), "moveDown");
        gamePanel.getActionMap().put("moveDown", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("down");
            }
        });

        gamePanel.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("UP"), "rotate");
        gamePanel.getActionMap().put("rotate", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("rotate");
            }
        });
    }
}
