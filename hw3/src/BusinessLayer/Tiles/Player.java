package src.BusinessLayer.Tiles;

import src.BusinessLayer.Interfaces.HeroicUnit;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Position;
import java.util.LinkedList;
import java.util.List;

public abstract class Player extends Unit implements HeroicUnit {
    protected int level;
    protected String abilityName;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    protected Player(String name, int healthCapacity, int attack, int defense) {
        super('@',name,healthCapacity,attack,defense,0);
        level = 1;
    }

    public abstract void onGameTick();

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void visit(Player p) {
        return;
    }

    @Override
    public void visit(Enemy e) {
        combat(e);
    }

    public void onKilledUnit(Unit enemy) {
        this.experience += enemy.experience;
        if(this.experience >= this.level*50) {
            levelUp();
        }
        msg.send(enemy.name + " has been killed by " + name + "!");
        enemy.onDeath();
    }

    @Override
    public String description() {
        String ans = super.description();
        ans += String.format("\t Level: %d\t Experience: %d\t ",level, experience);
        return ans;
    }

    @Override
    public void onDeath() {
        this.tile = 'X';
        msgDeath.call();
    }

    public void levelUp(){
        experience = experience - (50*level);
        level++;
        health.setHealthOnLevelUp(level);
        this.attackPoints += level*4;
        this.defensePoints += level;
        msg.send("Player: " + name + " leveled up! Now in level " + level);
    }

    public List<Enemy> getEnemiesInRange(List<Enemy> enemies, int range){
        List<Enemy> enemiesInRange = new LinkedList<>();
        for(Enemy enemy : enemies){
            if(enemy.getPosition().range(position) <= range){
                enemiesInRange.add(enemy);
            }
        }
        return enemiesInRange;
    }

    public abstract void doSpecialAbility(List<Enemy> enemies);


    @Override
    public void castAbility(List<Enemy> enemies, Player player) {
        msg.send(name + " used " + abilityName + "!");
        doSpecialAbility(enemies);
    }

    public void processStep(char m, List<Enemy> enemies){
        Tile newTile = null;
        switch (m){
            case 'w':
                newTile = getTile.getTileAt(Position.at(getPosition().getX() - 1 , getPosition().getY()));
                break;
            case 'a':
                newTile = getTile.getTileAt(Position.at(getPosition().getX(), getPosition().getY()-1));
                break;
            case 's':
                newTile = getTile.getTileAt(Position.at(getPosition().getX() + 1, getPosition().getY()));
                break;
            case 'd':
                newTile = getTile.getTileAt(Position.at(getPosition().getX(), getPosition().getY()+1));
                break;
            case 'e':
                castAbility(enemies, this);
        }
        if (newTile != null)
            processStep(newTile);
    }

}

