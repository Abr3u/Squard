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
	private Canvas myCanvas;

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

	public Canvas getCanvas(){
		return myCanvas;
	}
	
	public int getRATIO(){
		return RATIO;
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

	/* -------------------------
	 * ---------LOGIC
	   -------------------------*/
	
	
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
		myCanvas = getNewCanvas();
		
		populateScoreBoard();
		setupInitialBoardState();
		drawInitialBoard();
	}

	private void drawInitialBoard() {
		int lines = myBoard.getTotalLines();
		int columns = myBoard.getTotalColumns();
		
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		Group group = new Group();
		
		drawBoardCells(gc,  lines, columns, RATIO);
		drawPlayersOnBoard(gc,lines,columns,RATIO);
		group.getChildren().add(myCanvas);

		updateMyStage(group, lines*RATIO, columns*RATIO);
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
			Cell cell = BoardPositions.values()[i].toCell(myBoard);
			Player player = players.get(i);
			player.setCurrentPosition(cell);
			player.addCellToPath(cell);
		}
	}

	public void movePlayer(int i, Direction dir) {
		switch (dir.name()) {
		case "UP":
			try {
				Player p=players.get(i);
				p.moveUP();
				updatePlayerPositionStage(p);
			} catch (NotInMatchException e) {
				// this should never happen
			}
			break;
		case "DOWN":
			try {
				Player p=players.get(i);
				p.moveDOWN();
				updatePlayerPositionStage(p);
			} catch (NotInMatchException e) {
				// this should never happen
			}
			break;
		case "RIGHT":
			try {
				Player p=players.get(i);
				p.moveRIGHT();
				updatePlayerPositionStage(p);
			} catch (NotInMatchException e) {
				// this should never happen
			}
			break;
		case "LEFT":
			try {
				Player p=players.get(i);
				p.moveLEFT();
				updatePlayerPositionStage(p);
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
	
	/* -------------------------
	 * --------------JAVAFX
	   --------------------*/
	
	private Canvas getNewCanvas() {
		return new Canvas(myBoard.getTotalLines()*RATIO, myBoard.getTotalColumns()*RATIO);
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
			updatePlayerPositionStage(player);
		}
	}

	public void setFillColorByPlayer(GraphicsContext gc, Player player) {
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
	}

	private void drawBoardCells(GraphicsContext gc, int lines, int columns, int ratio) {
		for(Cell cell : myBoard.getBoardCells()){
			cell.draw(gc,ratio);
		}
	}

	public void keyPressed(KeyEvent e) {
		KeyCode keyCode = e.getCode();
	    switch( keyCode ) {
	        case UP:
	            movePlayer(0, Direction.LEFT); 
	            break;
	        case DOWN:
	        	movePlayer(0, Direction.RIGHT);  
	            break;
	        case LEFT:
	        	movePlayer(0, Direction.DOWN); 
	            break;
	        case RIGHT :
	        	movePlayer(0, Direction.UP);
	            break;
	     }
	}

	private void updatePlayerPositionStage(Player p) {
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		Group group = new Group();
		
		setFillColorByPlayer(gc, p);
		p.getCurrentPosition().fill(gc, RATIO);
		p.draw(gc,RATIO);
		group.getChildren().add(myCanvas);
		updateMyStage(group, myBoard.getTotalLines()*RATIO, myBoard.getTotalColumns()*RATIO);
		
	}

}
