package src.Tests;

import org.junit.Before;
import org.junit.Test;
import src.BusinessLayer.Board;
import src.BusinessLayer.Position;
import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Tile;
import src.BusinessLayer.Tiles.TileFactory;
import src.CLI.CLI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UnitTest {

    private Player player;
    private Board board;
    private CLI cli;
    private List<Enemy> enemies;
    private TileFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new TileFactory();
        board = new Board();
        cli = new CLI(board);
        player = factory.producePlayer(0,null,cli,board);
        initBoard();
    }

    private void initBoard(){
        List<String> boardRows = new ArrayList<String>();
        boardRows.add("#####################");
        boardRows.add("#........M..........#");//boss
        boardRows.add("#.........#.........#");//monstar
        boardRows.add("#@.s......#.........#");
        boardRows.add("#....Q....#.........#");//boss
        boardRows.add("#...................#");
        boardRows.add("#####################");
        List<Tile> tiles = new ArrayList<>();
        enemies = new ArrayList<>();
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

    @Test
    public void processStep() {
        Position p = Position.at(player.getPosition());
        player.processStep(board.getTile(player.getPosition().getX(),player.getPosition().getY()+1));
        assertEquals(player.getPosition(),new Position(p.getX(),p.getY()+1));
        p = Position.at(enemies.get(1).getPosition());
        enemies.get(1).processStep(board.getTile(enemies.get(1).getPosition().getX(), enemies.get(1).getPosition().getY()-1));
        assertEquals(enemies.get(1).getPosition(),new Position(p.getX(),p.getY()));
    }



    @Test
    public void attackWithDefense() {
        int healthBefore = enemies.get(1).getHealth().getAmount();
        player.attackWithDefense(0,enemies.get(1));
        assertEquals(healthBefore,enemies.get(1).getHealth().getAmount());

    }


    @Test
    public void combatAfterRolling() {
        //player attacks enemy
        int healthBefore = enemies.get(1).getHealth().getAmount();
        player.combatAfterRolling(4,3,enemies.get(1));
        assertEquals(healthBefore-1,enemies.get(1).getHealth().getAmount());

        //enemy attacks player
        healthBefore = player.getHealth().getAmount();
        enemies.get(0).combatAfterRolling(4,5,enemies.get(1));
        assertEquals(healthBefore, player.getHealth().getAmount());

    }

}