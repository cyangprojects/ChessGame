public class Queen extends Piece {
    public Queen(int row, int col, boolean color){
        this.col = col;
        this.row = row;
        this.color = color;
    }

    public Piece.Type getEnum(){
        return ((color) ? Piece.Type.wQueen : Piece.Type.bQueen);
        
    }

    @Override
    public void updatePossibleMoves(Square[][] board){
        updateDiagonals(board);
        updateDownwardMoves(board);
        updateUpwardMoves(board);
        updateRightwardMoves(board);
        updateLeftwardMoves(board);
        
    }

    public Piece returnClone(){
        return new Queen(row, col, color);
    }
}
