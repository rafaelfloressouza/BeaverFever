package tools;

import world.Tile;
import world.World;

public class FieldOfView{
    private World world;

    // A list of all the tiles that are visible
    private boolean[][] visible;
    public boolean isVisible(int x, int y){
        return x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
    }

    // A list of all current tiles, based on if the player has seen them or not
    private Tile[][] tiles;
    public Tile tile(int x, int y){
        return tiles[x][y];
    }
    
    // A check to see if a player has seen a current tile
    public boolean hasSeen(int x, int y) {
    	return !(tiles[x][y] == Tile.NULL);
    }

    // A constructor that initializes all tiles of this FOV to null (has not seen anything yet)
    public FieldOfView(World world){
        this.world = world;
        this.visible = new boolean[world.width()][world.height()];
        this.tiles = new Tile[world.width()][world.height()];
    
        for (int x = 0; x < world.width(); x++){
            for (int y = 0; y < world.height(); y++){
                    tiles[x][y] = Tile.NULL;
            }
        }
    }
    
    /**
     * Iterates through every space within your vision radius
     * Draws a line to that space that is blocked by walls
     * Sets seen tiles to the actual world tiles when you can see them
     */
    public void update(int wx, int wy, int r){
        visible = new boolean[world.width()][world.height()];
        for (int x = -r; x <= r; x++){
            for (int y = -r; y <= r; y++){
                if (x*x + y*y > r*r)
                    continue;
         
                if (wx + x < 0 || wx + x >= world.width() 
                 || wy + y < 0 || wy + y >= world.height())
                    continue;
         
                for (Point p : new Line(wx, wy, wx + x, wy + y)){
                    Tile tile = world.tile(p.x, p.y);
                    visible[p.x][p.y] = true;
                    tiles[p.x][p.y] = tile;
                    if (!world.tile(p.x, p.y).isWall())
                        continue;
                    break;
                }
            }
        }
    }
}