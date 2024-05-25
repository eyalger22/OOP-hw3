package src.BusinessLayer.Enemies;

import src.BusinessLayer.Board;
import src.BusinessLayer.Interfaces.HeroicUnit;
import src.BusinessLayer.Position;
import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Tile;
import src.BusinessLayer.Tiles.Unit;

import java.util.List;
import java.util.Random;

public class Boss extends Enemy implements HeroicUnit {
    private int visionRange;
    private int abilityFrequency;
    private int combatTicks;
    public Boss(char s, String name, int healthCapacity, int attack, int defense, int experience, int visionRange, int abilityFrequency) {
        super(s,name,healthCapacity,attack,defense, experience);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        combatTicks = 0;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


    @Override
    public void processStep(Player p) {

        Position newPosition = null;
        int dX = position.getY() - p.getPosition().getY();
        int dY = position.getX() - p.getPosition().getX();
        if (p.getPosition().range(position)< visionRange) {

            //if combat ticks == ability frequency
            if (combatTicks == abilityFrequency){
                combatTicks = 0;
                castAbility(null,p);
                return;
            }
            combatTicks++;
            if (Math.abs(dX) > Math.abs(dY)) {
                if (dX > 0)
                    newPosition = Position.at(getPosition().getX(), getPosition().getY() - 1);//left
                else
                    newPosition = Position.at(getPosition().getX(), getPosition().getY() + 1);//right
            } else {
                if (dY < 0)
                    newPosition = Position.at(getPosition().getX() + 1, getPosition().getY());//down
                else
                    newPosition = Position.at(getPosition().getX() - 1, getPosition().getY());//up
            }
            if (newPosition != null) {
                processStep(getTile.getTileAt(newPosition));
                return;
            }
        }
        combatTicks = 0;
        //Perform a random movement action
        Random rand = new Random();
        int rnd = rand.nextInt(4) + 1;
        switch (rnd){//random move
            case 1:
                newPosition = Position.at(getPosition().getX() - 1 , getPosition().getY());
                break;
            case 2:
                newPosition = Position.at(getPosition().getX(), getPosition().getY()-1);
                break;
            case 3:
                newPosition = Position.at(getPosition().getX() + 1, getPosition().getY());
                break;
            case 4:
                newPosition = Position.at(getPosition().getX(), getPosition().getY()+1);
                break;
        }
        if (newPosition != null)
            processStep(getTile.getTileAt(newPosition));
    }

    @Override
    public void castAbility(List<Enemy> enemies, Player defender) {
        Random rand = new Random();
        int attackRoll = attackPoints;
        msg.send(String.format("%s attack with %d points.",getName(),attackRoll));
        int defenseRoll = rand.nextInt(defender.getDefensePoints() + 1);
        msg.send(String.format("%s rolled %d defense points.",defender.getName(),defenseRoll));
        combatAfterRolling(attackRoll,defenseRoll,defender);
    }
}

