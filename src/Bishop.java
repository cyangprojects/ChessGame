public class Bishop extends Piece {
    public Bishop(int row, int col, boolean color){
        this.col = col;
        this.row = row;
        this.color = color;
    }

    public Piece.Type getEnum(){
        return ((color) ? Piece.Type.wBishop : Piece.Type.bBishop);
        
    }

    @Override
    public void updatePossibleMoves(Square[][] board){
        updateDiagonals(board);
    
    }

    public Piece returnClone(){
        return new Bishop(row, col, color);
    }
}
