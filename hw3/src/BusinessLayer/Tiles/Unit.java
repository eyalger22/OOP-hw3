package src.BusinessLayer.Tiles;

import src.BusinessLayer.Health;
import src.BusinessLayer.Interfaces.GetTileCallback;
import src.BusinessLayer.Interfaces.MessageCallback;
import src.BusinessLayer.Interfaces.DeathCallback;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Position;

import java.util.Random;

public abstract class Unit extends Tile implements Visitor {
    protected String name;
    protected int attackPoints;
    protected int defensePoints;
    protected Health health;
    protected int experience;
    protected MessageCallback msg;
    protected DeathCallback msgDeath; //don't have DeathMessageCallback and won't add it due to fear of conflict, will change after merge
    protected GetTileCallback getTile;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    protected void initCallbacks(MessageCallback messageCallback, DeathCallback deathCallback, GetTileCallback getTileCallback){
        msg = messageCallback;
        msgDeath = deathCallback;
        getTile = getTileCallback;
    }


    public int getAttackPoints() {
        return attackPoints;

    }


    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public MessageCallback getMsg() {
        return msg;
    }

    public void setMsg(MessageCallback msg) {
        this.msg = msg;
    }

    public DeathCallback getMsgDeath() {
        return msgDeath;
    }

    public void setMsgDeath(DeathCallback msgDeath) {
        this.msgDeath = msgDeath;
    }

    protected Unit(char tile, String name, int healthCapacity, int attack, int defense, int experience) {
        super(tile);
        this.name = name;
        this.attackPoints = attack;
        this.defensePoints = defense;
        this.health = new Health(healthCapacity, healthCapacity);
        this.experience = experience;
    }

    public void processStep(Tile t){
        t.accept(this);
    }
    // What happens when the unit dies
    public abstract void onDeath();


    public void visit(Empty e){
        Position p = position;
        this.setPosition(e.position);
        e.setPosition(p);
    }
    public void visit(Wall w){
        return;
    }

    // Combat against another unit.
    //protected abstract void combat(Unit defender);

    protected void combat(Unit defender) {
        //Melisandre engaged in combat with Lannister Solider.
        msg.send(String.format("%s engaged in combat with %s",getName(),defender.getName()));
        msg.send(description());
        msg.send(defender.description());
        Random rand = new Random();
        int attackRoll = rand.nextInt(attackPoints + 1);
        msg.send(String.format("%s rolled %d attack points.",getName(),attackRoll));
        attackWithDefense(attackRoll, defender);
        if (!defender.health.isAlive()){
            processStep(getTile.getTileAt(defender.position));
        }
        //String combatDetails = String.format("combat: %s attacks %s ",getName(),defender.getName());
        //msg.send(combatDetails);
    }

    public void attackWithDefense(int attackRoll, Unit defender){
        Random rand = new Random();
        //Melisandre rolled 2 attack points.
        int defenseRoll = rand.nextInt(defender.getDefensePoints() + 1);
        msg.send(String.format("%s rolled %d defense points.",defender.getName(),defenseRoll));
        combatAfterRolling(attackRoll,defenseRoll,defender);
    }

    public void combatAfterRolling(int attackRoll, int defenseRoll, Unit defender){
        String combatResults;
        int diffRoll = attackRoll - defenseRoll;
        if (diffRoll > 0) {
            defender.health.reduceAmount(diffRoll);
            //Melisandre dealt 0 damage to Lannister Solider.
            combatResults = String.format("%s dealt %d damage to %s", getName(), diffRoll, defender.getName());
        }else{
            combatResults = String.format("%s has defended itself from %s", defender.getName(),getName());
        }
        if (!defender.health.isAlive()) {
            onKilledUnit(defender);
        }
        msg.send(combatResults);
    }

    protected abstract void onKilledUnit(Unit defender) ;

    public String description() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth(), getAttackPoints(), getDefensePoints());
    }

    public boolean isAlive() {
        return health.isAlive();
    }

    public int getExperience() {
        return experience;
    }
}
