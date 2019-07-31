package src;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

//GoBoard  add tips,pos_text,refresh,exit button
class GoBoard extends Panel implements MouseListener,ActionListener
{
    int x=-1,y=-1,isBlack=1;
    List<String> black_pos= new ArrayList();
    List<String> white_pos= new ArrayList();
    Button Refresh = new Button("Refresh");
    TextField tips = new TextField("Black chess first");
    Button ExitGame = new Button("Exit");
    TextField black_history = new TextField(" ");
    TextField white_history = new TextField(" ");
    GoBoard()
    {
        setSize(500,1000);
        setLayout(null);
        setBackground(Color.blue);
        addMouseListener(this);
        add(Refresh);
        Refresh.setBounds(10,5,60,26);
        Refresh.addActionListener(this);

        add(ExitGame);
        ExitGame.setBounds(200,5,60,26);

        ExitGame.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );
        add(tips);
        tips.setBounds(90,5,100,24);
        tips.setEditable(false);
        add(black_history);

        black_history.setBounds(50,500,300,100);
        black_history.setEditable(false);
        black_history.setText("black position:");

        add(white_history);
        white_history.setBounds(50,700,300,100);
        white_history.setEditable(false);
        white_history.setText("white position:");


    }

    //paint the goboard
    public void paint(Graphics g)
    {
        for(int i=40;i<=400;i+=20)
        {
            g.drawLine(40,i,400,i);
        }
        for(int j=40;j<=400;j+=20)
        {
            g.drawLine(j,40,j,400);
        }
//five black points
        g.fillOval(97,97,6,6);
        g.fillOval(97,337,6,6);
        g.fillOval(337,97,6,6);
        g.fillOval(337,337,6,6);
        g.fillOval(217,217,6,6);
    }

    //play chess
    public void mousePressed(MouseEvent e)
    {
        if(e.getModifiers() == InputEvent.BUTTON1_MASK)
        {
//get the position
            x=(int)e.getX();
            y=(int)e.getY();
            //paint black or white piece
            Piece_black piece_black = new Piece_black(this);
            Piece_white piece_white = new Piece_white(this);

            int a=(x+10)/20,b=(y+10)/20;
            //out of goboard
            if(x/20<2 || y/20<2 || x/20>19 || y/20>19)
            {}
            else
            {
                String t_pos="("+String.valueOf(a-2)+","+String.valueOf(18-b+2)+")";
                if(isBlack==1)
                {
                    this.add(piece_black);
                    black_pos.add(t_pos);
                    String t_history = String.join(",", black_pos);
                    black_history.setText(t_history);

                    piece_black.setBounds(a*20-10,b*20-10,20,20);
                    isBlack = isBlack*(-1);
                    tips.setText("White chess please");
                }
                else if(isBlack==-1)
                {
                    this.add(piece_white);

                    white_pos.add(t_pos);
                    String t_history = String.join(",", white_pos);
                    white_history.setText(t_history);

                    piece_white.setBounds(a*20-10,b*20-10,20,20);
                    isBlack = isBlack*(-1);
                    tips.setText("Black chess please");
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    // refresh button
    public void actionPerformed(ActionEvent e)
    {
        this.removeAll();
        isBlack=1;
        add(Refresh);
        Refresh.setBounds(10,5,60,26);
        add(tips);
        tips.setBounds(90,5,100,24);
        tips.setText("Black chess first");

        add(ExitGame);
        ExitGame.setBounds(200,5,60,26);
        //for exit button
        ExitGame.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);

                    }


                }
        );

        black_pos.clear();
        white_pos.clear();
        add(black_history);

        black_history.setBounds(50,500,300,100);
        black_history.setEditable(false);
        black_history.setText("black position:");

        add(white_history);
        white_history.setBounds(50,700,300,100);
        white_history.setEditable(false);
        white_history.setText("white position:");

    }
}




//black piece class
class Piece_black extends Canvas implements MouseListener
{
    GoBoard goboard=null;
    Piece_black(GoBoard p)
    {
        setSize(20,20);
        goboard=p;
        addMouseListener(this);
    }
    //piece size
    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillOval(0,0,20,20);
    }
    public void mousePressed(MouseEvent e)
    {
        if(e.getModifiers()==InputEvent.BUTTON3_MASK)
        {
            goboard.remove(this);
            goboard.isBlack=1;
            goboard.tips.setText("Black chess please");
        }
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e)
    {
        if(e.getClickCount()>=2)
            goboard.remove(this);
    }
}
//white piece class
class Piece_white extends Canvas implements MouseListener
{
    GoBoard goboard=null;
    Piece_white(GoBoard p)
    {
        setSize(20,20);
        goboard=p;
        addMouseListener(this);
    }
    //piece size
    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(0,0,20,20);
    }
    public void mousePressed(MouseEvent e)
    {
        if(e.getModifiers()==InputEvent.BUTTON3_MASK)
        {
            goboard.remove(this);
            goboard.isBlack=-1;
            goboard.tips.setText("White chess please");
        }
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e)
    {
        if(e.getClickCount()>=2)
            goboard.remove(this);
    }
}


//create the windows
public class MyGoGame extends Frame
{
    GoBoard goboard = new GoBoard();
    MyGoGame()
    {
        setVisible(true);
        setLayout(null);
        Label label = new Label("\n" +"Click the left button to play the chess piece, double click to eat the chess piece, right click on the chess piece to repent",Label.CENTER);
        add(label);
        label.setBounds(70,55,700,26);
        label.setBackground(Color.blue);
        add(goboard);
        goboard.setBounds(70,90,700,1000);
        addWindowListener(new WindowAdapter()
                          {
                              public void windowClosing(WindowEvent e)
                              {
                                  System.exit(0);
                              }
                          }
        );
        pack();
        setSize(800,1200);
    }
    public static void main(String args[])
    {
        MyGoGame go = new MyGoGame();
    }
}
