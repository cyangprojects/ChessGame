import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
public class CheckDetector {
    public static ArrayList<Square> createAttackMap(boolean color, Square[][] board){
        Set<Square> attackSet = new HashSet<>(); //Dictionary of pieces that are square
        for (Square[] squareList : board){
            for (Square square : squareList){
                if (square.getPiece() == null){
                    continue;
                } else if (square.getPiece().getColor() != color){
                    continue;
                } else if (square.getPiece() instanceof Pawn){
                    attackSet.addAll(((Pawn) square.getPiece()).getPossibleAttackMoves(board).keySet());
                }else if (square.getPiece() instanceof King) {
                    attackSet.addAll(((King) square.getPiece()).getPossibleAttackMoves(board).keySet());
                }else{
                    attackSet.addAll(square.getPiece().getPossibleMoves(board).keySet());
                }

                }
        }
        ArrayList<Square> attackMap = new ArrayList<>();
        attackMap.clear();
        for (Square square : attackSet){
            attackMap.add(square);
         }
         return attackMap;
    }

    

    public static boolean squaresUnderAttackOrOccupied(ArrayList<Square> squareList, Square[][] board, boolean color){
        ArrayList<Square> attackMap = createAttackMap(color, board);
        boolean underAttack = false;

        for (Square square : squareList){
            if (attackMap.contains(square) || square.getPiece() != null){
                underAttack = true;
                
            }
        }
        return underAttack;
    }

    public static boolean evaluateCheck(boolean color, Square[][] board){ //TAKES IN A "FAKE" BOARD STATE WITH A POSSIBLE MOVE
        for (Square[] squareList : board){
            for (Square square : squareList){
                if (square.getPiece() == null){
                    continue;
                }
                if (square.getPiece() instanceof King && square.getPiece().getColor() == color){
                    boolean opponentColor = !color;
                    return createAttackMap(opponentColor, board).contains(square);
                }
            }
        }
        return false;
    
    }
}
