package src.BusinessLayer;

public class Health {
    private int pool; //max health
    private int amount;//health now

    public Health(int pool, int amount) {
        this.pool = pool;
        this.amount = amount;
    }

    public int getPool() {
        return pool;
    }

    public boolean setPool(int pool) {
        if (pool < amount)
            return false;
        this.pool = pool;
        return true;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount < 0)
            this.amount=0;
        else
            this.amount = amount;
    }

    public void reduceAmount(int reduce){
        setAmount(amount-reduce);
    }

    public void setHealthOnLevelUp(int level){
        setPool(pool + 10*level);
        amount = pool;
    }
    @Override
    public String toString(){
        return amount + "/" + pool;
    }

    public boolean isAlive() {
        return amount>0;
    }
}
