package test;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.Board;
import domain.Cell;
import domain.CellColors;
import exceptions.InvalidBoardDimensionsException;

public class TestCell {	

	@Test
	public void testConstructorSuccessNormal(){
		Cell cell = new Cell(2, 3);
		assertTrue(cell.getLine()==2);
		assertTrue(cell.getColumn()==3);
		assertTrue(cell.getColor()==CellColors.WHITE);
		assertEquals(""+2+"#"+3, cell.toString());
	}
	
	
	@Test
	public void testChangeColor(){
		Cell cell = new Cell(4, 0);
		cell.changeColor(CellColors.BLUE);
		assertSame(CellColors.BLUE,cell.getColor());
	}
}
