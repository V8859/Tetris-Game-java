public class TetrisPiece {
    private int[][][] shapes; // 3D array for shapes (rotations)
    private int currentRotation;
    private int x, y; // Position on the board

    public TetrisPiece(int[][][] shapes) {
        this.shapes = shapes;
        this.currentRotation = 0;
        this.x = 0; // Initial position
        this.y = 0;
    }

    public static TetrisPiece createPiece(String type) {
        switch (type) {
            case "T":
                return new TetrisPiece(AllPieces.T_SHAPE);
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

    // Getter methods for x and y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Method to reset the piece's position
    public void resetPosition(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
}

