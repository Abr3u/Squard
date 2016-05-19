package domain;

import java.util.ArrayList;

import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;

public class Match {
	
	private Board myBoard;
	private Integer maxPlayers;
	private ArrayList<Player> players;
	
	public Match(Integer players) {
		this.maxPlayers = players;
		this.myBoard = new Board(10, 20);
	}
	
	public void addPlayer(Player p) throws MatchFullException{
		if(players.size() < maxPlayers){
			this.players.add(p);
		}else{
			throw new MatchFullException();
		}
	}
	
	public void startMatch() throws NotEnoughPlayersException{
		if(players.size()<maxPlayers){
			throw new NotEnoughPlayersException();
		}
		setupInitialPlayerPositions();
	}

	private void setupInitialPlayerPositions() {
		for(Player p : players){
			p.setCurrentPosition(BoardPositions.BotLeft.toCell(myBoard));
		}
		
	}
}
