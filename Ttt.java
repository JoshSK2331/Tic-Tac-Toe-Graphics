import pkg.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Ttt implements GameState{
  private Player x;
  private Player o;
  private Player current;
  
   
   // private String[][] state = {
    // {"-", "-", "-"}, 
    // {"-", "-", "-"},
    // {"-", "-", "-"}
  // };
  
  private static Square[][] state = {
	  {new Square(0,0),new Square(0,1),new Square(0,2)},
	  {new Square(1,0),new Square(1,1),new Square(1,2)},
	  {new Square(2,0),new Square(2,1),new Square(2,2)}
	};
  
  public Ttt(Player x, Player o){
    this.x = x;
    this.o = o;
    current = x;
	for(Square[] r:state){
		for(Square c:r){
			c.draw();
		}
	}
  }
  
  public Ttt (){
	
    Scanner reader = new Scanner(System.in);
    System.out.println("Player One, enter your name: ");
    Player x = new Player(reader.nextLine().trim()+ " (x)");
    System.out.println("\nPlayer Two, enter your name or type cpu: ");
    String c = reader.nextLine().trim();
    Player o;
    if(c.equals("CPU")) o = new Computer("CPU "+" (o)");
    else o = new Player(reader.nextLine().trim()+" (o)");
	  System.out.print("\n");
    this.x = x;
	this.o = o;
	current = x;
	
  }
  
  public enum Status{
    X,
    O,
    BLANK
  }
  public static class Square extends Rectangle/*an individual TTT square, of which there shall be nine*/{
    public Status current = Status.BLANK;
    int row;
    int col;
    final static int factor = 100;
    public Square(int row, int col){
      super(col*factor, row*factor, factor, factor);
      this.row = row;
      this.col = col;
      this.draw();
    }
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
    public boolean setStatus(Status q){
      if(q == Status.BLANK||current != Status.BLANK) return false;
      current = q;
      this.draw();
      return true;
    }
    public Status getStatus(){
      draw();
      return current;
    }
    public String toString(){
      if(current == Status.X) return "X";
      if(current == Status.O) return "O";
      return " ";
    }
    @Override
    public void draw(){
      this.undraw();
      super.draw();
      Text label = new Text(super.getX(), super.getY(), this.toString());
      label.grow((factor*2/3)+(factor/6),(factor*2/3)+(factor/6));
      label.draw();
    }
	
	public boolean containsCoord(double x, double y){
		if(x>super.getX()&&x<super.getX()+super.getWidth()&&y>super.getY()&&y<super.getY()+super.getHeight()){
			return true;
		}
		return false;
	}
  public Square clone(){
    Square temp = new Square(this.row, this.col);
    temp.setStatus(this.getStatus());
    return temp;
  }
  }
  public static Square whichSquare(double x, double y){
	for(Square[] r:state){
		for(Square c:r){
			if(c.containsCoord(x,y)){
				return c;
			}
		}
	}
	return null;
  }
  


  
  public boolean isGameover(){
   return getWinner() != null || getCurrentMoves().size()==0;
  }
  
  public Player getWinner(){
    //it is impossible to win without either having [0, 0], [1, 1], or [2, 2]
    //three ways to win: diagonal, vertical, horizontal
    Status potentialWinner = state[0][0].getStatus();
	Player potentialWinnerP;
	if(potentialWinner == Status.X){
		potentialWinnerP = Player.getPlayerList().get(0);
	}
	else if(potentialWinner == Status.O){
		potentialWinnerP = Player.getPlayerList().get(1);
	}
	else{
		potentialWinnerP = null;
	}
	
    if(potentialWinner != null){
      if(potentialWinner == state[1][1].getStatus() && potentialWinner ==state[2][2].getStatus())
        return potentialWinnerP;//diagonal
      if(potentialWinner == state[0][1].getStatus() && potentialWinner == state[0][2].getStatus())
        return potentialWinnerP;//vertical
      if(potentialWinner == state[1][0].getStatus()&& potentialWinner == state[2][0].getStatus())
        return potentialWinnerP;//horizontal
    }
    potentialWinner = state[1][1].getStatus();
	if(potentialWinner == Status.X){
		potentialWinnerP = Player.getPlayerList().get(0);
	}
	else if(potentialWinner == Status.O){
		potentialWinnerP = Player.getPlayerList().get(1);
	}
	else{
		potentialWinnerP = null;
	}
    if(potentialWinner != null){
      if(potentialWinner == state[2][0].getStatus() && potentialWinner ==state[0][2].getStatus())
        return potentialWinnerP;//diagonal
      if(potentialWinner == state[1][0].getStatus() && potentialWinner == state[1][2].getStatus())
        return potentialWinnerP;//vertical
      if(potentialWinner == state[0][1].getStatus()&& potentialWinner == state[2][1].getStatus())
        return potentialWinnerP;//horizontal
    }
    potentialWinner = state[2][2].getStatus();
	if(potentialWinner == Status.X){
		potentialWinnerP = Player.getPlayerList().get(0);
	}
	else if(potentialWinner == Status.O){
		potentialWinnerP = Player.getPlayerList().get(1);
	}
	else{
		potentialWinnerP = null;
	}
    if(potentialWinner != null){
      if(potentialWinner == state[2][0].getStatus() && potentialWinner == state[2][1].getStatus())
        return potentialWinnerP;//vertical
      if(potentialWinner == state[0][2].getStatus()&& potentialWinner == state[0][1].getStatus())
        return potentialWinnerP;//horizontal
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
  
  public void makeMove(double x, double y){
	for(Square[] r:state){
		for(Square c:r){
			if(c.containsCoord(x,y)){
				Square contained = c;
			}
		}
	}
  }
  
  public String toString(){
    String outputS = "";
    for(Square[] r:state){
      for(Square c:r){
        outputS += c.toString()+" ";
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
  public void togglePlayers(){
    if(current == x)
      current = o;
    else if(current == o)
      current = x;
    else System.out.println("error with player names");
  }

  //helper method to pick moves
  public String hasWin(Player p){
        ArrayList<String> potentialMoves = this.getCurrentMoves();
        Ttt check;
        int counter = 0;
        while(counter<potentialMoves.size()){
            check = this.clone();
            check.makeMove(potentialMoves.get(counter));
            if(check.getWinner() == p)
                return potentialMoves.get(counter);
            counter++;
        }
        return null;
    }
    /*method to pick moves:
      not guaranteed to win
      guaranteed to not lose on the next move if possible
      randomly picks moves that meet this criterion
    */
    public String pickMove(){
        Player p = new Player("play!");
        Player c = new Player("computer!");
        Ttt temp = new Ttt(p, c);
        temp.state = this.cloneState();
        String winningMove = hasWin(c);
            if(winningMove!=null){
                return winningMove;
            }

            ArrayList<String> currentMoves = this.getCurrentMoves();
            ArrayList<String> winningMoves = new ArrayList<String>();
            for(int i = 0; i<currentMoves.size(); i++){
                temp.state = this.cloneState();
                temp.makeMove(currentMoves.get(i));
                if(hasWin(p)==null){
                    winningMoves.add(currentMoves.get(i));
                }
            }
            if(winningMoves.size()==0){
                return currentMoves.get(0);
            }
            return winningMoves.get((int)(winningMoves.size()*Math.random()));
        }
    public Square[][] cloneState(){
      Square[][] temp = this.state;
      for(Square[] e: temp)
        for(Square f: e)
          f = f.clone();
      return temp;
    }
    public Ttt clone(){
      Ttt temp = new Ttt(new Player(x.getName()), new Player(o.getName()));
      if(temp.getCurrentPlayer()!=this.getCurrentPlayer()) temp.togglePlayers();
      temp.state = cloneState();
      return temp;
    }
}
