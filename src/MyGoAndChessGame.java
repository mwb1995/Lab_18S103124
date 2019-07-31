package src;
import java.util.Scanner;
public class MyGoAndChessGame {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        // 从键盘接收数据

        // nextLine方式接收字符串
        System.out.println("input the game type (chess or go)：");
        // 判断是否还有输入
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
