package domain;

import exceptions.InvalidCellCoordinatesException;

public class Cell {

	private CellColors color; 
	private Integer line;
	private Integer column;
	
	public Cell(Integer line, Integer column) throws InvalidCellCoordinatesException {
		if(line < 0 && column < 0){
			throw new InvalidCellCoordinatesException();
		}
		this.line = line;
		this.column = column;
		this.color = CellColors.WHITE;
	}
	
	public void changeColor(CellColors newcolor){
		this.color = newcolor;
	}
	
	public String toString(){
		return ""+line+"#"+column;
	}
	
	public Integer getLine(){
		return this.line;
	}
	
	public Integer getColumn(){
		return this.column;
	}
	
	public CellColors getColor(){
		return this.color;
	}
}
