import java.awt.*;

public class TetrisPiece implements Cloneable {
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

    // Static factory method to create a Tetris piece based on type
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

    // Get the current shape of the piece as a 2D array
    public int[][] getCurrentShape() {
        return shapes[currentRotation];
    }

    // Convert the current shape to a Point array (used by AI)
    public Point[] getShape() {
        int[][] shape = getCurrentShape();
        Point[] points = new Point[4];  // Tetrominoes always have 4 blocks
        int index = 0;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    points[index] = new Point(j, i);  // Use Point(x, y) for each block
                    index++;
                }
            }
        }
        return points;
    }

    // Rotate the piece by incrementing the rotation index
    public void rotate() {
        currentRotation = (currentRotation + 1) % shapes.length;
    }

    // Rotate the piece to a specific rotation (used by AI)
    public void rotate(int rotations) {
        currentRotation = (currentRotation + rotations) % shapes.length;
    }

    public int getCurrentRotation() {
        return currentRotation;
    }

    public void setRotation(int rotation) {
        currentRotation = rotation;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Reset the position of the piece
    public void resetPosition(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public Color getColor() {
        return color;
    }

    // Implementing clone to return a deep copy of the Tetris piece
    @Override
    public TetrisPiece clone() {
        try {
            TetrisPiece cloned = (TetrisPiece) super.clone();
            cloned.shapes = shapes.clone();  // Deep clone the shapes array
            cloned.color = color;  // Color is immutable, so no need to deep copy
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();  // This should never happen
        }
    }
}
