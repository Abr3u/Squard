package domain;

import java.util.HashSet;

import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidPlayerIdException;
import exceptions.NotInMatchException;
import exceptions.PlayerAlreadyInMatchException;

public class Player {

	private final Integer MAX_ID_LENGTH = 30;

	private String id;
	private Cell currentPosition;
	private CellColor color;
	private Match currentMatch;

	private HashSet<Cell> currentPath;
	
	public Player(String id) throws InvalidPlayerIdException {
		if (id == null || id.isEmpty() || id.length() < 3 || id.length() > MAX_ID_LENGTH) {
			throw new InvalidPlayerIdException();
		}
		this.id = id;
	}
	
	public void setCurrentMatch(Match m) throws PlayerAlreadyInMatchException {
		if(m != null && currentMatch != null){
			throw new PlayerAlreadyInMatchException();
		}
		this.currentMatch = m;
		this.currentPath = new HashSet<Cell>();
	}

	public void setCurrentPosition(Cell position) {
		this.currentPosition = position;
	}

	public Cell getCurrentPosition() {
		return this.currentPosition;
	}

	public void assignColor(CellColor color) {
		this.color = color;
		this.currentPosition.changeColor(color);
	}

	public void moveUP() throws NotInMatchException{
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}

		Cell newCell = null;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine()+1, currentPosition.getColumn());
		} catch (InvalidCellCoordinatesException e) {
			return;
		}
		
		if (this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
		newCell.changeColor(this.color);
	}

	public void moveDOWN() throws NotInMatchException{
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell = null;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine() - 1, currentPosition.getColumn());
		} catch (InvalidCellCoordinatesException e) {
			return;
		}
		
		if (newCell != null && this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
		newCell.changeColor(this.color);
	}

	public void moveLEFT() throws NotInMatchException{
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine(), currentPosition.getColumn() - 1);
		} catch (InvalidCellCoordinatesException e) {
			return;
		}
		
		if (this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
		newCell.changeColor(this.color);
	}

	public void moveRIGHT() throws NotInMatchException{
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell = null;
		try {
			newCell = currentMatch.getBoard().getCellByCoordinates(currentPosition.getLine(), currentPosition.getColumn() + 1);
		} catch (InvalidCellCoordinatesException e) {
			return;
		}
		if (this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
		newCell.changeColor(this.color);
	}

	public String getId() {
		return this.id;
	}

	public CellColor getCurrentColor() {
		return this.color;
	}
}
