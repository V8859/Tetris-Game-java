import java.util.Arrays;
public class PureGame {
    private int width;
    private int height;
    private int[][] cells;
    private int[][] currentShape;
    private int[][] nextShape;
    private static PureGame INSTANCE;
    private PureGame(){

    }public static PureGame getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new PureGame();
        }
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "PureGame{" +
                "width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.deepToString(cells) +
                ", currentShape=" + Arrays.deepToString(currentShape) +
                ", nextShape=" + Arrays.deepToString(nextShape) +
                '}';
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public void setCurrentShape(int[][] currentShape) {
        this.currentShape = currentShape;
    }
    public void setNextShape(int [][] nextShape){
        this.nextShape =nextShape;
    }
}