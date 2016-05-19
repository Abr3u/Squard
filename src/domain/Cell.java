package domain;

public class Cell {

	private CellColor color; 
	private Integer line;
	private Integer column;
	
	public Cell(Integer line, Integer column) {
		this.line = line;
		this.column = column;
		this.color = CellColor.WHITE;
	}
	
	public void changeColor(CellColor newcolor){
		this.color = newcolor;
	}
	
	public String toString(){
		return ""+line+"#"+column;
	}
}
