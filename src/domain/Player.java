package domain;

public class Player {

	private String id;
	private Cell currentPosition;

	public Player(String id) {
		this.id = id;
	}
	
	public void setCurrentPosition(Cell position){
		this.currentPosition = position;
	}
	
	public Cell getCurrentPosition(){
		return currentPosition;
	}
	
	public String getId(){
		return id;
	}
}
