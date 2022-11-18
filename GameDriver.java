import pkg.*;
import java.util.Scanner;


public class GameDriver implements InputControl, InputKeyControl{
	
	private GameState state;
	
	public GameDriver(GameState initial){
		state = initial;
	}
	public GameDriver(){
	}
	
	static boolean running = true;
	/*
	get players' names from command line
	save into player variables
	construct a gameState(player x, player o);
	make moves
	*/
	static Player current;
	public void play(){
		if(state.isGameover()){ //checks for winner or a draw
			System.out.println(state);
			if(state.getWinner()!=null){
				System.out.println(state.getWinner().getName()+" wins!");
			}
			else{
				System.out.println("Game ends in a draw");
			}
			running = false;
		}
		System.out.println(state);
		
		current = state.getCurrentPlayer();
		System.out.println("current player is "+current);
		String nextMove = current.getNextMove(state);
		
		if(nextMove.equals("no move")){
			System.out.println("Player "+current+" has no move");
		}
		else{
			System.out.println("\n"+current.getName() +" makes move "+nextMove+"\n");
		}
		state.makeMove(nextMove);
	}
	static Ttt game;
	public static void main(String[] args){
		KeyController kC = new KeyController(Canvas.getInstance(),new GameDriver());
		MouseController mC = new MouseController(Canvas.getInstance(),new GameDriver());
		game = new Ttt();
		new GameDriver(game).play();
		System.out.println("\nplay again? y/n");
		//used to be the most beautiful line of code but tim killed it
		//if(new Scanner(System.in).nextLine().trim().toUpperCase().contains("Y")) main(args);
		if(new Scanner(System.in).nextLine().trim().toUpperCase().contains("Y")){
			System.out.print("\n");
			main(args);
		}
		else System.out.println("\nSO LONG");
	}
	public void onMouseClick(double x, double y) {
		
		
		y -= 30;
		Ttt.Square apple = Ttt.whichSquare(x,y);
		if(apple != null){
			game.makeMove(apple.getRow()+" "+apple.getCol());
		}
		else{
			
		}
		Ttt.Square[][] temp = game.getState();
		
		//text outputs the board
		for(int r = 0;r<temp.length;r++){
			for(int c = 0;c<temp[0].length;c++){
				System.out.print(temp[r][c]+" ");
				current.nextMove = temp[r][c];
				synchronized(this){
					notifyAll();
				}
			}
			System.out.print("\n");
		}
		
		System.out.println(game.isGameover());
	}
	
	public void keyPress(String s) {
		// enter code here

	}
}
