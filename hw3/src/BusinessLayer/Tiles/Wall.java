package src.BusinessLayer.Tiles;

import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Position;

public class Wall extends Tile {
    protected Wall(Position position) {
        super('#');
        initialize(position);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

}
