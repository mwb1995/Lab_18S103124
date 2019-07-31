package src;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.TOP;
import static javax.swing.SwingConstants.TRAILING;
import javax.swing.border.*;

class Action {

    private final JPanel gui ;//Tthe BASE Panel
    public JButton[][] boardSquares ;//8*8 matrix of CHESS_BOARD CELLS buttons
    private JPanel board;//Panel for BOARD
    private JPanel players_container;//Panel for Player and Game Details
    private JLabel white;
    private JLabel black;
    private JLabel player1;
    private JLabel player2; 

    private JLabel Dead;
    public JLabel []Dead_pieces_white;
    public JLabel []Dead_Pawns_white;
    public JLabel []Dead_pieces_black;
    public JLabel []Dead_Pawns_black;
    public static JLabel Game_Status;
    public TextField Name1;
    public TextField Name2;

    private final JLabel message ;
    private static final String COLS = "abcdefgh";
    private FlowLayout layout;
    private Image img;
    final ChessBoard chessBoard;
    public Thread thread;
 
    public Action() {
        this.gui = null;
        this.message = null;
        this.chessBoard = null;
    }
    
    
    Action(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        gui = new JPanel(new BorderLayout());//OUTER PANEL
        gui.setPreferredSize(new Dimension(800,600));
        board = new JPanel(new GridLayout(0, 9));//LEFT SIDE PANEL
        layout=new FlowLayout();
        players_container=new JPanel();//RIGHT SIDE PANEL
        players_container.setLayout(layout);

        Dead_Pawns_white=new JLabel[8];
        Dead_pieces_white=new JLabel[7];
        Dead_Pawns_black=new JLabel[8];
        Dead_pieces_black=new JLabel[7];

        boardSquares = new JButton[8][8];
        message = new JLabel("Welcome !");
        thread = new Thread();
        initializeGui();
    }
  
    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new LineBorder(Color.WHITE));
        board.setBorder(new LineBorder(Color.BLACK));
        board.setPreferredSize(new Dimension(800,600));
        players_container.setBorder(new LineBorder(Color.BLACK));
        players_container.setPreferredSize(new Dimension(450,1000));
        layout.setAlignment(TRAILING);
        layout.setVgap(25);
        layout.setHgap(10);
        
        white=new JLabel("              WHITE          ");
        white.setFont(new Font("Courier New",Font.BOLD, 24));
        white.setForeground(Color.BLACK);
        player1=new JLabel("Player1:");
        player1.setFont(new Font(COLS, TOP, 18));
        Name1=new TextField();
        Name1.setColumns(15);


        Dead=new JLabel("DEAD:"); 
        Dead.setFont(new Font("Courier New",Font.BOLD, 18));

        
        players_container.add(white);
        players_container.add(player1);
        players_container.add(Name1);

        players_container.add(Dead);
        
        for(int i=0;i<8;i++)
        {
            Dead_Pawns_white[i]=new JLabel();
            Dead_Pawns_white[i].setPreferredSize(new Dimension(40,40));

            players_container.add(Dead_Pawns_white[i]);
        }
        
        for(int i=0;i<7;i++)
        {
            Dead_pieces_white[i]=new JLabel();
            Dead_pieces_white[i].setPreferredSize(new Dimension(40,40));

            players_container.add(Dead_pieces_white[i]);
        }
        JLabel Line=new JLabel();
        Line.setText("_______________________________________________________________________________");
        players_container.add(Line);
        
        Game_Status=new JLabel();
        Game_Status.setPreferredSize(new Dimension(450,40));
        Game_Status.setText("Chess GAME");
        Game_Status.setHorizontalAlignment(CENTER);
        Game_Status.setHorizontalTextPosition(CENTER);
        //Game_Status.setBorder(new LineBorder(Color.MAGENTA));
        players_container.add(Game_Status);
        JLabel Line1=new JLabel();
        Line1.setText("_______________________________________________________________________________");
        players_container.add(Line1);
    
        JLabel j = new JLabel("                                                         ");

        players_container.add(j);

        
        black=new JLabel("              BLACK          ");
        black.setFont(new Font("Courier New",Font.BOLD, 24));
        black.setForeground(Color.BLACK);
        player2=new JLabel("Player2:");
        player2.setFont(new Font(COLS, TOP, 18));
        Name2=new TextField();
        Name2.setColumns(15);

        Dead=new JLabel("DEAD:"); 
        Dead.setFont(new Font("Courier New",Font.BOLD, 18));

     
        players_container.add(black);
        players_container.add(player2);
        players_container.add(Name2);

        players_container.add(Dead);
        
        for(int i=0;i<8;i++)
        {
            Dead_Pawns_black[i]=new JLabel();
            Dead_Pawns_black[i].setPreferredSize(new Dimension(40,40));

            players_container.add(Dead_Pawns_black[i]);
        }
        
        for(int i=0;i<7;i++)
        {
            Dead_pieces_black[i]=new JLabel();
            Dead_pieces_black[i].setPreferredSize(new Dimension(40,40));

            players_container.add(Dead_pieces_black[i]);
        }
        
        players_container.setBackground(Color.white);
        board.setBackground(Color.BLACK);
        gui.setBackground(Color.BLACK);
        gui.add(board,BorderLayout.WEST);
        gui.add(players_container,BorderLayout.EAST);

        Handler[][] handler = new Handler[8][8];
        int ii,jj;
        Insets buttonMargin = new Insets(0,0,0,0);
        for (jj = 0; jj < boardSquares.length; jj++) {
            for (ii = 0; ii < boardSquares.length; ii++) {
                
                handler[jj][ii] = new Handler(jj,ii,chessBoard);
                
                handler[jj][ii].button.setMargin(buttonMargin);

                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                   handler[jj][ii].button.setBackground(Color.WHITE);
                } else {
                    handler[jj][ii].button.setBackground(Color.yellow);
                }
               // handler.button.setIcon(icon);
             try {

                 if(jj==1)
                   {
                     img = ImageIO.read(getClass().getResource("ChessImages/1.png"));
                     handler[jj][ii].button.setIcon(new ImageIcon(img));
                   }
                   else if(jj == 0)
                    {   
                        String str= "ChessImages/0".concat(String.valueOf(ii)).concat(".png");
                        img = ImageIO.read(getClass().getResource(str));

                        //img = ImageIO.read(getClass().getResource(str));
                        handler[jj][ii].button.setIcon(new ImageIcon(img));
                    }
                   else if(jj==6)
                   {
                     img = ImageIO.read(getClass().getResource("ChessImages/2.png"));
                     handler[jj][ii].button.setIcon(new ImageIcon(img));
                   }
                   else if(jj == 7)
                    {   
                        String str= "ChessImages/7".concat(String.valueOf(ii)).concat(".png");
                        img = ImageIO.read(getClass().getResource(str)); handler[jj][ii].button.setIcon(new ImageIcon(img));
                    }
                   else
                       handler[jj][ii].button.setIcon(null);
                   
                  } catch (IOException ex) {
                  } 

                boardSquares[jj][ii] = handler[jj][ii].button;
            }
        }

        //fill the chess board
        board.add(new JLabel(""));
        // fill the top row
        for ( ii = 0; ii < 8; ii++) {
            JLabel jb = new JLabel(COLS.substring(ii, ii + 1),SwingConstants.CENTER);
            jb.setForeground(Color.WHITE);
            jb.setFont((new Font("Courier New",Font.CENTER_BASELINE,18)));
            board.add(jb);
        }
        // fill the player2 non-pawn piece row
        
        for ( jj = 0; jj < 8; jj++) {
            for ( ii = 0; ii < 8; ii++) {
                switch (ii) {
                    case 0:
                        JLabel jb = new JLabel("" + (jj + 1),SwingConstants.CENTER);
                        jb.setForeground(Color.WHITE);
                        jb.setFont((new Font("Courier New",Font.CENTER_BASELINE,18)));
                        board.add(jb);
                        
                    default:
                       // boardSquares[jj][ii].setBorderPainted(true);
                        board.add(boardSquares[jj][ii]);
                        handler[jj][ii].setListener(jj, ii, this,handler);
                }
            }
        }
    }

    public void countDown()
    {
        
    }
    
    public final JComponent getChessBoard() {
        return board;
    }

    public final JComponent getGui() {
        return gui;
    }
   
}
