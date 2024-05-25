package src.BusinessLayer.Tiles;

public abstract class Enemy extends Unit {
    protected Enemy(char tile, String name, int healthCapacity, int attack, int defense, int experience) {
        super(tile,name,healthCapacity,attack,defense, experience);
    }

    public void visit(Enemy e) {
        return;
    }

    @Override
    public void visit(Player p) {
        combat(p);
    }

    public abstract void processStep(Player p);


    protected void onKilledUnit(Unit defender) {
        defender.onDeath();
    }
    @Override
    public void onDeath() {
        msgDeath.call();
    }
}
