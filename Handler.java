package src;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

class Handler {
    public final String WHITE = "white";
    public final String BLACK = "black";
    JButton button;
    int X;
    int Y;
    Action cbcr;
    final ChessBoard chessBoard;
    Handler handler;
    static String currentImage;
    Image img;
    static int previousX;
    static int previousY;
    
    static Piece previousPiece;
    static ArrayList<Square> previousPossibleMoves;
    ArrayList<Square> possibleMoves;
    static boolean turn = true;
    static int whitePawn = 0;
    static int whitePiece =  0;
    static int blackPawn = 0;
    static int blackPiece =  0;
    static boolean timerturn = true;
    
    //CONSTRUCTOR
    public Handler(){
        chessBoard =null;
        currentImage = null;
    }
    public Handler(int x, int y, ChessBoard chessBoard) {
        this.button = new JButton();
        this.X = x;
        this.Y = y;
        this.chessBoard = chessBoard;
        currentImage = null;

        //cbcr = new ChessBoardWithColumnsAndRow();
    }
    
    public void setListener(int jj, int ii, Action cbcr, Handler[][] handler)
    {
        cbcr.boardSquares[jj][ii].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int x = handler[jj][ii].getX();
            int y = handler[jj][ii].getY();
            
            Piece p = chessBoard.squares[x][y].getPiece();

            
            if(sourceSelected(chessBoard.squares))
            {

                previousX = x;
                previousY = y;
                previousPiece = p;
                
                if(p == null){
                    DialogMessage("NO PIECE ON THE SELECTED SQUARE");
                    return;              
                }   
            
                else
                {
                    if(turn == true && p.getColour().equals(BLACK))
                    {
                        DialogMessage("WHITE's  TURN");
                        return;
                    }
                    
                    else if(turn == false && p.getColour().equals(WHITE))
                    {
                        DialogMessage("BLACKS's  TURN");
                        return;
                    }
                    
                    if(kingInDanger(cbcr)) 
                    { 
                        if(!(p instanceof King))
                        {
                            DialogMessage("King under Check!");
                            clearAllHighlight(chessBoard.squares);
                            return;
                        }
                        else
                        {
                            possibleMoves = p.move(chessBoard.squares);
                            
                            if(possibleMoves.isEmpty())
                            {
                                DialogMessage("game overs");
                                return;
                            }
                            else
                            {
                                Border emptyBorder = BorderFactory.createEmptyBorder();
                                cbcr.boardSquares[x][y].setBorder(emptyBorder);
                                clearAllHighlight(chessBoard.squares);
                            }
                        }
                    }
                
                    
                    currentImage = p.getIdToPath(p.id);


                    possibleMoves = p.move(chessBoard.squares);
                    addHighLight(cbcr,possibleMoves);
                    previousPossibleMoves = possibleMoves;
                }
            }
            
            else
            {
                //imageName = p.getIdToPath(p.id);
                if(p == null){
                    // set and remove image from buttons
                    if(previousPossibleMoves.contains(chessBoard.squares[x][y]))
                    {
                        //set and remove Piece Image from squares
                        SetandRemoveImage(handler,x,y);

                        //set and remove Piece object from squares
                        SetandRemovePiece(x,y);

                        //remove highlight of buttons
                        removeHighlight(cbcr,previousPossibleMoves,chessBoard.squares);

                    }

                    else{
                        // ILLEGAL MOVE DIALOG BOX
                        DialogMessage(" ILLEGAL MOVE ");
                        return;
                    }
                }   
            
                else//Attacking opponent piece
                {
                    if(x == previousX && y == previousY){
                        removeHighlight(cbcr,previousPossibleMoves,chessBoard.squares);
                        return;
                    }
                    
                    if(previousPossibleMoves.contains(chessBoard.squares[x][y])){
                        //currentImage = previousPiece.getIdToPath(previousPiece.getId());
                        String attackedPieceImage = p.getIdToPath(p.getId());

                        //set the dead piece image
                        setDeadPiece(cbcr,p,attackedPieceImage);

                        //set and remove Piece Image from squares
                        SetandRemoveImage(handler,x,y);

                        //set and remove Piece object from squares
                        SetandRemovePiece(x,y);

                        removeHighlight(cbcr,previousPossibleMoves,chessBoard.squares);

                        previousX = x;
                        previousY = y;
                    }
                    else{
                        // ILLEGAL MOVE DIALOG BOX
                        DialogMessage(" ILLEGAL MOVE ");
                        return;
                    }
                   
                }
                //timer.start(turn);
                
                changeTurn();
                
                //timer.stop(turn);
                
                if(turn == true)
                    cbcr.Game_Status.setText("WHITE TO PLAY");
                
                else
                    cbcr.Game_Status.setText("BLACK TO PLAY");
                
            }
        }
    });
    }
    
    public boolean sourceSelected(Square[][] squares)
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(squares[i][j].highlight)
                    return false;
            }
        }
        
        return true;
    }
    
    public void addHighLight(Action cbcr, ArrayList<Square> possibleMoves){
       
        for(Square sq : possibleMoves)
        {
            int x = sq.getX();
            int y = sq.getY();
            
            Border greenBorder = new LineBorder(Color.GREEN, 8);
            Border redBorder = new LineBorder(Color.RED, 8);
            
            if(sq.getPiece() != null)
                cbcr.boardSquares[x][y].setBorder(redBorder);
            
            else
                cbcr.boardSquares[x][y].setBorder(greenBorder);
        }
    }
    
    public void removeHighlight(Action cbcr, ArrayList<Square> possibleMoves, Square[][] squares){
       
        for(Square sq : possibleMoves)
        {
            int x = sq.getX();
            int y = sq.getY();
            
            Border emptyBorder = BorderFactory.createEmptyBorder();
            cbcr.boardSquares[x][y].setBorder(emptyBorder);
            //squares[x][y].highlight = false;
        }
        
        clearAllHighlight(squares);
        
    }
       
    public void SetandRemoveImage(Handler[][] handler, int x, int y){
        
        try {
            img = ImageIO.read(getClass().getResource("ChessImages/" +currentImage));
        }catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }

        handler[x][y].button.setIcon(new ImageIcon(img));

        //remove previous image
        handler[previousX][previousY].button.setIcon(new ImageIcon());
       
    }

    public void SetandRemovePiece(int x,int y){
        
        previousPiece.setX(x);
        previousPiece.setY(y);
        chessBoard.squares[x][y].setPiece(previousPiece);
        
        chessBoard.squares[previousX][previousY].setPiece(null);
    }
    
    public void setDeadPiece(Action cbcr, Piece p, String s)
    {
        if(p.getColour().equals(WHITE))
        {
            if(p instanceof Pawn)
            {
                try {
                    Image img = ImageIO.read(getClass().getResource("DeadImages/2.png"));
                    cbcr.Dead_Pawns_white[whitePawn].setIcon(new ImageIcon(img));
                    whitePawn++;
                } catch (IOException ex) {
                    Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    Image img = ImageIO.read(getClass().getResource("DeadImages/" +p.getIdToPath(p.getId())));
                    cbcr.Dead_pieces_white[whitePiece].setIcon(new ImageIcon(img));
                    whitePiece++;
                } catch (IOException ex) {
                    Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        else
        {
            if(p instanceof Pawn)
            {
                try {
                    Image img = ImageIO.read(getClass().getResource("DeadImages/1.png"));
                    cbcr.Dead_Pawns_black[blackPawn].setIcon(new ImageIcon(img));
                    blackPawn++;
                } catch (IOException ex) {
                    Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    Image img = ImageIO.read(getClass().getResource("DeadImages/" +p.getIdToPath(p.getId())));
                    cbcr.Dead_pieces_black[blackPiece].setIcon(new ImageIcon(img));
                    blackPiece++;
                } catch (IOException ex) {
                    Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }
    
    public void changeTurn(){
        turn = !turn;
    }
    
    public boolean kingInDanger(Action cbcr){
        King k;
        if(turn == true)
            k = getWhiteKing();
        
        else
            k = getBlackKing();
        

        if(k.isCheckMate(chessBoard.squares,k.getX(),k.getY()))
        {
            if(k.colour.equals(WHITE))
            {
                cbcr.Game_Status.setText("GAME OVER! BLACK WINS!!!");
                DialogMessage("CHECKMATE! BLACK WINS!!!\r\n");
                return false;
            }
            else
            {
                cbcr.Game_Status.setText("GAME OVER! WHITE WINS!!!");
                DialogMessage("CHECKMATE! WHITE WINS!!!");
                return false;
            }
        }
        
        if(k.isInDanger(chessBoard.squares,k.getX(),k.getY()))
        {
            Border redBorder = new LineBorder(Color.RED, 8);
            cbcr.boardSquares[k.getX()][k.getY()].setBorder(redBorder);
            //chessBoard.squares[k.getX()][k.getY()].highlight = true;
            return true;
        }
        
        return false;
    }
    
    public void clearAllHighlight(Square[][] squares){
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                squares[i][j].highlight = false;
            }
        }
    }
    
    public King getWhiteKing(){
        return (King)chessBoard.wk;
    }
    
    public King getBlackKing(){
        return (King)chessBoard.bk;
    }
    
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
    
    public void DialogMessage(String s){
        JOptionPane.showMessageDialog(null, s);
    }
}
