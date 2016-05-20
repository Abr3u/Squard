package domain;

import exceptions.InvalidCellCoordinatesException;

public class Cell {

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
}
