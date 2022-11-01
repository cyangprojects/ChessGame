import java.util.Iterator;
import java.util.HashMap;
public class Pawn extends Piece {

    public Pawn(int row, int col, boolean color){
        this.col = col;
        this.row = row;
        this.color = color;
    }

    public Piece.Type getEnum(){
        return ((color) ? Piece.Type.wPawn : Piece.Type.bPawn);
        
    }

    public HashMap<Square, String> getPossibleAttackMoves(Square[][] board){
        possibleMoves.clear();
        if (color){
            addSquare(board, row-1, col + 1, "Move");
            addSquare(board, row-1, col - 1, "Move");
        } else {
            addSquare(board, row + 1, col + 1, "Move");
            addSquare(board, row + 1, col - 1, "Move");
        }
        return possibleMoves;
    }

    public HashMap<Square, String> getPossibleMovesEnPassant(Square[][] board, Piece pawn){
        HashMap<Square,String> moves = this.getPossibleMoves(board);
        if (pawn != null){
            if (pawn.getRow() == row && Math.abs(col - pawn.getCol()) == 1 && color != pawn.getColor()){
                moves.put(board[row + ((color) ? -1 : 1)][pawn.getCol()], "En Passant");   
            }

        }
        return moves;
    }


    @Override 
    public void updatePossibleMoves(Square[][] board){

        if (color){
            if (row == 1){
                addSquare(board, row - 1, col, "Promote");
                addSquare(board, row - 1, col - 1, "Promote");
                addSquare(board, row -1, col + 1, "Promote");
            } else {
                addSquare(board, row -1 , col, "Move");
                addSquare(board, row - 1, col - 1, "Move");
                addSquare(board, row -1, col + 1, "Move");
                if (row == 6){
                    addSquare(board, row -2 , col, "Pawn Jump");
            }
            }
            
            
            
        } else {
            if (row == 6){
                addSquare(board, row + 1, col, "Promote");
                addSquare(board, row + 1, col + 1, "Promote");
                addSquare(board, row + 1, col - 1, "Promote");    
            } else {
                addSquare(board, row + 1, col, "Move");
                addSquare(board, row + 1, col + 1, "Move");
                addSquare(board, row + 1, col - 1, "Move");
                if (row == 1){
                    addSquare(board, row + 2, col, "Pawn Jump");
                }
        }
        }   
        Iterator<Square> itr = possibleMoves.keySet().iterator();
         while (itr.hasNext()){
            Square square = itr.next();
            if (square.getCol() == col){
                if (square.getPiece() != null){
                    itr.remove();
                    continue;
                }
                continue;
            } else {
                if (square.getPiece() == null){
                    itr.remove();
                    continue;
                } else if (square.getPiece().getColor() == color){
                    itr.remove();
                }
            }
            

        }

    }


    public Piece returnClone(){
        return new Pawn(row, col, color);
    }
}

