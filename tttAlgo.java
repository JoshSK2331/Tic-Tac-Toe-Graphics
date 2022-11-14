/*to go in the Ttt classpublic class tttAlgo{*/
    public Move quickWin(Ttt theoretical){
        
        Player p = new Player("player");
        Player c = new Player("computer")
        Ttt check = new Ttt(p, c);
        check.state = theoretical.state;
        for(Move m: Move(theoretical.getCurrentMoves())){
            //check for each move made
            theoretical.makeMove(m.getMove());
            if(theoretical.getWinner().equals(c)) return m;
            //check each move if it will make the computer win
        }
        return null;
    }
    public Move recursive(Ttt theoretical){
        if(quickWin(theoretical)!=null)
            return quickWin(theoretical);
        for(Move me: new Move(theoretical.getCurrentMoves())){
            theoretical.makeMove(me.getMove())
        }
    }
    public class Move{
        private int x;
        private int y;
        private Move(String moveName){
            String[] moveS = move.split(" ");
            x = Integer.parseInt(moveS[0]);
            y = Integer.parseInt(moveS[1]);
        }
        public String getMove(){
            return x+" "+y;
        }
    }
}