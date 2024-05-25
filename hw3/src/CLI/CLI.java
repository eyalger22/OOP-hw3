package src.CLI;

import src.BusinessLayer.Board;
import src.BusinessLayer.Tiles.Player;

import java.util.List;
import java.util.Scanner;

public class CLI {
    private Board board;
    private Scanner s = new Scanner(System.in);

    public CLI(Board b){
        board = b;
    }
    private void printBoard(){
        System.out.println(board.toString());
    }

    public void printGameState(){
        printBoard();
        System.out.println(board.getPlayer().description());

    }

    public int choosePlayer(List<Player> listPlayers){
        int i = 1;
        System.out.println("Select player: ");
        for (Player p :listPlayers){
            System.out.println(i+ ". " + p.description());
            i++;
        }
        String choose = s.next();
        try {
            int idx = Integer.parseInt(choose)-1;
            System.out.println("you have selected:");
            System.out.println(listPlayers.get(idx).getName());
            return idx;
        }
        catch (Exception e){
            System.out.println("You must enter only numbers");
            return choosePlayer(listPlayers);
        }

    }

    public void printMessage(String message) {
        System.out.println(message);
    }


    public char getPlayerMove() {
        System.out.println("Choose your next move:");
        String m = s.next();
        return m.charAt(0);
    }

    public void playerDeath(Player p) {
        System.out.println(p.getName() + " has died! GAME OVER");
        System.exit(0);
    }
}
