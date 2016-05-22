package domain;

import java.util.ArrayList;

import exceptions.InvalidCellCoordinatesException;

public enum BoardType {
	SMALL, MEDIUM, LARGE;

	public void populateBoard(Board b) {
		switch (this.name()) {
		case "SMALL":
			fillBoardCellsByDimensions(b, 5, 7);
			break;
		case "LARGE":
			fillBoardCellsByDimensions(b, 70, 100);
			break;
		case "MEDIUM":
			fillBoardCellsByDimensions(b, 30, 50);
			break;
		}
	}
	
	private void fillBoardCellsByDimensions(Board board, int lines, int columns){
		int i,j;
		for(i=0;i<lines; i++){
			for(j = 0; j< columns; j++){
				Cell newCell = null;
				try {
					newCell = new Cell(i, j);
				} catch (InvalidCellCoordinatesException e) {
					e.printStackTrace();
				}
				board.getBoardCells().add(newCell);
			}
		}
	}

	public Integer getTotalLines() {
		switch (this.name()) {
		case "SMALL":
			return 5;

		case "LARGE":
			return 70;
		case "MEDIUM":
			return 30;
		}
		return null;
	}
	
	public Integer getTotalColumns() {
		switch (this.name()) {
		case "SMALL":
			return 7;

		case "LARGE":
			return 100;
		case "MEDIUM":
			return 50;
		}
		return null;
	}
}
