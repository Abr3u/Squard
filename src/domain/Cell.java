package domain;

import exceptions.InvalidCellCoordinatesException;
import javafx.scene.canvas.GraphicsContext;

public class Cell implements Comparable<Cell>{

	private CellColor color; 
	private Integer line;
	private Integer column;
	
	public Cell(Integer line, Integer column) throws InvalidCellCoordinatesException {
		if(line < 0 && column < 0){
			throw new InvalidCellCoordinatesException();
		}
		this.line = line;
		this.column = column;
		this.color = CellColor.WHITE;
	}
	
	public void changeColor(CellColor newcolor){
		this.color = newcolor;
		
	}
	
	public String toString(){
		return "["+line+"#"+column+"] -> "+color;
	}
	
	public Integer getLine(){
		return this.line;
	}
	
	public Integer getColumn(){
		return this.column;
	}
	
	public CellColor getColor(){
		return this.color;
	}

	public void draw(GraphicsContext gc, int ratio) {
		gc.strokeRect(this.line * ratio, this.column * ratio, ratio, ratio);
	}
	
	public void fill(GraphicsContext gc, int ratio) {
		gc.fillRect(this.line * ratio, this.column * ratio, ratio, ratio);
	}

	@Override
	public int compareTo(Cell o) {
		if(this.line<o.getLine()){
			return -1;
		}
		if(this.line>o.getLine()){
			return 1;
		}
		if(this.column<o.getColumn()){
			return -1;
		}
		if(this.column>o.getColumn()){
			return 1;
		}
		return 0;
	}
}
