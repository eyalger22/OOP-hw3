package src.BusinessLayer;

import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.TileFactory;
import src.CLI.CLI;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private List<String> levels;
    private int currectLevel;
    private Player player;
    private CLI cli;
    private Board board;
    private LevelManager levelManager;

    public static void main(String args[])  //static method
    {
        GameManager gm = new GameManager();
        if (args.length != 1){
            System.out.println("You must get exactly 1 args parameter");
        }
        String path = args[0];
        gm.startGame(path);
    }

    public void startGame(String path){
        try {
            readLevels(path);
        }
        catch (Exception e){
            System.out.println("you enter wrong path to levels");
        }
        TileFactory factory = new TileFactory();
        board = new Board();
        cli = new CLI(board);
        int typePlayer = cli.choosePlayer(factory.listPlayers());
        player = factory.producePlayer(typePlayer,null,cli,board);
        //start levels
        currectLevel = 1;
        for(String level: levels){
            cli.printMessage("You start level " + currectLevel);
            List<String> boardRows = readLevelBoard((new File(new File(path), level)).getPath());
            levelManager = new LevelManager(boardRows, player, currectLevel,cli, board);
            levelManager.startLevel();
//            if (!){
//                cli.printMessage(player.getName() + " has died! GAME OVER");
//                cli.printGameState();
//                return;
//            }
            currectLevel++;
        }
        cli.printMessage("Game Over!");
        cli.printMessage(player.getName() + " has won the game!");
    }
    public void readLevels(String path){
        try {
            levels = new LinkedList<>();
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for (File f : listOfFiles) {
                levels.add(f.getName());
            }
            levels.stream().sorted().toList();
        } catch (Exception e) {
            cli.printMessage(e.getMessage());
        }
    }

    public List<String> readLevelBoard(String path) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(path));
            String next;
            while ((next = reader.readLine()) != null) {
                lines.add(next);
            }
        } catch (FileNotFoundException e) {
            System.out.println ("File not found " + path);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" +
                    e.getStackTrace());
        }
        return lines;
    }
}
