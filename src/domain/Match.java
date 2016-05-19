package domain;

import java.util.ArrayList;

import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;

public class Match {
	
	private Board myBoard;
	private Integer maxPlayers;
	private ArrayList<Player> players;
	
	public Match(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
		this.myBoard = new Board(10, 20);
		this.players = new ArrayList<Player>();
	}
	
	public void printPlayersCurrentPositions(){
		if(players.size()==0){
			return;
		}
		for(Player player : players){
			System.out.println(player.getId()+" -> "+player.getCurrentPosition().toString());
		}
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
		int i,j;
		for(i=0;i<players.size();){
			for(j=0;j<BoardPositions.values().length;j++){
				players.get(i).setCurrentPosition(BoardPositions.values()[j].toCell(myBoard));
				i++;
			}
		}
		
	}
}
