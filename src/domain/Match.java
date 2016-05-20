package domain;

import java.util.ArrayList;

import org.w3c.dom.UserDataHandler;

import exceptions.CantFindPlayerByIdException;
import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;
import exceptions.NotInMatchException;

public class Match {

	private final Integer MAX_PLAYERS = 4;

	private Integer id;
	private Integer maxPlayers;
	private MatchState state;
	private BoardType boardType;
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
		// TODO: remove this later
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
		// TODO: remove this later
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

	public void addPlayer(Player p) throws MatchFullException {
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
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
			break;
		case "DOWN":
			try {
				players.get(i).moveDOWN();
			} catch (NotInMatchException e) {
				//this should never happen
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
			break;
		case "RIGHT":
			try {
				players.get(i).moveRIGHT();
			} catch (NotInMatchException e) {
				//this should never happen
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
			break;
		case "LEFT":
			try {
				players.get(i).moveLEFT();
			} catch (NotInMatchException e) {
				//this should never happen
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public void printPlayerCurrentPositions(int i) {
		System.out.println(players.get(i).getId() + " -> " + players.get(i).getCurrentPosition().toString());
	}
}
