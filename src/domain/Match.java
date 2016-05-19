package domain;

import java.util.ArrayList;

import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;

public class Match {

	private Integer MAX_PLAYERS = 4;

	private Boolean started;
	private Board myBoard;
	private Integer maxPlayers;
	private ArrayList<Player> players;

	public Match(Integer maxPlayers) throws InvalidMaxPlayersForMatchException {
		if (maxPlayers < 1 || maxPlayers > MAX_PLAYERS) {
			throw new InvalidMaxPlayersForMatchException();
		}

		this.started = false;
		this.maxPlayers = maxPlayers;
		// TODO: remove this later
		try {
			this.myBoard = new Board(10, 20);
		} catch (InvalidBoardDimensionsException e) {
			e.printStackTrace();
		}
		this.players = new ArrayList<Player>();
	}

	public Integer getMaxPlayers() {
		return this.maxPlayers;
	}

	public Boolean hasStarted(){
		return this.started;
	}
	
	public Board getBoard() {
		return this.myBoard;
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	public void printPlayersCurrentPositions() {
		if (players.size() == 0) {
			return;
		}
		for (Player player : players) {
			System.out.println(player.getId() + " -> " + player.getCurrentPosition().toString());
		}
	}

	public void printPlayersCurrentColors() {
		if (players.size() == 0) {
			return;
		}
		for (Player player : players) {
			System.out.println(player.getId() + " -> " + player.getCurrentColor());
		}
	}

	public void addPlayer(Player p) throws MatchFullException {
		if (players.size() < maxPlayers) {
			this.players.add(p);
		} else {
			throw new MatchFullException();
		}
	}

	public void startMatch() throws NotEnoughPlayersException {
		if (players.size() < maxPlayers) {
			throw new NotEnoughPlayersException();
		}
		started = true;
		setupInitialBoardState();
	}

	private void setupInitialBoardState() {
		distributePlayersPositions();
		assignPlayersColors();
	}

	private void assignPlayersColors() {
		int i;
		for (i = 0; i < players.size();i++) {
				players.get(i).assignColor(CellColors.values()[i+1]);
			}
	}

	private void distributePlayersPositions() {
		int i;
		for (i = 0; i < players.size(); i++) {
			players.get(i).setCurrentPosition(BoardPositions.values()[i].toCell(myBoard));
		}
	}
}
