package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MyChessGame extends javax.swing.JFrame {
    
    private JFrame f;
    //public HomeSc  
    public MyChessGame(ChessBoard chessBoard)
    {
        f= new JFrame();
        JPanel gui = new JPanel(new BorderLayout());//OUTER PANEL
        JButton b=new JButton("");
        gui.setPreferredSize(new Dimension(1920,1080));
       
        b.setIcon(new javax.swing.ImageIcon(getClass().getResource("welcome.jpg")));
         
        b.addActionListener(new ActionListener() {
        @Override 
        public void actionPerformed(ActionEvent e)
        {
            f.dispose();
            f.setVisible(false);

            Runnable r = new Runnable() {

            @Override
            public void run() {

                Action cb =
                        new Action(chessBoard);

                JFrame f = new JFrame("ChessGame");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setSize(1920,1080);
                f.setVisible(true);
                  String Player1_name=JOptionPane.showInputDialog("White Player Name:");
                  cb.Name1.setText(Player1_name);
                  cb.Name1.setFont(new Font("Courier New",Font.BOLD, 18));
                  String Player2_name=JOptionPane.showInputDialog("Black Player Name:");
                  cb.Name2.setText(Player2_name);
                  cb.Name2.setFont(new Font("Courier New",Font.BOLD, 18));
            }
        };
        SwingUtilities.invokeLater(r);
          
        }});
      
        gui.add(b);

        f.add(gui);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);


        f.setSize(1920,1080);
        f.setVisible(true);
    }

    
    public static void main(String args[])
    {
        // Initialize Chess Board
        ChessBoard chessBoard = new ChessBoard();
        
        MyChessGame cover=new MyChessGame(chessBoard);
        
         

    }
}

