package test;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.Board;
import domain.Cell;
import domain.CellColor;
import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidCellCoordinatesException;

public class TestCell {	

	@Test
	public void testConstructorSuccessNormal(){
		Cell cell = null;
		try {
			cell = new Cell(2, 3);
		} catch (InvalidCellCoordinatesException e) {
			fail();
		}
		assertTrue(cell.getLine()==2);
		assertTrue(cell.getColumn()==3);
		assertTrue(cell.getColor()==CellColor.WHITE);
		assertEquals(""+2+"#"+3, cell.toString());
	}
	
	@Test
	public void testConstructorInvalidCoordinates(){
		Cell cell = null;
		try {
			cell = new Cell(0, 3);
			fail();
		} catch (InvalidCellCoordinatesException e) {
		}
		
		try {
			cell = new Cell(3, 0);
			fail();
		} catch (InvalidCellCoordinatesException e) {
		}
		
	}
	
	
	@Test
	public void testChangeColor() throws InvalidCellCoordinatesException{
		Cell cell = new Cell(4, 0);
		cell.changeColor(CellColor.BLUE);
		assertSame(CellColor.BLUE,cell.getColor());
	}
}
