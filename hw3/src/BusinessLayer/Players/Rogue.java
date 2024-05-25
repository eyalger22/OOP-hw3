package src.BusinessLayer.Players;

import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Unit;

import java.util.List;

public class Rogue extends Player {
    private int cost;
    private int currentEnergy;

    public Rogue( String name, int healthCapacity, int attack, int defense, int cost) {
        super(name,healthCapacity,attack,defense);
        this.cost = cost;
        this.currentEnergy = 100;
        this.abilityName = "Fan of Knives";
    }

    public int getCost() {
        return cost;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    @Override
    public void levelUp(){
        super.levelUp();
        currentEnergy = 100;
        attackPoints += 3*level;
    }

    @Override
    public void onGameTick() {
        currentEnergy = Math.min(100, currentEnergy+10);
    }

    @Override
    public String description() {
        String ans = super.description();
        ans += String.format("\t Energy: %d\t Ability Cost: %d\t",currentEnergy, cost);
        return ans;
    }

    @Override
    public void doSpecialAbility(List<Enemy> enemies) {
        if(currentEnergy < cost){
            msg.send("You don't have enough energy to use " + abilityName + "!");
            return;
        }
        List<Enemy> enemiesInRange = getEnemiesInRange(enemies,2);
        currentEnergy -= cost;
        for(Enemy enemy : enemiesInRange){
            msg.send(String.format("%s attacked %s",getName(),enemy.getName()));
            attackWithDefense(attackPoints, enemy);
        }
    }
}
