public class Rook extends Piece {
    private boolean hasMoved = false;

    public Rook(int row, int col, boolean color){
        this.col = col;
        this.row = row;
        this.color = color;
    }

    public void updateHasMoved(){
        hasMoved = true;
    }

    public Piece.Type getEnum(){
        return ((color) ? Piece.Type.wRook : Piece.Type.bRook);
    }

    @Override
    public void updatePossibleMoves(Square[][] board){
        updateDownwardMoves(board);
        updateLeftwardMoves(board);
        updateRightwardMoves(board);
        updateUpwardMoves(board);
    }

    public boolean hasMoved(){
        return hasMoved;
    }

    public Piece returnClone(){
        return new Rook(row, col, color);
    }
    
}
