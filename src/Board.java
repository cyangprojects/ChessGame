
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane; 
import javafx.scene.image.*;
import javafx.event.EventTarget;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.layout.Border;

public class Board {
    private Square[][] board = new Square[8][8];
    private SquarePane[][] panes = new SquarePane[8][8];
    private GridPane GUIboard = new GridPane();
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;
    
    //public static final String defaultFEN = "rnbqkbnr/pppppppp/8/8/6r1/8/PPPPP2P/RNBQK2R";
    //public static final String defaultFEN = "rnbq1bnr/pppppppp/8/8/1R3p1k/8/PPPPPPPP/RNBQKBNR";
    public static final String defaultFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    public static final int PANE_SIZE = 100;
    private Piece currentPiece;
    private boolean currentColor = true;
    private Piece EnPassantPawn;
    private Square lastMoveOriginalSquare;
    private Square lastMoveTargetSquare;
    HashMap<Square, String> selectedLegalMoves = new HashMap<Square,String>();
    HashMap<Piece, HashMap<Square, String>> totalLegalMoves = new HashMap<Piece, HashMap<Square, String>>();


    

    public Board(){
        initEmptyBoard();
        drawBoard();
        FENToBoard(defaultFEN);
        drawPieces();
        addEventHandlers();

    }

    private void initEmptyBoard(){
        boolean color = true;
        for (int row = 0; row < BOARD_HEIGHT; row++){
            color = !color;
            for (int col = 0; col < BOARD_WIDTH; col++){
                color = !color;
                setSquare(row, col, color, null);
            }
        }
    }
    private void deselect(Piece piece){
        if (piece != null){
            removeCurrentHighlights(selectedLegalMoves.keySet());
        }
    }


    private void rotateBoard(){
        GUIboard.setRotate(GUIboard.getRotate() + 180);
        for (SquarePane[] paneList : panes){
            for (SquarePane squarePane : paneList){
                squarePane.setRotate(squarePane.getRotate() + 180);
            }
        }
    }

    private boolean checkForMate(){
        return (totalLegalMoves.size() == 0);
    }

    private void updateTurn(){
        currentColor = !currentColor;
        updateTotalLegalMoves();
        rotateBoard();
        highlightLastMove();
        if (checkForMate()){
            System.out.print("Checkmate!");
        }
    }

    private void select(Piece piece){
        currentPiece = piece;
        selectedLegalMoves.clear();
        if (totalLegalMoves.containsKey(piece)){
            HashMap<Square, String> moves = totalLegalMoves.get(piece);
            for (Square square : moves.keySet()){
                selectedLegalMoves.put(square, moves.get(square));
            }
            highlightPossibleMoves(selectedLegalMoves.keySet());
        }
    }



    /*Move and checkmate logic
    NEW TURN
    1.) Update Total Legal Moves (CurrentColor --> Starts as white)
        1a.) For each piece on the side of currentColor, call getLegal* moves on the piece
        1b.) Add the piece and its legal moves (includes square and type of move) into the HashMap totalLegalMoves
    * Get Legal Moves (intakes a piece and a board state)
        a.) Call getPossible Moves for the piece and board state (Each piece has a unique algorithm to determine possible moves based on board state)
        b.) For each square and move returned by getPossibleMoves
            i.) Create an identical replicated board, make the move ising the Move Ches, and see if the king is in check by calling evaluateCheck** in the CheckDetector Class
        c.) If the king is not in check, add square and move to totalLegalMoves
    ** Evaluate check (intakes replica board and currentColor)
        a.) On the board state, call createAttackMap which returns all squares under attack by the enemy player
        b.) If the king's square is in the attacked square, return true as it is in check
    4.) Select piece (currentPiece-->  piece that was selected)
        4a.) If the piece contains moves in totalLegalMoves, highlight the piece's legal squares to move
        4b.) If one of the legal squares on the board is clicked, make move and GUI move using Move Class
    5.) Update turn
        5a.) Change currentColor
        5b.) Call updateTotalLegalMove
        5c.) Check for checkmate (TotalLegalMoves is size 0)
        5d.) Rotate board
    
    */ 

