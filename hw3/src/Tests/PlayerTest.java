package src.Tests;

import org.junit.Before;
import org.junit.Test;
import src.BusinessLayer.Board;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Players.Hunter;
import src.BusinessLayer.Players.Mage;
import src.BusinessLayer.Players.Rogue;
import src.BusinessLayer.Players.Warrior;
import src.BusinessLayer.Position;
import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Tile;
import src.BusinessLayer.Tiles.TileFactory;
import src.CLI.CLI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Enemy enemy1;
    private Enemy enemy2;
    private Warrior warrior;
    private Mage mage;
    private Rogue rogue;
    private Hunter hunter;
    private List<Enemy> enemies;

    @Before
    public void setUp() throws Exception {
        player = new Player("Johnathan", 100, 100, 100) {
            @Override
            public void onGameTick() {
                return;
            }

            @Override
            public void doSpecialAbility(List<Enemy> enemies) {
                return;
            }
        };
        player.setMsg((s)->{});
        player.setMsgDeath(()->{});
        enemy1 = new Enemy('$', "Jacob", 100, 100, 100, 10) {
            @Override
            public void processStep(Player p) {
                return;
            }

            @Override
            public void accept(Visitor v) {
                return;
            }
        };
        enemy2 = new Enemy('g', "Nathan", 100, 100, 100, 10) {
            @Override
            public void processStep(Player p) {
                return;
            }

            @Override
            public void accept(Visitor v) {
                return;
            }
        };
        warrior = new Warrior("Jon Snow", 300, 30, 4, 3);
        mage = new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
        rogue = new Rogue("Arya Stark", 150, 40, 2, 20);
        hunter = new Hunter("Ygritte", 220, 30, 2, 6);
        enemies = new LinkedList<>();
        enemies.add(enemy1);
        enemies.add(enemy2);
        warrior.setMsg((s)->{});
        warrior.setMsgDeath(()->{});
        mage.setMsg((s)->{});
        mage.setMsgDeath(()->{});
        rogue.setMsg((s)->{});
        rogue.setMsgDeath(()->{});
        hunter.setMsg((s)->{});
        hunter.setMsgDeath(()->{});
        enemy1.setMsg((s)->{});
        enemy1.setMsgDeath(()->{});
        enemy2.setMsg((s)->{});
        enemy2.setMsgDeath(()->{});
    }

    @Test
    public void levelUp() {
        player.levelUp();//Warrior
        assertEquals(2,player.getLevel());
        assertEquals(120, player.getHealth().getPool());
        assertEquals(120, player.getHealth().getAmount());
        assertEquals(108, player.getAttackPoints());
        assertEquals(102, player.getDefensePoints());
        assertEquals(-50, player.getExperience());

        warrior.levelUp();
        assertEquals(0,warrior.getRemainingCooldown());
        assertEquals(330, warrior.getHealth().getPool());
        assertEquals(42, warrior.getAttackPoints());
        assertEquals(8, warrior.getDefensePoints());

        mage.levelUp();
        assertEquals(350,mage.getManaPool());
        assertEquals(162, mage.getCurrentMana());
        assertEquals(35, mage.getSpellPower());

        rogue.levelUp();
        assertEquals(100,rogue.getCurrentEnergy());
        assertEquals(54, rogue.getAttackPoints());

        hunter.levelUp();
        assertEquals(30,hunter.getArrowsCount());
        assertEquals(42, hunter.getAttackPoints());
        assertEquals(6, hunter.getDefensePoints());
    }

    @Test
    public void getEnemiesInRange() {
        player.initialize(new Position(0,0));
        enemy1.initialize(new Position(0,3));
        enemy2.initialize(new Position(5,5));
        List<Enemy> range = player.getEnemiesInRange(enemies,5);
        assertEquals(1,range.size());
    }

    @Test
    public void onGameTickWarrior() {
        int remaining = warrior.getRemainingCooldown();
        warrior.onGameTick();
        assertEquals(remaining-1,warrior.getRemainingCooldown());
    }

    @Test
    public void onGameTickMage() {
        int currentMana = mage.getCurrentMana();
        mage.onGameTick();
        assertEquals(currentMana+1,mage.getCurrentMana());
        int manaPool = mage.getManaPool();
        for(int i = 0; i < manaPool; i++){
            mage.onGameTick();
        }
        assertEquals(manaPool, mage.getCurrentMana());
    }

    @Test
    public void onGameTickRogue() {
        rogue.initialize(new Position(4,1));
        enemy1.initialize(new Position(0,3));
        enemy2.initialize(new Position(5,5));
        rogue.setMsg((s) -> {});
        rogue.doSpecialAbility(enemies);
        int currentEnergy = rogue.getCurrentEnergy();
        rogue.onGameTick();
        assertEquals(currentEnergy+10,rogue.getCurrentEnergy());
        rogue.doSpecialAbility(enemies);
        for(int i = 0; i < 15; i++){
            rogue.onGameTick();
        }
        assertEquals(100, rogue.getCurrentEnergy());
    }

    @Test
    public void onGameTickHunter() {
        int arrowsCount = hunter.getArrowsCount();
        for(int i = 0; i < 10; i++){
            hunter.onGameTick();
            assertEquals(arrowsCount,hunter.getArrowsCount());
        }
        hunter.onGameTick();
        assertEquals(arrowsCount+ hunter.getLevel(), hunter.getArrowsCount());
    }
    @Test
    public void doSpecialAbilityWarrior() {
        warrior.initialize(new Position(0,0));
        enemy1.initialize(new Position(0,3));
        enemy2.initialize(new Position(5,5));
        int formerHealth = enemy1.getHealth().getAmount();
        warrior.doSpecialAbility(enemies);
        assertEquals(warrior.getRemainingCooldown(),warrior.getAbilityCooldown());
        assertEquals(formerHealth-warrior.getHealth().getPool()/10,enemy1.getHealth().getAmount());
    }

    @Test
    public void doSpecialAbilityMage() {
        mage.initialize(new Position(0,0));
        enemy1.initialize(new Position(0,3));
        enemy1.setDefensePoints(0);
        enemy2.initialize(new Position(5,mage.getAbilityRange()));
        enemy2.setDefensePoints(0);
        int formerHealth1 = enemy1.getHealth().getAmount();
        int formerHealth2 = enemy2.getHealth().getAmount();
        int currentMana = mage.getCurrentMana();
        mage.doSpecialAbility(enemies);
        assertEquals(currentMana-mage.getManaCost(),mage.getCurrentMana());
        assertNotEquals(formerHealth1,enemy1.getHealth().getAmount());
        assertEquals(formerHealth2,enemy2.getHealth().getAmount());
    }

    @Test
    public void doSpecialAbilityRogue() {
        rogue.initialize(new Position(0,0));
        enemy1.initialize(new Position(0,2));
        enemy1.setDefensePoints(0);
        enemy2.initialize(new Position(0,5));
        enemy2.setDefensePoints(0);
        int formerHealth1 = enemy1.getHealth().getAmount();
        int formerHealth2 = enemy2.getHealth().getAmount();
        int currentEnergy = rogue.getCurrentEnergy();
        rogue.doSpecialAbility(enemies);
        assertEquals(currentEnergy-rogue.getCost(),rogue.getCurrentEnergy());
        assertEquals(formerHealth1-rogue.getAttackPoints(),enemy1.getHealth().getAmount());
        assertEquals(formerHealth2,enemy2.getHealth().getAmount());
    }

    @Test
    public void doSpecialAbilityHunter() {
        hunter.initialize(new Position(0,0));
        enemy1.initialize(new Position(0,hunter.getRange()));
        enemy1.setDefensePoints(0);
        enemy2.initialize(new Position(hunter.getRange(),hunter.getRange()));
        enemy2.setDefensePoints(0);
        int formerHealth1 = enemy1.getHealth().getAmount();
        int formerHealth2 = enemy2.getHealth().getAmount();
        int arrowsCount = hunter.getArrowsCount();
        hunter.doSpecialAbility(enemies);
        assertEquals(arrowsCount-1,hunter.getArrowsCount());
        assertEquals(formerHealth1-hunter.getAttackPoints(),enemy1.getHealth().getAmount());
        assertEquals(formerHealth2,enemy2.getHealth().getAmount());
    }

    @Test
    public void onKilledUnit() {
        player.onKilledUnit(enemies.get(0));
        assertEquals(10, player.getExperience());
    }

    @Test
    public void onDeathTest(){
        player.onDeath();
        assertEquals(player.getTile(), 'X');
    }
}