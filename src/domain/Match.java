package domain;

import java.util.ArrayList;
import java.util.HashMap;

import javax.activation.UnsupportedDataTypeException;
import javax.swing.tree.ExpandVetoException;

import org.w3c.dom.UserDataHandler;

import exceptions.CantFindPlayerByIdException;
import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;
import exceptions.NotInMatchException;
import exceptions.PlayerAlreadyInMatchException;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Match {

	private final Integer MAX_PLAYERS = 4;
	private final int RATIO = 100;//each cell 100 pixels

	private Integer id;
	private Integer maxPlayers;
	private MatchState state;
	private BoardType boardType;
	private Board myBoard;
	private ArrayList<Player> players;
	private HashMap<Player, Integer> player_score;
	private Stage myStage;

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

	public void printBoard() {
		for (Cell cell : myBoard.getBoardCells()) {
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
		myStage = new Stage();
		populateScoreBoard();
		setupInitialBoardState();
		drawInitialBoard();
	}

	private void drawInitialBoard() {
		int lines, columns;
		lines = myBoard.getTotalLines();
		columns = myBoard.getTotalColumns();

		int canvasL = lines * RATIO;
		int canvasC = columns * RATIO;
		Group group = new Group();
		Canvas canvas = new Canvas(canvasL, canvasC);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		drawBoardCells(gc, lines, columns, RATIO);
		drawPlayersOnBoard(gc,lines,columns,RATIO);
		group.getChildren().add(canvas);

		updateMyStage(group, canvasL, canvasC);
	}

	private void updateMyStage(Group group, int canvasl, int canvasc) {
		Scene myScene = new Scene(group, canvasl, canvasc);
		myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				keyPressed(event);
			}

		});
		myStage.setScene(myScene);
		myStage.show();
	}

	private void drawPlayersOnBoard(GraphicsContext gc, int lines, int columns, int ratio) {
		for(Player player : players){
			switch(player.getCurrentColor().name()){
			case "RED":
				gc.setFill(Color.RED);
				break;
			case "BLUE":
				gc.setFill(Color.BLUE);
				break;
			case "YELLOW":
				gc.setFill(Color.YELLOW);
				break;
			case "GREEN":
				gc.setFill(Color.GREEN);
				break;
			}
			gc.fillRect(player.getCurrentPosition().getLine() * ratio,
					player.getCurrentPosition().getColumn() * ratio, ratio, ratio);
		}
	}

	private void drawBoardCells(GraphicsContext gc, int lines, int columns, int ratio) {
		int i, j;
		for (i = 0; i < lines; i++) {
			for (j = 0; j < columns; j++) {
				gc.strokeRect(i * ratio, j * ratio, ratio, ratio);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		KeyCode keyCode = e.getCode();
		System.out.println("key -> " + keyCode);
	}

	private void populateScoreBoard() {
		this.player_score = new HashMap<Player, Integer>();
		for (Player player : this.players) {
			this.player_score.put(player, 0);
		}
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
				// this should never happen
			}
			break;
		case "DOWN":
			try {
				players.get(i).moveDOWN();
			} catch (NotInMatchException e) {
				// this should never happen
			}
			break;
		case "RIGHT":
			try {
				players.get(i).moveRIGHT();
			} catch (NotInMatchException e) {
				// this should never happen
			}
			break;
		case "LEFT":
			try {
				players.get(i).moveLEFT();
			} catch (NotInMatchException e) {
				// this should never happen
			}
			break;
		}
	}

	public void end() {
		for (Player p : players) {
			try {
				p.setCurrentMatch(null);
			} catch (PlayerAlreadyInMatchException e) {
				// this should never happen
				e.printStackTrace();
			}
		}
		this.state = MatchState.FINISHED;
		this.myBoard = null;
	}

}
