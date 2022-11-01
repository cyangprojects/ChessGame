public class Square {
    private int x,y;
    private boolean color;
    private Piece piece;

    public Square(int row, int col, boolean color, Piece piece){
        this.x = col;
        this.y = row;
        this.color = color;
        this.piece = piece;
    }

    public boolean getColor(){
        return color;

    } 

    public void removePiece(){
        piece = null;
    }
    public int getCol(){
        return x;
    }
    public int getRow(){
        return y;
    }

    public Piece getPiece(){
        return piece;
    }
    public void addPiece(Piece piece){
        this.piece = piece;
    }
}
