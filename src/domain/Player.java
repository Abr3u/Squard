package domain;

import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidPlayerIdException;
import exceptions.NotInMatchException;

public class Player {

	private final Integer MAX_ID_LENGTH = 30; 
	
	private String id;
	private Cell currentPosition;
	private CellColors color;
	private Match currentMatch;

	public Player(String id) throws InvalidPlayerIdException {
		if(id == null || id.isEmpty() || id.length()<3 || id.length() > MAX_ID_LENGTH){
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

	public void assignColor(CellColors color) {
		this.color = color;
	}

	public void moveUP() throws NotInMatchException {
		if(this.currentMatch == null){
			throw new NotInMatchException();
		}
		
		Cell newCell = null;
		try {
			newCell = new Cell(currentPosition.getLine()+1, currentPosition.getColumn());
		} catch (InvalidCellCoordinatesException e) {
			//this should never happen
			e.printStackTrace();
		}
		if(this.currentMatch.getBoard().validateCell(newCell)){
			this.currentPosition = newCell;
		}
	}

	public void moveDOWN() throws NotInMatchException {
		if(this.currentMatch == null){
			throw new NotInMatchException();
		}
		Cell newCell = null;
		try {
			newCell = new Cell(currentPosition.getLine()-1, currentPosition.getColumn());
		} catch (InvalidCellCoordinatesException e) {
			//this should never happen
			e.printStackTrace();
		}
		if(this.currentMatch.getBoard().validateCell(newCell)){
			this.currentPosition = newCell;
		}
	}

	public void moveLEFT() throws NotInMatchException {
		if(this.currentMatch == null){
			throw new NotInMatchException();
		}
		Cell newCell = null;
		try {
			newCell = new Cell(currentPosition.getLine(), currentPosition.getColumn()-1);
		} catch (InvalidCellCoordinatesException e) {
			//this should never happen
			e.printStackTrace();
		}
		if(this.currentMatch.getBoard().validateCell(newCell)){
			this.currentPosition = newCell;
		}
	}

	public void moveRIGHT() throws NotInMatchException {
		if(this.currentMatch == null){
			throw new NotInMatchException();
		}
		Cell newCell = null;
		try {
			newCell = new Cell(currentPosition.getLine(), currentPosition.getColumn()+1);
		} catch (InvalidCellCoordinatesException e) {
			//this should never happen
			e.printStackTrace();
		}
		if(this.currentMatch.getBoard().validateCell(newCell)){
			this.currentPosition = newCell;
		}
	}

	public String getId() {
		return this.id;
	}

	public CellColors getCurrentColor() {
		return this.color;
	}
}
