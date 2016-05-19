package domain;

public enum BoardPositions {
	BotLeft, BotRight, TopLeft, TopRight;

	public Cell toCell(Board b) {
		Integer maxL = b.getTotalLines();
		Integer maxC = b.getTotalColumns();
		switch (this.name()) {
		case "BotRight":
			return new Cell(0, maxC-1);
		case "TopLeft":
			return new Cell(maxL-1, 0);
		case "TopRight":
			return new Cell(maxL-1, maxC-1);
		default:
			return new Cell(0, 0);
		}
	}
}
