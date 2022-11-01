import java.util.Iterator;
public class Knight extends Piece {
    public Knight(int row, int col, boolean color){
        this.col = col;
        this.row = row;
        this.color = color;
    }

    public Piece.Type getEnum(){
        return ((color) ? Piece.Type.wKnight : Piece.Type.bKnight);
        
    }
    @Override
    public void updatePossibleMoves(Square[][] board){
        addSquare(board, row + 1, col - 2, "Move");
        addSquare(board, row - 1, col - 2, "Move");
        addSquare(board, row + 2, col - 1, "Move");
        addSquare(board, row - 2, col - 1, "Move");
        addSquare(board, row + 2, col + 1, "Move");
        addSquare(board, row + 1, col + 2, "Move");
        addSquare(board, row - 1, col + 2, "Move");
        addSquare(board, row - 2, col + 1, "Move");

        //Using Iterator to avoid ConcurrentModificationException
        Iterator<Square> itr = possibleMoves.keySet().iterator();
         while (itr.hasNext()){
            Square square = itr.next();
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    itr.remove();
                }
            }
            

        }
    }
    public Piece returnClone(){
        return new Knight(row, col, color);
    }
}
