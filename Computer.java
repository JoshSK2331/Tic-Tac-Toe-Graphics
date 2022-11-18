public class Computer extends Player{
    public Computer(String s){
        super(s);
    }
    public String getNextMove (GameState state){
        return ((Ttt)(state)).pickMove();
    }
}