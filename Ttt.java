import pkg.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Ttt implements GameState{
  private Player x;
  private Player o;
  private Player current;
  public Ttt(Player x, Player o){
    this.x = x;
    this.o = o;
    current = x;
  }
  public Enum Status{
    X,
    O,
    BLANK
  }
  public class Square extends Rectangle/*an individual TTT square, of which there shall be nine*/{
    public Status current = Status.BLANK;
    int row;
    int col;
    public Square(int row, int col){
      this.row = row;
      this.col = col;
    }
    public boolean setStatus(Status q){
      if(q = Status.BLANK||current != Status.BLANK) return false;
      current = q;
      return true;
    }
    public Status getStatus(){
      return current;
    }
    public String toString(){
      if(current == Status.X) return "X";
      if(current == Status.O) return "O";
      return "-";
    }
  }
  
  public static Ttt setup(){
	  
    Scanner reader = new Scanner(System.in);
    System.out.println("Player One, enter your name: ");
    Player x = new Player(reader.nextLine().trim()+ " (x)");
    System.out.println("\nPlayer Two, enter your name: ");
    Player o = new Player(reader.nextLine().trim()+" (o)");
	System.out.print("\n");
    
    return new Ttt(x,o);
	
  }
  
   // private String[][] state = {
    // {"-", "-", "-"}, 
    // {"-", "-", "-"},
    // {"-", "-", "-"}
  // };
  private Square[][] state = {
	  {new Square(0,0),new Square(0,1),new Square(0,2)},
	  {new Square(1,0),new Square(1,1),new Square(1,2)},
	  {new Square(2,0),new Square(2,1),new Square(2,2)}
  };
  
  public boolean isGameover(){
   return getWinner() != null || getCurrentMoves().size()==0;
  }
  
  public Player getWinner(){
    //it is impossible to win without either having [0, 0], [1, 1], or [2, 2]
    //three ways to win: diagonal, vertical, horizontal
    Status potentialWinner = state[0][0].getStatus();
    if(potentialWinner != null){
      if(potentialWinner == fromString(1, 1) && potentialWinner ==fromString(2, 2))
        return potentialWinner;//diagonal
      if(potentialWinner == fromString(0, 1) && potentialWinner == fromString(0, 2))
        return potentialWinner;//vertical
      if(potentialWinner == fromString(1, 0)&& potentialWinner == fromString(2, 0))
        return potentialWinner;//horizontal
    }
    if(potentialWinner != null){
      potentialWinner = fromString(1, 1);
      if(potentialWinner == fromString(2, 0) && potentialWinner ==fromString(0, 2))
        return potentialWinner;//diagonal
      if(potentialWinner == fromString(1, 0) && potentialWinner == fromString(1, 2))
        return potentialWinner;//vertical
      if(potentialWinner == fromString(0, 1)&& potentialWinner == fromString(2, 1))
        return potentialWinner;//horizontal
    }
    potentialWinner = fromString(2, 2);
    if(potentialWinner != null){
      if(potentialWinner == fromString(2, 0) && potentialWinner == fromString(2, 1))
        return potentialWinner;//vertical
      if(potentialWinner == fromString(0, 2)&& potentialWinner == fromString(0, 1))
        return potentialWinner;//horizontal
    }
    return null;
  }
  
  
  public Player getCurrentPlayer(){
    return current;
  }
  
  public ArrayList<String> getCurrentMoves(){
    ArrayList<String> result = new ArrayList<String>();
    for(int i = 0; i<state.length; i++)
      for(int j = 0; j<state.length; j++)
        if(state[i][j].getStatus() == Status.BLANK)
          result.add(i+" "+j);
    return result;
  }
  
  public int[] stringMoveToIntMove(String move){
    String[] moveS = move.split(" ");
    int[] moveI = {Integer.parseInt(moveS[0]),Integer.parseInt(moveS[1])};
    return moveI;
  }
  
  public void makeMove(String move){

    int[] latestMove = stringMoveToIntMove(move);

    if(state[latestMove[0]][latestMove[1]].getStatus() == Status.BLANK){

      if(getCurrentPlayer() == x){
        state[latestMove[0]][latestMove[1]].setStatus(Status.X);
      }
      if(getCurrentPlayer() == o){
        state[latestMove[0]][latestMove[1]].setStatus(Status.O);
      }
      togglePlayers();
    }
    else{
      System.out.println("NOOOOOOOOOOOOOOOOOOO");//test
    }
  }
  
  public String toString(){
    String outputS = "";
    for(String[] r:state){
      for(String c:r){
        outputS += +" ";
      }
      outputS += "\n";
    }
    return outputS;
  }
  
  //helper methods
  private Player fromStatus(Status s){
    if(s == Status.X)
       return x;
    if(s == Status.O)
       return o;
    return null;//a blank string
  }
  private void togglePlayers(){
    if(current == x)
      current = o;
    else if(current == o)
      current = x;
    else System.out.println("error with player names");
  }
}
