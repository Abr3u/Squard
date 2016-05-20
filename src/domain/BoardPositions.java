package domain;

import exceptions.InvalidCellCoordinatesException;

public enum BoardPositions {
	BotLeft, BotRight, TopLeft, TopRight;

	
	//TODO: this is a bit ugly
	public Cell toCell(Board b) {
		Integer maxL = b.getTotalLines();
		Integer maxC = b.getTotalColumns();
		switch (this.name()) {
		case "BotRight":
			try {
				return b.getCellByCoordinates(0, maxC - 1);
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
			
		case "TopLeft":
			try {
				return b.getCellByCoordinates(maxL-1, 0);
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
		case "TopRight":
			try {
				return b.getCellByCoordinates(maxL-1, maxC - 1);
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
		default:
			try {
				return b.getCellByCoordinates(0, 0);
			} catch (InvalidCellCoordinatesException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
