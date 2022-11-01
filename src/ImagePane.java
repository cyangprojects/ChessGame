import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class ImagePane extends ImageView {
    private int x, y;
    
    public ImagePane(Image image, int row, int col){
        super(image);
        x = col;
        y = row;
        
    }
    public int getCol(){
        return x;
    }

    public void setRow(int row){
        y = row;
    }
    
    public void setCol(int col){
        x = col;
    }
    public int getRow(){
        return y;
    }

    public String toString(){
        return "Piece";
    }
}
