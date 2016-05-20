package domain;

import java.util.ArrayList;

import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidCellCoordinatesException;

public class Board {
	
	private Integer tLines;
	private Integer tColumns;
	private BoardType myType;
	private final Integer MAX_LINES = 2048;
	private final Integer MAX_COLUMNS = 2048;
	
	private ArrayList<Cell> boardCells;
	
	public Board(BoardType type) throws InvalidBoardDimensionsException, InvalidCellCoordinatesException {
		Integer l,c;
		l = type.getTotalLines();
		c = type.getTotalColumns();
		
		if(!validateBoard(l,c)){
			throw new InvalidBoardDimensionsException();
		}
		this.myType = type;
		this.tLines = l;
		this.tColumns = c;
		this.boardCells = new ArrayList<Cell>();
		myType.populateBoard(this);
	}

	public Board(Integer l, Integer c) throws InvalidBoardDimensionsException{
		System.out.println("USED BAD BOARD CONSTRUCTOR");
		if(!validateBoard(l,c)){
			throw new InvalidBoardDimensionsException();
		}
	}
	
	public Cell getCellByCoordinates(Integer l, Integer c) throws InvalidCellCoordinatesException{
		int index = l*getTotalColumns()+c ;
		if(l < 0 || c <0 || l>MAX_LINES || c > MAX_COLUMNS || index > boardCells.size()-1){
			throw new InvalidCellCoordinatesException();
		}
		return this.boardCells.get(l*getTotalColumns()+c);
	}
	
	private boolean validateBoard(Integer lines, Integer columns) {
		if(lines <= 0 || columns <=0 || lines>MAX_LINES || columns > MAX_COLUMNS){
			return false;
		}
		return true;
	}
	
	public ArrayList<Cell> getBoardCells(){
		return boardCells;
	}

	public Integer getTotalLines() {
		return tLines;
	}
	
	public Integer getTotalColumns() {
		return tColumns;
	}
	
	public Boolean validateCell(Cell c){
		if(c.getLine()>=0 && c.getLine()<tLines && c.getColumn()>=0 && c.getColumn()<tColumns){
			return true;
		}
		return false;
	}
	
}
