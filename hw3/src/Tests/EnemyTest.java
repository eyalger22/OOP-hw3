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

public class EnemyTest {

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
        player = factory.producePlayer(0, null, cli, board);
        initBoard();
    }

    private void initBoard() {
        List<String> boardRows = new ArrayList<String>();
        boardRows.add("#####################");
        boardRows.add("#........M..........#");//boss
        boardRows.add("#..s......#.........#");//monstar
        boardRows.add("#@........#.........#");//boss
        boardRows.add("#....M....#.........#");//Trap
        boardRows.add("#.......Q...........#");
        boardRows.add("#####################");
        List<Tile> tiles = new ArrayList<>();
        enemies = new ArrayList<>();
        int x = 0;
        int y = 0;
        for (String row : boardRows) {
            y = 0;
            for (char c : row.toCharArray()) {
                Position p = new Position(x, y);
                switch (c) {
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
                        Enemy e = factory.produceEnemy(c, p, cli, board);
                        tiles.add(e);
                        enemies.add(e);
                        break;
                }
                y++;
            }
            x++;
        }
        board.initBoard(tiles, player, enemies);
    }

    @Test
    public void processStepCheckMonstar() {
        Position p;
        Enemy enemy;
        enemy = enemies.get(1);//monstar
        p = Position.at(enemy.getPosition());
        enemy.processStep(player);
        assertEquals(Position.at(p.getX(), p.getY() - 1), enemy.getPosition());
        enemy.processStep(player);
        assertEquals(Position.at(p.getX() + 1, p.getY() - 1), enemy.getPosition());


    }

    @Test
    public void processStepCheckBoss1() {
        Position p = new Position(0, 0);
        Enemy enemy = enemies.get(2);//boss
        p = Position.at(enemy.getPosition());
        enemy.processStep(player);
        assertEquals(Position.at(p.getX(), p.getY() - 1), enemy.getPosition());
        enemy.processStep(player);
        assertEquals(Position.at(p.getX(), p.getY() - 2), enemy.getPosition());
    }
    @Test
    public void processStepCheckBoss2() {
        Position p = new Position(0, 0);
        Enemy enemy = enemies.get(2);//boss
        player.processStep('d', enemies);
        player.processStep('d', enemies);
        player.processStep('d', enemies);
        player.processStep('d', enemies);
        player.processStep('d', enemies);
        player.processStep('d', enemies);

        p = Position.at(enemy.getPosition());
        enemy.processStep(player);
        assertEquals(Position.at(p.getX(), p.getY()+1), enemy.getPosition());
        enemy.processStep(player);
        assertEquals(Position.at(p.getX()-1, p.getY() + 1), enemy.getPosition());
    }

    @Test
    public void processStepCheckTrap() {
        Position p;
        Enemy enemy;
        enemy = enemies.get(3);//Trap:3/10
        p = Position.at(enemy.getPosition());
        enemy.processStep(player);
        assertEquals("Q", enemy.toString());
        enemy.processStep(player);
        assertEquals("Q", enemy.toString());
        enemy.processStep(player);
        assertEquals("Q", enemy.toString());
        enemy.processStep(player);
        assertEquals(".", enemy.toString());
        enemy.processStep(player);
        assertEquals(".", enemy.toString());
        assertEquals(p, enemy.getPosition());
        enemy.processStep(player);
        assertEquals(".", enemy.toString());
        enemy.processStep(player);
        assertEquals(".", enemy.toString());
        assertEquals(p, enemy.getPosition());



    }
}