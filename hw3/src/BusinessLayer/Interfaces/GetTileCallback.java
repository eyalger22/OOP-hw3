package src.BusinessLayer.Interfaces;

import src.BusinessLayer.Position;
import src.BusinessLayer.Tiles.Tile;

public interface GetTileCallback {
    public Tile getTileAt(Position p);
}
