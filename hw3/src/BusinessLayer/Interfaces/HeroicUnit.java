package src.BusinessLayer.Interfaces;

import src.BusinessLayer.Tiles.Enemy;
import src.BusinessLayer.Tiles.Player;
import java.util.List;

public interface HeroicUnit {
    void castAbility(List<Enemy> enemies, Player player);
}
