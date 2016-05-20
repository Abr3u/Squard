package domain;

import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidPlayerIdException;
import exceptions.NotInMatchException;

public class Player {

	private final Integer MAX_ID_LENGTH = 30;

	private String id;
	private Cell currentPosition;
	private CellColor color;
	private Match currentMatch;

	public Player(String id) throws InvalidPlayerIdException {
		if (id == null || id.isEmpty() || id.length() < 3 || id.length() > MAX_ID_LENGTH) {
			throw new InvalidPlayerIdException();
		}
		this.id = id;
	}

	public void setCurrentMatch(Match m) {
		this.currentMatch = m;
	}

	public void setCurrentPosition(Cell position) {
		this.currentPosition = position;
	}

	public Cell getCurrentPosition() {
		return this.currentPosition;
	}

	public void assignColor(CellColor color) {
		this.color = color;
	}

	public void moveUP() throws NotInMatchException, InvalidCellCoordinatesException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}

		Cell newCell = new Cell(currentPosition.getLine() + 1, currentPosition.getColumn());
		
		if (this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
	}

	public void moveDOWN() throws NotInMatchException, InvalidCellCoordinatesException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell =  new Cell(currentPosition.getLine() - 1, currentPosition.getColumn());
		
		if (newCell != null && this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
	}

	public void moveLEFT() throws NotInMatchException, InvalidCellCoordinatesException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell = new Cell(currentPosition.getLine(), currentPosition.getColumn() - 1);
		
		if (this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
	}

	public void moveRIGHT() throws NotInMatchException, InvalidCellCoordinatesException {
		if (this.currentMatch == null) {
			throw new NotInMatchException();
		}
		Cell newCell = new Cell(currentPosition.getLine(), currentPosition.getColumn() + 1);
		if (this.currentMatch.getBoard().validateCell(newCell)) {
			this.currentPosition = newCell;
		}
	}

	public String getId() {
		return this.id;
	}

	public CellColor getCurrentColor() {
		return this.color;
	}
}
