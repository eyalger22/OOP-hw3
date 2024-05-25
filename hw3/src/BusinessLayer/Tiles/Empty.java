package src.BusinessLayer.Tiles;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Position;

public class Empty extends Tile {
    public Empty(Position position) {
        super('.');
        initialize(position);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

}
