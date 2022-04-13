package sample;

import javafx.geometry.Pos;

public class Path implements Comparable<Path>{
    Tank tank1;
    Tank tank2;
    int path;
    Map map;
    Position current;
    Path(Tank tank1, Tank tank2){
        this.tank1 = tank1;
        this.tank2 = tank2;
        map = tank1.map;
        current = tank1.getPosition();
        path = Math.abs(tank1.getPosition().getX() - tank2.getPosition().getX()) + Math.abs(tank1.getPosition().getY() - tank2.getPosition().getY());
    }

    @Override
    public int compareTo(Path o) {
        if(path > o.path){
            return -1;
        }
        else if(path < o.path){
            return 1;
        }
        else{
            return 0;
        }
    }
}
