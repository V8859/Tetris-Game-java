import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class TetrisApp extends JPanel {

    public TetrisApp(int boardWidth, int boardHeight){
        GameBoard gameBoard = new GameBoard(boardWidth, boardHeight);
        GamePanel gamePanel = new GamePanel(gameBoard);
        GameLoop gameLoop = new GameLoop(gameBoard, gamePanel);  // Pass both gameBoard and gamePanel

        this.setLayout(new BorderLayout());
        this.add(gamePanel);



        // Add key bindings for player input
        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        this.getActionMap().put("moveLeft", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("left");
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        this.getActionMap().put("moveRight", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("right");
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("DOWN"), "moveDown");
        this.getActionMap().put("moveDown", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("down");
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("UP"), "rotate");
        this.getActionMap().put("rotate", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("rotate");
            }
        });

    this.setFocusable(true);
    this.requestFocusInWindow();
    this.setVisible(true);
    }

}
