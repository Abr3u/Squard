package domain;

import java.util.ArrayList;

import javax.activation.UnsupportedDataTypeException;

import org.w3c.dom.UserDataHandler;

import exceptions.CantFindPlayerByIdException;
import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;
import exceptions.NotInMatchException;
import exceptions.PlayerAlreadyInMatchException;

public class Match {

	private final Integer MAX_PLAYERS = 4;

	private Integer id;
	private Integer maxPlayers;
	private MatchState state;
	private BoardType boardType;
	private MatchThread myThread;
	private Board myBoard;
	private ArrayList<Player> players;

	public Match(Integer id, Integer maxPlayers) throws InvalidMaxPlayersForMatchException {
		if (maxPlayers < 1 || maxPlayers > MAX_PLAYERS) {
			throw new InvalidMaxPlayersForMatchException();
		}

		this.id = id;
		this.state = MatchState.CREATED;
		this.boardType = BoardType.MEDIUM;
		this.maxPlayers = maxPlayers;
		
		try {
			this.myBoard = new Board(boardType);
		} catch (InvalidBoardDimensionsException | InvalidCellCoordinatesException e) {
			e.printStackTrace();
		}
		this.players = new ArrayList<Player>();
	}

	public Match(Integer id, Integer maxPlayers, BoardType type) throws InvalidMaxPlayersForMatchException {
		if (maxPlayers < 1 || maxPlayers > MAX_PLAYERS) {
			throw new InvalidMaxPlayersForMatchException();
		}

		this.id = id;
		this.state = MatchState.CREATED;
		this.boardType = type;
		this.maxPlayers = maxPlayers;
		
		try {
			this.myBoard = new Board(boardType);
		} catch (InvalidBoardDimensionsException | InvalidCellCoordinatesException e) {
			e.printStackTrace();
		}
		this.players = new ArrayList<Player>();
	}

	public Integer getMaxPlayers() {
		return this.maxPlayers;
	}

	public Board getBoard() {
		return this.myBoard;
	}

	public Integer getId() {
		return this.id;
	}

	public MatchState getState() {
		return this.state;
	}

	public Player getPlayerById(String id) throws CantFindPlayerByIdException {
		for (Player player : players) {
			if (player.getId().equals(id)) {
				return player;
			}
		}
		throw new CantFindPlayerByIdException();
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

	public void printPlayerCurrentPositions(int i) {
		System.out.println(players.get(i).getId() + " -> " + players.get(i).getCurrentPosition().toString());
	}
	
	public void printBoard(){
		for(Cell cell : myBoard.getBoardCells()){
			System.out.println(cell.toString());
		}
	}
	
	public void addPlayer(Player p) throws MatchFullException, PlayerAlreadyInMatchException {
		if (players.size() < maxPlayers) {
			this.players.add(p);
			p.setCurrentMatch(this);
		} else {
			throw new MatchFullException();
		}
	}

	public void start() throws NotEnoughPlayersException {
		if (players.size() < maxPlayers) {
			throw new NotEnoughPlayersException();
		}
		state = MatchState.ONGOING;
		setupInitialBoardState();
		myThread = new MatchThread(myBoard);
	}

	private void setupInitialBoardState() {
		distributePlayersPositions();
		assignPlayersColors();
	}

	private void assignPlayersColors() {
		int i;
		for (i = 0; i < players.size(); i++) {
			players.get(i).assignColor(CellColor.values()[i + 1]);
		}
	}

	private void distributePlayersPositions() {
		int i;
		for (i = 0; i < players.size(); i++) {
			players.get(i).setCurrentPosition(BoardPositions.values()[i].toCell(myBoard));
		}
	}

	public void movePlayer(int i, Direction up) {
		switch (up.name()) {
		case "UP":
			try {
				players.get(i).moveUP();
			} catch (NotInMatchException e) {
				//this should never happen
			}
			break;
		case "DOWN":
			try {
				players.get(i).moveDOWN();
			} catch (NotInMatchException e) {
				//this should never happen
			} 
			break;
		case "RIGHT":
			try {
				players.get(i).moveRIGHT();
			} catch (NotInMatchException e) {
				//this should never happen
			}
			break;
		case "LEFT":
			try {
				players.get(i).moveLEFT();
			} catch (NotInMatchException e) {
				//this should never happen
			}
			break;
		}
	}


	public void end() {
		for(Player p:players){
			try {
				p.setCurrentMatch(null);
			} catch (PlayerAlreadyInMatchException e) {
				//this should never happen
				e.printStackTrace();
			}
		}
		this.state=MatchState.FINISHED;
		this.myBoard = null;
		this.myThread = null;
	}
	
	final class MatchThread implements Runnable{
		
		private Board board;
		
		public MatchThread(Board b) {
			this.board = b;
		}

		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				while(!state.equals(MatchState.FINISHED)){
					//wait events
					//update
					//draw
				}
			}
		}
	}
}
