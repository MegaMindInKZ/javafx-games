package sample;

import java.util.Scanner;

public class Map {
    Wall[][] walls;
    Position tankLocation;
    private int size;
    Map(Scanner input) throws InvalidMapException{
        size = input.nextInt();
        walls = new Wall[size][size];
        if(size == 0){
            throw new InvalidMapException("Map size can not be zero");
        }
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(!input.hasNext()){
                    throw  new InvalidMapException("Not enough map elements");
                }
                char c = input.next().charAt(0);
                if(c == 'P'){
                    tankLocation = new Position(j, i);
                }
                else if(c == '1'){
                    walls[j][i] = new SteelWall(new Position(j, i));
                }
                else if (c == '2') {
                    walls[j][i] = new BrickWall(new Position(j, i));
                }
                else if(c == '3'){
                    walls[j][i] = new Tree(new Position(j, i));
                }
                else if (c == '4') {
                    walls[j][i] = new Water(new Position(j, i));
                }
            }
        }

    }
    Position getTankLocation(){
        return tankLocation;
    }
    Wall getValueAt(Position pos){
        if(walls[pos.getX()][pos.getY()] == null){
            return null;
        }
        else{
            return walls[pos.getX()][pos.getY()];
        }
    }
    int getSize(){
        return size;
    }
}
