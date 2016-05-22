package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import javax.swing.plaf.synth.SynthSpinnerUI;

import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidPlayerIdException;
import exceptions.NotInMatchException;
import exceptions.PlayerAlreadyInMatchException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {

	private final Integer MAX_ID_LENGTH = 30;

	private String id;
	private Integer score;
	private Cell currentPosition;
	private CellColor color;
	private Match currentMatch;

	private ArrayList<Cell> currentPath;

	public Player(String id) throws InvalidPlayerIdException {
		if (id == null || id.isEmpty() || id.length() < 3 || id.length() > MAX_ID_LENGTH) {
			throw new InvalidPlayerIdException();
		}
		this.score = 0;
		this.id = id;
	}

	public void setCurrentMatch(Match m) throws PlayerAlreadyInMatchException {
		if (m != null && currentMatch != null) {
			throw new PlayerAlreadyInMatchException();
		}
		this.currentMatch = m;
		this.score = 0;
		this.currentPath = new ArrayList<Cell>();
	}

	public void setCurrentPosition(Cell position) {
		if (this.currentPosition != null) {
			GraphicsContext gContext = currentMatch.getCanvas().getGraphicsContext2D();
			currentMatch.setFillColorByPlayer(gContext, this);
			currentPosition.fill(gContext, currentMatch.getRATIO());
		}
		this.currentPosition = position;
	}

	public Cell getCurrentPosition() {
		return this.currentPosition;
	}

	public void assignColor(CellColor color) {
		this.color = color;
		this.currentPosition.changeColor(color);
	}

	public void moveUP() throws NotInMatchException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}

		Cell newCell = null;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine() + 1,
					currentPosition.getColumn());
		} catch (InvalidCellCoordinatesException e) {
			return;
		}

		if (this.currentMatch.getBoard().validateCell(newCell)) {
			setCurrentPosition(newCell);
			addCellToPath(newCell);
		}
		newCell.changeColor(this.color);
		GraphicsContext gContext = currentMatch.getCanvas().getGraphicsContext2D();
		currentMatch.setFillColorByPlayer(gContext, this);
		newCell.fill(gContext, currentMatch.getRATIO());
	}

	public void moveDOWN() throws NotInMatchException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell = null;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine() - 1,
					currentPosition.getColumn());
		} catch (InvalidCellCoordinatesException e) {
			return;
		}

		if (newCell != null && this.currentMatch.getBoard().validateCell(newCell)) {
			setCurrentPosition(newCell);
			addCellToPath(newCell);
		}
		newCell.changeColor(this.color);
	}

	public void moveLEFT() throws NotInMatchException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine(),
					currentPosition.getColumn() - 1);
		} catch (InvalidCellCoordinatesException e) {
			return;
		}

		if (this.currentMatch.getBoard().validateCell(newCell)) {
			setCurrentPosition(newCell);
			addCellToPath(newCell);
		}
		newCell.changeColor(this.color);
	}

	public void moveRIGHT() throws NotInMatchException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell = null;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine(),
					currentPosition.getColumn() + 1);
		} catch (InvalidCellCoordinatesException e) {
			return;
		}
		if (this.currentMatch.getBoard().validateCell(newCell)) {
			setCurrentPosition(newCell);
			addCellToPath(newCell);
		}
		newCell.changeColor(this.color);
	}

	public void addCellToPath(Cell newCell) {
		if (currentPath.contains(newCell)) {
			if (currentPath.size() >= 4) {
				calculatePointsGained(newCell, this.currentPath);
			}
		} else {
			currentPath.add(newCell);
		}
	}

	private void calculatePointsGained(Cell endingCell, ArrayList<Cell> path) {
		int startingL = path.get(0).getLine();
		int startingC = path.get(0).getColumn();
		int endingL = endingCell.getLine();
		int endingC = endingCell.getColumn();
		System.out.println("calculatePointsGained -> "+path.size());
		System.out.println("starting "+path.get(0).toString());
		System.out.println("ending "+endingCell.toString());

		if (startingC == endingC && startingL == endingL) {
			
			ArrayList<Cell> ordered = new ArrayList<Cell>();
			path.stream().sorted().forEach(mycell -> ordered.add(mycell));
			
			startingL = ordered.get(0).getLine();
			startingC = ordered.get(0).getColumn();
			int highestL = 0;
			int highestC = 0;
			for (Cell c : ordered) {
				
				System.out.println("checking "+c.toString());
				int Ldiff = Math.abs(c.getLine()-startingL);
				int Cdiff = Math.abs(c.getColumn()-startingC);
				
				if (Ldiff > highestL) {
					System.out.println("hL "+c.toString()+" diff -> "+Ldiff);
					highestL = Ldiff;
				}
				if (Cdiff > highestC) {
					System.out.println("hC "+c.toString()+" diff -> "+Cdiff);
					highestC = Cdiff;
				}
			}
			System.out.println("HL -> " + highestL + " HC -> " + highestC);
			if (highestL == 0 || highestC == 0) {
				System.out.println("no points gained");
				return;// this should never happen
			}
			System.out.println("gained " + ((highestC + 1) * (highestL + 1)) + " points");
			this.score += (highestC + 1) * (highestL + 1);
			resetCurrentPath();
			System.out.println(this.id + " has " + score + " points");
		}else{
			System.out.println("nao coincidiam");
			ArrayList<Cell> newPath = new ArrayList<Cell>();
			boolean found = false;
			for(Cell cell : currentPath){
				if(found){
					System.out.println("adicionei "+cell.toString());
					newPath.add(cell);
				}
				if(cell.equals(endingCell)){
					System.out.println("encontrei e adicionei "+cell.toString());
					newPath.add(cell);
					found = true;
				}
			}
			calculatePointsGained(endingCell,newPath);
		}
	}

	private void resetCurrentPath() {
		for (Cell cell : currentPath) {
			GraphicsContext gContext = currentMatch.getCanvas().getGraphicsContext2D();
			int RATIO = currentMatch.getRATIO();

			cell.changeColor(CellColor.WHITE);
			gContext.setFill(Color.WHITE);
			cell.fill(gContext, RATIO);
			cell.draw(gContext, RATIO);
		}
		currentPath.clear();
		currentPath.add(currentPosition);
	}

	public String getId() {
		return this.id;
	}

	public CellColor getCurrentColor() {
		return this.color;
	}

	public void draw(GraphicsContext gc, int ratio) {
		gc.setFill(Color.BLACK);
		gc.fillRect(currentPosition.getLine() * ratio + (ratio*0.25), currentPosition.getColumn() * ratio + (ratio*0.25),
				ratio *0.5, ratio * 0.5);
	}
}
