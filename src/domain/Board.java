package domain;

import java.math.BigInteger;
import java.util.ArrayList;

import exceptions.InvalidBoardDimensionsException;

public class Board {
	
	private Integer tLines;
	private Integer tColumns;
	private final Integer MAX_LINES = 2048;
	private final Integer MAX_COLUMNS = 2048;
	
	private ArrayList<Cell> boardCells;
	
	public Board(Integer tLines, Integer tColumns) throws InvalidBoardDimensionsException {
		if(!validateBoard(tLines,tColumns)){
			throw new InvalidBoardDimensionsException();
		}
		this.tLines = tLines;
		this.tColumns = tColumns;
		this.boardCells = new ArrayList<Cell>();
		populateBoard();
	}

	private boolean validateBoard(Integer lines, Integer columns) {
		if(lines <= 0 || columns <=0 || lines>MAX_LINES || columns > MAX_COLUMNS){
			return false;
		}
		return true;
	}

	private void populateBoard() {
		int i,j;
		for(i=0;i<tLines; i++){
			for(j = 0; j< tColumns; j++){
				Cell newCell = new Cell(i, j);
				this.boardCells.add(newCell);
			}
		}
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
	
}
