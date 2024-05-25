package src.BusinessLayer;

import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Tile;
import src.BusinessLayer.Tiles.TileFactory;
import src.CLI.CLI;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private Board board;
    private CLI cli;
    private Player player;
    private List<Enemy> enemies;
    private int levelNum;
    public LevelManager(List<String> boardRows, Player player, int levelNum, CLI cli, Board board) {
        this.board = board;
        this.player = player;
        this.levelNum = levelNum;
        this.cli = cli;
        enemies = new ArrayList<>();
        createBoard(boardRows);
    }

    private void createBoard(List<String> boardRows){
        TileFactory factory = new TileFactory();
        List<Tile> tiles = new ArrayList<>();
        int x = 0;
        int y = 0;
        for (String row: boardRows){
            y = 0;
            for(char c: row.toCharArray()){
                Position p = new Position(x,y);
                switch (c){
                    case '#':
                        tiles.add(factory.produceWall(p));
                        break;
                    case '.':
                        tiles.add(factory.produceEmpty(p));
                        break;
                    case '@':
                        tiles.add(player);
                        player.initialize(p);
                        break;
                    default:
                        Enemy e = factory.produceEnemy(c,p,cli,board);
                        tiles.add(e);
                        enemies.add(e);
                        break;
                }
                y++;
            }
            x++;
        }
        board.initBoard(tiles,player,enemies);
    }

    public void startLevel(){
        while (!isLevelEnd()){
            runRound();
        }
    }

    private void runRound() {
        cli.printGameState();
        char move = cli.getPlayerMove();
        player.processStep(move,enemies);
        for (Enemy e: enemies){
            e.processStep(player);
        }
        player.onGameTick();
    }

    private boolean isLevelEnd() {
        return (!player.isAlive() || enemies.size() == 0);
    }
}
