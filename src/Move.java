import javafx.scene.image.*;
public class Move {
    

    



    public static void movePiece(Piece piece, Square targetSquare, String move, SquarePane[][] panes, Square[][] board){
        if (move == "Move" || move == "Pawn Jump"){
            GUIMoveOrAttack(piece, targetSquare, panes, board);
            moveOrAttack(piece, targetSquare, board);         
        } else if (move == "Promote"){
            GUIPromote(piece, targetSquare, panes, board);
            promotePiece(piece, targetSquare, board);
        } else if (move == "Short Castle") {
            GUIShortCastle(piece, targetSquare, panes, board);
            shortCastle(piece, targetSquare, board);
        } else if (move == "Long Castle"){
            GUILongCastle(piece, targetSquare, panes, board);
            longCastle(piece, targetSquare, board);
        } else if (move == "En Passant"){
            GUIEnPassant(piece, targetSquare, panes, board);
            EnPassant(piece, targetSquare, board);
        }
        
    }
    

    public static void GUIEnPassant(Piece piece, Square targetSquare, SquarePane[][] panes, Square[][] board){
        GUIMoveOrAttack(piece, targetSquare, panes, board);
        SquarePane EnPassantPawnPane = panes[piece.getRow()][targetSquare.getCol()];
        EnPassantPawnPane.getChildren().remove(0); 
    }

    public static void EnPassant(Piece piece, Square targetSquare, Square[][] board){
        Square EnPassantSquare = board[piece.getRow()][targetSquare.getCol()];
        moveOrAttack(piece, targetSquare, board);
        EnPassantSquare.removePiece();
    }


    public static void GUIMoveOrAttack(Piece piece, Square targetSquare, SquarePane[][] panes, Square[][] board){
        SquarePane targetPane = panes[targetSquare.getRow()][targetSquare.getCol()];
        SquarePane originalPane = panes[piece.getRow()][piece.getCol()];
        ImagePane piecePane = (ImagePane) originalPane.getChildren().get(0);
        piecePane.setRow(targetPane.getRow());
        piecePane.setCol(targetPane.getCol());
        if (targetSquare.getPiece() != null){
            targetPane.getChildren().remove(0);
       }
       targetPane.getChildren().add(piecePane);
    }

    public static void moveOrAttack(Piece piece, Square targetSquare, Square[][] board){
        Square originalSquare = board[piece.getRow()][piece.getCol()];
        originalSquare.removePiece();
        targetSquare.addPiece(piece);
        piece.updateLocation(targetSquare.getRow(), targetSquare.getCol());
    }


    public static void longCastle(Piece piece, Square targetSquare, Square[][] board){
        Square longRookSquare = board[piece.getRow()][0];
        Rook longRook = (Rook) longRookSquare.getPiece();
        Square kingSquare = board[piece.getRow()][piece.getCol()];
        Square TARGET_OF_KING = board[piece.getRow()][2];
        Square TARGET_OF_ROOK = board[piece.getRow()][3];
        longRookSquare.removePiece();
        TARGET_OF_KING.addPiece(piece);
        kingSquare.removePiece();
        TARGET_OF_ROOK.addPiece(longRook);
        longRook.updateLocation(piece.getRow(), 3);
        piece.updateLocation(targetSquare.getRow(), 2);
    }

    public static void GUILongCastle(Piece piece, Square targetSquare, SquarePane[][] panes, Square[][] board){
        int r = piece.getRow();
        SquarePane rookPane = panes[r][0];
        SquarePane kingPane = panes[r][4];
        SquarePane TARGET_OF_KING = panes[r][2];
        SquarePane TARGET_OF_ROOK = panes[r][3];

        ImagePane ROOK = (ImagePane) rookPane.getChildren().get(0);
        TARGET_OF_ROOK.getChildren().add(ROOK);
        ImagePane KING = (ImagePane) kingPane.getChildren().get(0);
        TARGET_OF_KING.getChildren().add(KING);

        
        ROOK.setCol(2);
        KING.setCol(3);

    }





    public static void shortCastle(Piece piece, Square targetSquare, Square[][] board){
        Square shortRookSquare = board[piece.getRow()][7];
        Rook shortRook = (Rook) shortRookSquare.getPiece();
        Square kingSquare = board[piece.getRow()][piece.getCol()];
        Square TARGET_OF_KING = board[piece.getRow()][6];
        Square TARGET_OF_ROOK = board[piece.getRow()][5];
        shortRookSquare.removePiece();
        TARGET_OF_KING.addPiece(piece);
        kingSquare.removePiece();
        TARGET_OF_ROOK.addPiece(shortRook);
        shortRook.updateLocation(piece.getRow(), 5);
        piece.updateLocation(targetSquare.getRow(), 6);
    }

    public static void GUIShortCastle(Piece piece, Square targetSquare, SquarePane[][] panes, Square[][] board){
        int r = piece.getRow();
        SquarePane rookPane = panes[r][7];
        SquarePane kingPane = panes[r][4];
        SquarePane TARGET_OF_KING = panes[r][6];
        SquarePane TARGET_OF_ROOK = panes[r][5];

        ImagePane ROOK = (ImagePane) rookPane.getChildren().get(0);
        TARGET_OF_ROOK.getChildren().add(ROOK);
        ImagePane KING = (ImagePane) kingPane.getChildren().get(0);
        TARGET_OF_KING.getChildren().add(KING);

        
        ROOK.setCol(5);
        KING.setCol(6);

    }
    

    public static void GUIPromote(Piece piece, Square targetSquare, SquarePane[][] panes, Square[][] board){
        SquarePane targetPane = panes[targetSquare.getRow()][targetSquare.getCol()];
        SquarePane originalPane = panes[piece.getRow()][piece.getCol()];
        if (targetSquare.getPiece() != null){
            targetPane.getChildren().remove(0);
       }
       originalPane.getChildren().remove(0);
       if (piece.getColor()){
        targetPane.getChildren().add(new ImagePane(new Image("Pieces/whiteQueen.png"), targetPane.getRow(),targetPane.getCol()));
       }else {
        targetPane.getChildren().add(new ImagePane(new Image("Pieces/blackQueen.png"), targetPane.getRow(), targetPane.getCol()));
       }
        

    }


    public static void promotePiece(Piece piece, Square targetSquare, Square[][] board){
        Square originalSquare = board[piece.getRow()][piece.getCol()];
        originalSquare.removePiece();
        targetSquare.addPiece(new Queen(targetSquare.getRow(), targetSquare.getCol(), piece.getColor()));
    }
    }


