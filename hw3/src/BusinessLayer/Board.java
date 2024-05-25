package src.BusinessLayer;

import src.BusinessLayer.Interfaces.MessageCallback;
import src.BusinessLayer.Tiles.Empty;
import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Tiles.Player;
import src.BusinessLayer.Tiles.Tile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private List<Tile> tiles;
    private Player player;
    private List<Enemy> enemies;

    public Board(Tile[][] board){
        tiles = new ArrayList<>();
        for(Tile[] line : board){
            tiles.addAll(Arrays.asList(line));
        }
    }

    public void initBoard(List<Tile> tiles, Player p, List<Enemy> enemies){
        this.tiles = tiles;
        this.player = p;
        this.enemies = enemies;
    }

    public Player getPlayer(){
        return player;
    }

    public Board(){
    }

    public Tile getTile(int x, int y) {
        for(Tile t : tiles){
            if (t.getPosition().equals(Position.at(x, y))){
                return t;
            }
        }
        // Throw an exception if no such tile.
        return null;
    }


    public Tile getTile(Position p) {
        for(Tile t : tiles){
            if (t.getPosition().equals(p)){
                return t;
            }
        }
        // Throw an exception if no such tile.
        return null;
    }

    public void remove(Enemy e) {
        tiles.remove(e);
        enemies.remove(e);
        Position p = e.getPosition();
        tiles.add(new Empty(p));
    }

    @Override
    public String toString() {
        tiles = tiles.stream().sorted().collect(Collectors.toList());
        int lastRow = 0;
        String ans = "";
        for(Tile t: tiles){
            int newRow = t.getPosition().getX();
            if (lastRow != newRow)
                ans +="\n";
            ans = ans + t.toString();
            lastRow = newRow;
        }
        return ans;
    }



    public List<Tile> getTiles() {
        return tiles;
    }
}
