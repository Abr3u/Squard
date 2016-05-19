package domain;

public class Player {

	private String id;
	private Cell currentPosition;
	private CellColors color;

	public Player(String id) {
		this.id = id;
	}
	
	public void setCurrentPosition(Cell position){
		this.currentPosition = position;
	}
	
	public Cell getCurrentPosition(){
		return this.currentPosition;
	}
	
	public void assignColor(CellColors color){
		this.color = color;
	}
	
	public String getId(){
		return this.id;
	}

	public CellColors getCurrentColor() {
		return this.color;
	}
}
