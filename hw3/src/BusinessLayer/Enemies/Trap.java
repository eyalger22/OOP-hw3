package src.BusinessLayer.Enemies;

import src.BusinessLayer.Board;
import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Tile;
import src.BusinessLayer.Tiles.Unit;

import java.util.List;
import java.util.Random;

public class Trap extends Enemy {
    private int visibilityTime;
    private int inVisibilityTime;
    private int ticksCount;
    private boolean visible;
    public Trap(char s, String name, int healthCapacity, int attack, int defense, int experience, int visibilityTime, int inVisibilityTime) {
        super(s,name,healthCapacity,attack,defense, experience);
        this.inVisibilityTime = inVisibilityTime;
        this.visibilityTime = visibilityTime;
        ticksCount = 0;
        visible = true;
    }



    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void processStep(Player p) {
        visible = ticksCount < visibilityTime;
        if (ticksCount == visibilityTime+inVisibilityTime)
            ticksCount =0;
        else
            ticksCount++;
        if (p.getPosition().range(position) < 2){
            msg.send(String.format("Trap %s attack %s",getName(), p.getName()));
            Random rand = new Random();
            int attackRoll = rand.nextInt(attackPoints + 1);
            attackWithDefense(attackRoll, p);
        }
    }

    @Override
    public String toString(){
        if (visible)
            return super.toString();
        else
            return ".";
    }
}
