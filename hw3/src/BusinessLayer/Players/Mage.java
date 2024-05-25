package src.BusinessLayer.Players;

import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Unit;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;
    public Mage( String name, int healthCapacity, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name,healthCapacity,attack,defense);
        this.manaPool = manaPool;
        this.currentMana = this.manaPool/4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
        this.abilityName = "Blizzard";
    }

    public int getManaPool() {
        return manaPool;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public int getHitsCount() {
        return hitsCount;
    }

    public int getAbilityRange() {
        return abilityRange;
    }

    @Override
    public void levelUp(){
        super.levelUp();
        manaPool += level*25;
        currentMana = Math.min(currentMana + manaPool/4, manaPool);
        spellPower += 10*level;
    }

    @Override
    public String description() {
        String ans = super.description();
        ans += String.format("\t Mana: %d/%d\t Mana Cost: %d\t  Spell Power: %d\t Hits Amount: %d\t Ability Range: %d\t",
                currentMana,manaPool,manaCost,spellPower,hitsCount,abilityRange);
        return ans;
    }

    @Override
    public void onGameTick() {
        currentMana = Math.min(currentMana+level, manaPool);
    }

    @Override
    public void doSpecialAbility(List<Enemy> enemies) {
        if(currentMana < manaCost){
            msg.send("You don't have enough Mana to use " + abilityName + "!");
            return;
        }
        List<Enemy> enemiesInRange = getEnemiesInRange(enemies,abilityRange);
        currentMana -= manaCost;
        int hits = 0;
        Random random = new Random();
        while (hits < hitsCount && !enemiesInRange.isEmpty()){
            Enemy enemy = enemiesInRange.get(random.nextInt(enemiesInRange.size()));
            msg.send(String.format("%s attacked %s with %d damage!", name, enemy.getName(), spellPower));
            attackWithDefense(spellPower, enemy);
            if(!enemy.isAlive()){
                enemiesInRange.remove(enemy);
            }
            hits++;
        }
    }

}
