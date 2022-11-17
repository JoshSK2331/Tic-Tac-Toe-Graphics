import java.util.Scanner;
import java.util.ArrayList;
public class Player{
	
  private String name; // name of this player
  private static ArrayList<Player> playerList = new ArrayList<Player>();
  
  public Player (String aName){ 
	name = aName;
	playerList.add(this);
  }
 
  public static ArrayList<Player> getPlayerList(){
	 return playerList;
  }	  
  
  public String getName(){ 
	return name; 
  }
  /** This implementation chooses the first valid move.
  * Ovenide this method in subclasses to define players with other strategies.
  * @param state the cunent state ofthe game; its curent player is this player.
  * @return a string representing the move chosen;
  * "no move" if no valid moves for the current player. */

  public String getNextMove (GameState state){ 
      Scanner er = new Scanner(System.in);
      String move = er.nextLine().trim();
    
      // later put in error checking to see if the player made an invalid move
      // either incorrect format or an impossible move
       if(!state.getCurrentMoves().contains(move)){
        System.out.println("\nInvalid move, try again\nPlease enter your move in the format \"row column\"");
        return getNextMove(state);
      }  
    
      if(!move.equals("")){
          return move;
      }
    
     
    
      return "no move";
 
   }
}
