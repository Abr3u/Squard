package domain;

import java.util.ArrayList;

public class Board {
	
	private Integer tLines;
	private Integer tColumns;
	
	private ArrayList<Cell> boardCells;
	
	public Board(Integer tLines, Integer tColumns) {
		this.tLines = tLines;
		this.tColumns = tColumns;
		this.boardCells = new ArrayList<Cell>();
		populateBoard();
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

	public Integer getTotalLines() {
		return tLines;
	}
	
	public Integer getTotalColumns() {
		return tColumns;
	}
	
}
