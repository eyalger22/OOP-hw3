package src.BusinessLayer.Tiles;

import src.BusinessLayer.Interfaces.Visited;
import src.BusinessLayer.Interfaces.Visitor;
import src.BusinessLayer.Position;

public abstract class Tile implements Comparable<Tile>, Visited {
    protected char tile;
    protected Position position;

    protected Tile(char tile){
        this.tile = tile;
    }

    public void initialize(Position p){
        position = p;
    }

    public char getTile() {
        return tile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position p) {
        position = p;
    }

    @Override
    public int compareTo(Tile tile) {
        return getPosition().compareTo(tile.getPosition());
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }
}


