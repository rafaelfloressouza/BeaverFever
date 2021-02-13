package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
         return true;
        if (obj == null)
         return false;
        if (!(obj instanceof Point))
         return false;
        Point other = (Point) obj;
        if (x != other.x)
         return false;
        if (y != other.y)
         return false;
        return true;
    }
    
    /**
     * Get all 8 neighbors of this point
     */
    public List<Point> neighbors8(){
        List<Point> points = new ArrayList<Point>();
      
        for (int ox = -1; ox < 2; ox++){
            for (int oy = -1; oy < 2; oy++){
                if (ox == 0 && oy == 0)
                    continue;
        
                points.add(new Point(x+ox, y+oy));
            }
        }

        Collections.shuffle(points);
        return points;
    }
    
    /**
     * Get all 4 neighbors of this point
     */
    public List<Point> neighbors4(){
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(x+1, y));
        points.add(new Point(x-1, y));
        points.add(new Point(x, y+1));
        points.add(new Point(x, y-1));
        Collections.shuffle(points);
        return points;
    }
}