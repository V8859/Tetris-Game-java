import java.awt.*;

public class TetrisPiece {
    private int[][][] shapes; // 3D array for shapes (rotations)
    private int currentRotation;
    private int x, y;
    private Color color;

    public TetrisPiece(int[][][] shapes, Color color) {
        this.shapes = shapes;
        this.currentRotation = 0;
        this.x = 0;
        this.y = 0;
        this.color = color;
    }

    public static TetrisPiece createPiece(String type) {
        switch (type) {
            case "I":
                return new TetrisPiece(AllPieces.I_SHAPE, Color.CYAN);
            case "O":
                return new TetrisPiece(AllPieces.O_SHAPE, Color.YELLOW);
            case "T":
                return new TetrisPiece(AllPieces.T_SHAPE, Color.MAGENTA);
            case "L":
                return new TetrisPiece(AllPieces.L_SHAPE, Color.ORANGE);
            case "J":
                return new TetrisPiece(AllPieces.J_SHAPE, Color.BLUE);
            case "S":
                return new TetrisPiece(AllPieces.S_SHAPE, Color.GREEN);
            case "Z":
                return new TetrisPiece(AllPieces.Z_SHAPE, Color.RED);
            default:
                throw new IllegalArgumentException("Unknown piece type");
        }
    }

    public int[][] getCurrentShape() {
        return shapes[currentRotation];
    }

    public void rotate() {
        currentRotation = (currentRotation + 1) % shapes.length;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public void moveUp() {
        y--;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void resetPosition(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
    public Color getColor(){
        return color;
    }
}
