import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
public class King extends Piece{

    private boolean hasMoved = false;

    public King(int row, int col, boolean color){
        this.col = col;
        this.row = row;
        this.color = color;
        
    }


    public void updateHasMoved(){
        hasMoved = true;
    }
    public Piece.Type getEnum(){
        return ((color) ? Piece.Type.wKing : Piece.Type.bKing);
    }

    public Piece returnClone(){
        return new King(row, col, color);
    }

    private void checkForCastle(Square[][] board){
        if (!CheckDetector.evaluateCheck(color, board)){
            ArrayList<Square> squareList = new ArrayList<>();
            Square longRookSquare = board[row][0];
            Square shortRookSquare = board[row][7];


            if (shortRookSquare.getPiece() instanceof Rook){
                Rook shortRook = (Rook) shortRookSquare.getPiece();
                if (!shortRook.hasMoved()){
                    squareList.add(board[row][5]);
                    squareList.add(board[row][6]);
                    if (!CheckDetector.squaresUnderAttackOrOccupied(squareList, board, !color)){
                        addSquare(board, row, 6, "Short Castle");
                    }    
                }
            }

            squareList.clear();

            if (longRookSquare.getPiece() instanceof Rook){
                Rook longRook = (Rook) longRookSquare.getPiece();
                if (!longRook.hasMoved() && board[row][1].getPiece() == null){
                    squareList.add(board[row][2]);
                    squareList.add(board[row][3]);
                    if (!CheckDetector.squaresUnderAttackOrOccupied(squareList, board, !color)){
                        addSquare(board, row, 2, "Long Castle");
                    }
                }
            }
        }
    }

    public HashMap<Square, String> getPossibleAttackMoves(Square[][] board){
        possibleMoves.clear();
        addSquare(board, row + 1, col + 1, "Move"); 
        addSquare(board, row, col + 1, "Move");
        addSquare(board, row, col - 1, "Move");
        addSquare(board, row + 1, col - 1, "Move");
        addSquare(board, row + 1, col, "Move");
        addSquare(board, row - 1, col, "Move");
        addSquare(board, row - 1, col + 1, "Move");
        addSquare(board, row - 1, col - 1, "Move");
        
        
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
        return possibleMoves;
    }

    @Override
    public void updatePossibleMoves(Square[][] board){
        addSquare(board, row + 1, col + 1, "Move"); 
        addSquare(board, row, col + 1, "Move");
        addSquare(board, row, col - 1, "Move");
        addSquare(board, row + 1, col - 1, "Move");
        addSquare(board, row + 1, col, "Move");
        addSquare(board, row - 1, col, "Move");
        addSquare(board, row - 1, col + 1, "Move");
        addSquare(board, row - 1, col - 1, "Move");

        if (!hasMoved){
            checkForCastle(board);
        } 
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

}