package src.BusinessLayer.Players;

import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Tiles.Player;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private int abilityCooldown;
    private int remainingCooldown;
    public Warrior( String name, int healthCapacity, int attack, int defense, int cooldown) {
        super(name,healthCapacity,attack,defense);
        this.abilityCooldown = cooldown;
        this.remainingCooldown = 0;
        this.abilityName = "Avenger’s Shield";
    }

    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    public int getRemainingCooldown() {
        return remainingCooldown;
    }

    @Override
    public void onGameTick() {
        remainingCooldown--;
    }

    @Override
    public void doSpecialAbility(List<Enemy> enemies) {
        if(remainingCooldown > 0){
            msg.send("Wait for the cooldown to end before using " + abilityName + "!");
            return;
        }
        List<Enemy> enemiesInRange = getEnemiesInRange(enemies,3);
        if(enemiesInRange.size() > 0) {
            Random random = new Random();
            Enemy enemy = enemiesInRange.get(random.nextInt(enemiesInRange.size()));
            remainingCooldown = abilityCooldown;
            health.setAmount(Math.min(health.getAmount() + 10 * defensePoints, health.getPool()));
            int damage = health.getPool() / 10;
            enemy.getHealth().reduceAmount(damage);
            msg.send(String.format("%s hit %s with %d damage!", name, enemy.getName(), damage));
            if (!enemy.isAlive()) {
                onKilledUnit(enemy);
            }
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        remainingCooldown = 0;
        health.setPool(health.getPool() + 5*level);
        attackPoints += 2*level;
        defensePoints += level;
    }

    @Override
    public String description() {
        String ans = super.description();
        ans += String.format("\t Ability Cooldown: %d/%d\t",remainingCooldown, abilityCooldown);
        return ans;
    }


    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
