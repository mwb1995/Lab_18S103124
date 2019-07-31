package src;
import java.util.Scanner;
public class MyChessAndGoGame {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        System.out.println("input the game type (chess or go)：");
        if (scan.hasNextLine()) {
            String str2 = scan.nextLine();
            System.out.println("Game start!");
            if(str2.equals("chess")||str2.equals("go")){
                if(str2.equals("chess")){
                    ChessBoard chessBoard = new ChessBoard();
                    MyChessGame cover=new MyChessGame(chessBoard);
                }
                if(str2.equals("go")){
                    MyGoGame go = new MyGoGame();
                }

            }


            else{
                System.out.println("Error");
            }



        }
        scan.close();



    }
}
