package domain;

import java.util.ArrayList;

import exceptions.CantFindMatchByIdException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;
import exceptions.PlayerAlreadyInMatchException;

public class MatchManager {
	
	private ArrayList<Match> myMatches;
	private Integer matchCount;
	
	public MatchManager() {
		matchCount = 0;
		myMatches = new ArrayList<Match>();
	}
	
	public Integer createNewMatch(Integer maxPlayers){
		Match newMatch = null;
		try {
			matchCount++;
			newMatch = new Match(matchCount,maxPlayers);
			myMatches.add(newMatch);
		} catch (InvalidMaxPlayersForMatchException e) {
			matchCount--;
			e.printStackTrace();
		}
		return newMatch.getId();
	}
	
	public Integer createNewMatch(Integer maxPlayers, BoardType type){
		Match newMatch = null;
		try {
			matchCount++;
			newMatch = new Match(matchCount,maxPlayers, type);
			myMatches.add(newMatch);
		} catch (InvalidMaxPlayersForMatchException e) {
			matchCount--;
			e.printStackTrace();
		}
		return newMatch.getId();
	}
	
	public Match getMatchById(Integer id){
		for(Match match : myMatches){
			if(match.getId().equals(id)){
				return match;
			}
		}
		return null;
		//throw new CantFindMatchByIdException();
	}
	
	public void startMatchById(Integer id){
		try {
			getMatchById(id).start();
		} catch (NotEnoughPlayersException e) {
			e.printStackTrace();
		}
	}
	
	
	public void startAllMatches(){
		for(Match match : myMatches){
			try {
				match.start();
			} catch (NotEnoughPlayersException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void endMatchById(Integer id){
		getMatchById(id).end();
		myMatches.remove(id);
	}

	//TODO este null
	public MatchState getMatchStateById(Integer id){
		
			return getMatchById(id).getState();
		
	}

	public void addPlayerToMatch(Player abreu, Integer mID) {
		try {
			getMatchById(mID).addPlayer(abreu);
		} catch (MatchFullException e) {
			e.printStackTrace();
		} catch (PlayerAlreadyInMatchException e) {
			e.printStackTrace();
		}
	}
	
}
