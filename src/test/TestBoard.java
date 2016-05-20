package test;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.Board;
import domain.BoardType;
import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidCellCoordinatesException;

public class TestBoard {
	
	private Integer MAX_LINES = 2048;
	private Integer MAX_COLUMNS = 2048;
	

	@Test
	public void testConstructorSuccessNormal() throws InvalidCellCoordinatesException{
		Board myBoard = null;
		try {
			myBoard = new Board(BoardType.MEDIUM);
		} catch (InvalidBoardDimensionsException e) {
			fail();
		}
		assertTrue(myBoard.getTotalLines()==30);
		assertTrue(myBoard.getTotalColumns()==50);
		assertTrue(myBoard.getBoardCells().size()==30*50);
		
	}
	
	@Test
	public void testConstructorSuccessBoundaries() throws InvalidCellCoordinatesException{
		testConstructorBoundariesLines();
		testConstructorBoundariesColumns();
	}
	
	public void testConstructorBoundariesLines() throws InvalidCellCoordinatesException {
		Board myBoard = null;
		try {
			myBoard = new Board(1,10);
		} catch (InvalidBoardDimensionsException e) {
			fail();
		}
		
		try {
			myBoard = new Board(MAX_LINES,10);
		} catch (InvalidBoardDimensionsException e) {
			fail();
		}
	}
	
	public void testConstructorBoundariesColumns() throws InvalidCellCoordinatesException {
		Board myBoard = null;
		try {
			myBoard = new Board(10,1);
		} catch (InvalidBoardDimensionsException e) {
			fail();
		}
		
		try {
			myBoard = new Board(10,MAX_COLUMNS);
		} catch (InvalidBoardDimensionsException e) {
			fail();
		}
	}
	

	@Test
	public void testConstructorBadCoordinates() throws InvalidCellCoordinatesException{
		testConstructorBadLines();
		testConstructorBadColumns();		
	}
	
	public void testConstructorBadLines() throws InvalidCellCoordinatesException{
		try {
			new Board(-1, 1);
			fail();
		} catch (InvalidBoardDimensionsException e) {
		}
		try {
			new Board(MAX_LINES+1, 1);
			fail();
		} catch (InvalidBoardDimensionsException e) {
		}
	}
	
	public void testConstructorBadColumns() throws InvalidCellCoordinatesException{
		try {
			new Board(1, -1);
			fail();
		} catch (InvalidBoardDimensionsException e) {
		}
		try {
			new Board(1, MAX_COLUMNS+1);
			fail();
		} catch (InvalidBoardDimensionsException e) {
		}
	}
	
}
