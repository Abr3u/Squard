package test;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.Board;
import exceptions.InvalidBoardDimensionsException;

public class TestBoard {
	
	private Integer MAX_LINES = 2048;
	private Integer MAX_COLUMNS = 2048;
	

	@Test
	public void testConstructorSuccessNormal(){
		Board myBoard = null;
		try {
			myBoard = new Board(12, 30);
		} catch (InvalidBoardDimensionsException e) {
			fail();
		}
		assertTrue(myBoard.getTotalLines()==12);
		assertTrue(myBoard.getTotalColumns()==30);
		assertTrue(myBoard.getBoardCells().size()==30*12);
		
	}
	
	@Test
	public void testConstructorSuccessBoundaries(){
		testConstructorBoundariesLines();
		testConstructorBoundariesColumns();
	}
	
	public void testConstructorBoundariesLines() {
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
	
	public void testConstructorBoundariesColumns() {
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
	public void testConstructorBadCoordinates(){
		testConstructorBadLines();
		testConstructorBadColumns();		
	}
	
	public void testConstructorBadLines(){
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
	
	public void testConstructorBadColumns(){
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