    private void addEventHandlers(){
        updateTotalLegalMoves();
        GUIboard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                EventTarget target = event.getTarget();
                if(currentPiece != null){
                    if (target.toString().equals("Square")){
                        SquarePane squarePane = (SquarePane) target;
                        Square square = board[squarePane.getRow()][squarePane.getCol()];
                        if (square.getPiece() != null){
                            Piece piece = square.getPiece();
                            if (piece == currentPiece){
                                deselect(currentPiece);
                                currentPiece = null;
                            } else if (piece.getColor() == currentColor){
                                deselect(currentPiece);
                                select(piece);
                            }
                        }

                        if (selectedLegalMoves.keySet().contains(square)){
                            removeHighlightedLastMove();
                            lastMoveOriginalSquare = board[currentPiece.getRow()][currentPiece.getCol()];
                            lastMoveTargetSquare = board[square.getRow()][square.getCol()];
                            deselect(currentPiece);
                            Move.movePiece(currentPiece, square, selectedLegalMoves.get(square), panes, board);
                            
                            
                            if (selectedLegalMoves.get(square) == "Pawn Jump"){
                                EnPassantPawn = currentPiece;
                            } else {
                                EnPassantPawn = null;
                            }
                            
                            currentPiece = null;
                            updateTurn();
                        }
                    
                    } else if (target.toString().equals("Piece")){
                        ImagePane piecePane = (ImagePane) target;
                        Piece piece = board[piecePane.getRow()][piecePane.getCol()].getPiece();
                        if (piece == currentPiece){
                            deselect(currentPiece);
                            currentPiece = null;
                        } else if (piece.getColor() == currentColor){
                            deselect(currentPiece);
                            select(piece);  
                        }
                        Square square = board[piece.getRow()][piece.getCol()];
                        if (selectedLegalMoves.keySet().contains(square)){
                            deselect(currentPiece);
                            removeHighlightedLastMove();
                            lastMoveOriginalSquare = board[currentPiece.getRow()][currentPiece.getCol()];
                            lastMoveTargetSquare = board[square.getRow()][square.getCol()];
                            Move.movePiece(currentPiece, square, selectedLegalMoves.get(square), panes, board);
                            if (selectedLegalMoves.get(square) == "Pawn Jump"){
                                EnPassantPawn = currentPiece;
                            } else {
                                EnPassantPawn = null;
                            }
                            currentPiece =null;
                            updateTurn();
                        }
                    }
                } else {
                    if (target.toString().equals("Square")){
                        SquarePane squarePane = (SquarePane) target;
                        Square square = board[squarePane.getRow()][squarePane.getCol()];
                        if (square.getPiece() != null && square.getPiece().getColor() == currentColor ){
                            select(square.getPiece());
                        }
                    } else if (target.toString().equals("Piece")){
                        ImagePane piecePane = (ImagePane) target;
                        Piece piece = board[piecePane.getRow()][piecePane.getCol()].getPiece();
                        if (piece.getColor() == currentColor){
                            select(piece);
                        }
                    }
                }


            }
        });
    }


    private void highlightPossibleMoves(Set<Square> possibleMoves){
        Glow glow = new Glow();
        glow.setLevel(0.7);
        for (Square square : possibleMoves){
            SquarePane squareBoard = panes[square.getRow()][square.getCol()];
            squareBoard.setBackground(new Background(new BackgroundFill(Color.rgb(98, 203, 255), CornerRadii.EMPTY, Insets.EMPTY)));
            squareBoard.setEffect(glow);
            squareBoard.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.2))));  
        }

    }

    private void highlightLastMove(){
        if (lastMoveOriginalSquare != null && lastMoveTargetSquare != null){
            Glow glow = new Glow();
            glow.setLevel(0.7);
            SquarePane targetSquarePane = panes[lastMoveTargetSquare.getRow()][lastMoveTargetSquare.getCol()];
            SquarePane originalSquarePane = panes[lastMoveOriginalSquare.getRow()][lastMoveOriginalSquare.getCol()];

            targetSquarePane.setBackground(new Background(new BackgroundFill(Color.rgb(216, 124, 122), CornerRadii.EMPTY, Insets.EMPTY)));
            originalSquarePane.setBackground(new Background(new BackgroundFill(Color.rgb(213, 124, 122), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        
    }

    private void removeHighlightedLastMove(){
        if (lastMoveOriginalSquare != null && lastMoveTargetSquare != null){
            Set<Square> squares = new HashSet<>();
            squares.add(lastMoveOriginalSquare);
            squares.add(lastMoveTargetSquare);
            removeCurrentHighlights(squares);

        }
    }

    private void removeCurrentHighlights(Set<Square> highlightedMoves) {
        for (Square square : highlightedMoves){
            SquarePane squareBoard = panes[square.getRow()][square.getCol()];
            if (square.getColor()){
                squareBoard.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                squareBoard.setBackground(new Background(new BackgroundFill(Color.rgb(118, 150, 86), CornerRadii.EMPTY, Insets.EMPTY)));
            }

            squareBoard.setEffect(null);
            squareBoard.setBorder(null);
        }
    }
    private void drawBoard(){
        Background whiteBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        Background blackBackground = new Background(new BackgroundFill(Color.rgb(118, 150, 86), CornerRadii.EMPTY, Insets.EMPTY));
        for(int row = 0;  row < BOARD_HEIGHT; row++){
            for (int col = 0; col < BOARD_WIDTH; col++){
                Square currentSquare = board[row][col];
                SquarePane squarePane = new SquarePane(row, col);
                squarePane.setMinWidth(PANE_SIZE);
                squarePane.setMinHeight(PANE_SIZE);
                if (currentSquare.getColor()){
                    squarePane.setBackground(whiteBackground);
                } else {
                    squarePane.setBackground(blackBackground);
                }
                panes[row][col] = squarePane;
                GUIboard.add(squarePane, col, row);

            }
        }

    }


    private void updateTotalLegalMoves(){
        totalLegalMoves.clear();
        for (Square[] squareList : board){
            for (Square square : squareList){
                if (square.getPiece() == null){
                    continue;
                } else if (square.getPiece().getColor() == currentColor){
                    if (getLegalMoves(square.getPiece()).size() != 0){
                        totalLegalMoves.put(square.getPiece(), getLegalMoves(square.getPiece()));
                    }
                }

            }
        }
    }

    private HashMap<Square, String> getLegalMoves(Piece piece){
        HashMap<Square, String>  moves = new HashMap<Square, String>();
        HashMap<Square, String> possibleMoves;
        if (piece instanceof Pawn && EnPassantPawn != null){
            Pawn pawn = (Pawn) piece;
            possibleMoves = pawn.getPossibleMovesEnPassant(board, EnPassantPawn); //Add en pessant pawn
        } else {
            possibleMoves = piece.getPossibleMoves(board);
        }
        for (Square targetSquare : possibleMoves.keySet()){ 
            Square[][] newBoard = cloneBoard();
            Square newSquare = newBoard[targetSquare.getRow()][targetSquare.getCol()];
            Piece newPiece = newBoard[piece.getRow()][piece.getCol()].getPiece();
            if (possibleMoves.get(targetSquare) == "En Passant"){
                Move.EnPassant(newPiece, newSquare, newBoard);
            } else {
                Move.moveOrAttack(newPiece, newSquare, newBoard);
            }
            if (!(CheckDetector.evaluateCheck(currentColor, newBoard))){
                moves.put(targetSquare, possibleMoves.get(targetSquare));
            }
        }
        return moves;
    }


    private Square[][] cloneBoard(){
        Square[][] newBoard = new Square[8][8];
        for (Square[] squareList : board ){
            for (Square square : squareList){
                if (square.getPiece() != null){
                    Square newSquare = new Square(square.getRow(), square.getCol(), square.getColor(), square.getPiece().returnClone());
                    newBoard[square.getRow()][square.getCol()] = newSquare;
                } else {
                    Square newSquare = new Square(square.getRow(), square.getCol(), square.getColor(), null);
                    newBoard[square.getRow()][square.getCol()] = newSquare;
                }
                
            }
        }
        return newBoard;




    }

    private void drawPieces(){
        for(int row = 0;  row < BOARD_HEIGHT; row++){
            for (int col = 0; col < BOARD_WIDTH; col++){
                if (board[row][col].getPiece() == null){
                    continue;
                }
                Piece piece = board[row][col].getPiece();
                switch (piece.getEnum()){
                    case wPawn:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/whitePawn.png"), row, col));
                        break;
                     case bPawn:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/blackPawn.png"), row, col));
                        break;
                    case wKnight:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/whiteKnight.png"), row, col));
                        break;
                    case bKnight:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/blackKnight.png"), row, col));
                        break;
                    case wBishop:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/whiteBishop.png"), row, col));
                        break;
                    case wRook:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/whiteRook.png"), row, col));
                        break;
                    case bRook:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/blackRook.png"), row, col));
                        break;
                    case bBishop:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/blackBishop.png"), row, col));
                        break;
                    case wQueen:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/whiteQueen.png"), row, col));
                        break;
                    case bQueen:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/blackQueen.png"), row, col));
                        break;
                    case wKing:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/whiteKing.png"), row, col));
                        break;
                    case bKing:
                        panes[row][col].getChildren().add(new ImagePane(new Image("Pieces/blackKing.png"), row, col));   
                        break;  

                }
                
                

            }
        }
    }


    private void FENToBoard(String FEN){
        int row = 0;
        int col = 0;
        char[] charArray = FEN.toCharArray();
        
        for (char c : charArray){
            boolean color;
            if (c == '/'){
                row++;
                col = 0;
                continue;
            }
            if (Character.isDigit(c)){
                for (int i = 0; i < Character.getNumericValue(c); i++){
                    board[row][col].addPiece(null);
                    col++;
                }
                continue;
            }
            if(Character.isUpperCase(c)){
                color = true;
            } else{
                color = false;
            }
            c = Character.toLowerCase(c);
            if (c == 'p'){
                board[row][col].addPiece(new Pawn(row, col, color));
                col++;
                continue;
            } 
            if (c == 'r'){
                board[row][col].addPiece(new Rook(row, col, color));
                col++;
                continue;
            }
            if (c == 'n'){
                board[row][col].addPiece(new Knight(row, col, color));
                col++;
                continue;
            }
            if (c == 'b'){
                board[row][col].addPiece(new Bishop(row, col, color));
                col++;
                continue;
            }
            if (c == 'q'){
                board[row][col].addPiece(new Queen(row, col, color));
                col++;
                continue;
            }
            if (c == 'k'){
                board[row][col].addPiece(new King(row, col, color));
                col++;
                continue;
            }

        }

    }

    private void setSquare(int row, int col, boolean color, Piece piece){
        board[row][col] = new Square(row, col, color, null);
    }

    public GridPane getGridPane(){
        return GUIboard;
    }





}
