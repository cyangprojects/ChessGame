import javafx.scene.layout.StackPane;

public class SquarePane extends StackPane {
    private int x, y;
    
    public SquarePane(int row, int col){
        this.x = col;
        this.y = row;
    }

    public int getCol(){
        return x;
    }
    
    public int getRow(){
        return y;
    }

    public String toString(){
        return "Square";
    }
}
