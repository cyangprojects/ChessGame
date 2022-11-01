import java.util.HashMap;
public class Piece {
    int col, row;
    boolean color;
    HashMap<Square, String> possibleMoves = new HashMap<Square,String>();
    public enum Type {
        wPawn, bPawn, wRook, bRook, wKnight, bKnight, wBishop, bBishop, wQueen, bQueen, wKing, bKing
    }

    public void updateHasMoved(){}

    public Piece.Type getEnum(){
        return Type.wPawn;
    }
    
    public boolean getColor(){
        return color;
    }

    public void updateLocation(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Piece returnClone(){
        return new King(row, col, color);
    }

    public int getCol(){
        return col;
    }

    public int getRow(){
        return row;
    }

    public void updatePossibleMoves(Square[][] board){ //to be overridden
    }

    public HashMap<Square, String> getPossibleMoves(Square[][] board){
        possibleMoves.clear();
        updatePossibleMoves(board);
        return possibleMoves;
    } 
    public void addSquare(Square[][] board, int row, int col, String move){
        if ((col >= 0 && col < Board.BOARD_WIDTH) && (row >= 0 && row < Board.BOARD_HEIGHT)){
            possibleMoves.put(board[row][col], move);
        }
    }


    public void updateLeftwardMoves(Square[][] board){
        for (int i = col - 1; i >= 0 ; i--){
            Square square = board[row][i];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {
                    addSquare(board, row, i, "Move");
                    break;
                }
            }
            addSquare(board, row, i, "Move");
        }
    }

    public void updateRightwardMoves(Square[][] board){
        for (int i = col + 1; i < Board.BOARD_WIDTH ; i++){
            Square square = board[row][i];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {
                    addSquare(board, row, i, "Move");
                    break;
                }
            }
            addSquare(board, row, i, "Move");
        }
    }

    public void updateDownwardMoves(Square[][] board){
        for (int i = row - 1; i >= 0 ; i--){
            Square square = board[i][col];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {
                    addSquare(board, i, col, "Move");
                    break;
                }
            }
            addSquare(board, i, col, "Move");
        }
    }

    public void updateUpwardMoves(Square[][] board){
        for (int i = row + 1; i < Board.BOARD_HEIGHT ; i++){
            Square square = board[i][col];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {
                    addSquare(board, i, col, "Move");
                    break;
                }
            }
            addSquare(board, i, col, "Move");
        }
    }

    public void updateDiagonals(Square[][] board){

        //Check bottom right
        for (int i = row + 1, j = col + 1; i < Board.BOARD_HEIGHT && j < Board.BOARD_WIDTH; i++, j++ ){
            Square square = board[i][j];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {    
                    addSquare(board, i, j, "Move");
                    break;
                }
            }
            addSquare(board, i, j, "Move");
        }

        //Check bottom left
        for (int i = row + 1, j = col - 1; i < Board.BOARD_HEIGHT && j >= 0; i++, j-- ){
            Square square = board[i][j];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {    
                    addSquare(board, i, j, "Move");
                    break;
                }
            }
            addSquare(board, i, j, "Move");
        }

        // Check top left
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j-- ){
            Square square = board[i][j];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {    
                    addSquare(board, i, j, "Move");
                    break;
                }
            }
            addSquare(board, i, j, "Move");
        }

        //Check top right

        for (int i = row - 1, j = col + 1; i >= 0 && j < Board.BOARD_WIDTH; i--, j++ ){
            Square square = board[i][j];
            if (square.getPiece() != null){
                if (square.getPiece().getColor() == color){
                    break;
                } else {    
                    addSquare(board, i, j, "Move");
                    break;
                }
            }
            addSquare(board, i, j, "Move");
        }
    }






    
}
