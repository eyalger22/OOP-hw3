package src.BusinessLayer.Interfaces;

import src.BusinessLayer.Tiles.Empty;
import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Wall;

public interface Visitor {

    public void visit(Wall wall);
    public void visit(Empty empty);
    public void visit(Player player);
    public void visit(Enemy enemy);
}
