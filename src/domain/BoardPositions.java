package domain;

public enum BoardPositions {
	BotLeft, BotRight, TopLeft, TopRight;

	public Cell toCell(Board b) {
		Integer maxL = b.getTotalLines();
		Integer maxC = b.getTotalColumns();
		switch (this.name()) {
		case "BotLeft":
			return new Cell(0, 0);
		default:
			return new Cell(0, 0);
		}
	}
}
