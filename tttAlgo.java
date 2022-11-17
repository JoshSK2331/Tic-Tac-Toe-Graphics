/*to go in the Ttt classpublic class tttAlgo{*/
    public String hasWin(Player p){
        String[] potentialMoves = this.getCurrentMoves();
        Ttt check;
        int counter = 0;
        while(counter<potentialMoves.length){
            check = this.clone();
            check.makeMove(potentialMoves[counter]);
            if(check.getWinner == p)
                return potentialMoves[i];
            counter++;
        }
    }
    
    public String pickMove(){
        Player p = new Player("play!");
        Player c = new Player("computer!")
        Ttt temp = new Ttt(p, c);
        temp.state = this.state;
        String winningMove = hasWin(c);
            if(winningMove!=null)){
                return winningMove;
            }

            String currentMoves = this.getCurrentMoves();
            ArrayList<String> winningMoves = new ArrayList<String>();
            for(int i = 0; i<currentMoves.length; i++){
                temp.state = this.state;
                temp.makeMove(currentMoves[i]);
                if(hasWin(p)==null){
                    winningMoves.add(currentMoves[i]);
                }
            }
            if(winningMoves.size()==0){
                return currentMoves[0];
            }
            return winningMoves[(int)(winningMoves.length*Math.random()];
        }
    }