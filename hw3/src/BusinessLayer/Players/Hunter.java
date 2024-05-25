package src.BusinessLayer.Players;

import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Unit;

import java.util.List;

public class Hunter extends Player {
    private int range;
    private int arrowsCount;
    private int ticksCount;
    public Hunter( String name, int healthCapacity, int attack, int defense,int range) {
        super(name,healthCapacity,attack,defense);
        this.range = range;
        this.arrowsCount = 10*level;
        this.ticksCount = 0;
        this.abilityName = "Shoot";
    }

    public int getRange() {
        return range;
    }

    public int getArrowsCount() {
        return arrowsCount;
    }

    public int getTicksCount() {
        return ticksCount;
    }

    @Override
    public void levelUp(){
        super.levelUp();
        arrowsCount += 10*level;
        attackPoints += 2*level;
        defensePoints += level;
    }

    @Override
    public void onGameTick() {
        if(ticksCount == 10){
            arrowsCount += level;
            ticksCount = 0;
        }
        else{
            ticksCount++;
        }
    }

    @Override
    public String description() {
        String ans = super.description();
        ans += String.format("\t Range: %d\t Arrows: %d\t",range, arrowsCount);
        return ans;
    }

    @Override
    public void doSpecialAbility(List<Enemy> enemies) {
        if(arrowsCount <= 0){
            msg.send("You don't have enough arrows to use " + abilityName + "!");
            return;
        }
        List<Enemy> enemiesInRange = getEnemiesInRange(enemies,range);
        if(!enemiesInRange.isEmpty()){
            Enemy enemy = closestEnemy(enemiesInRange);
            msg.send(String.format("%s shot %s!",getName(),enemy.getName()));
            attackWithDefense(attackPoints, enemy);
            arrowsCount--;
        }
    }

    private Enemy closestEnemy(List<Enemy> enemies){
        double minDist = enemies.get(0).getPosition().range(position);
        Enemy minEnemy = enemies.get(0);
        for(Enemy enemy : enemies){
            if(enemy.getPosition().range(position) < minDist){
                minDist = enemy.getPosition().range(position);
                minEnemy = enemy;
            }
        }
        return minEnemy;
    }
}
