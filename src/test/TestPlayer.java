package test;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.Board;
import domain.Cell;
import domain.CellColor;
import domain.Match;
import domain.Player;
import exceptions.CantFindPlayerByIdException;
import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.InvalidPlayerIdException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;
import exceptions.NotInMatchException;
import exceptions.PlayerAlreadyInMatchException;

public class TestPlayer {
	
	@Test
	public void testConstructorSuccessNormal() {
		Player newPlayer = null;
		try {
			newPlayer = new Player("abreu");
		} catch (InvalidPlayerIdException e) {
			fail();
		}
		assertTrue(newPlayer.getId().equals("abreu"));
		assertNull(newPlayer.getCurrentColor());
		assertNull(newPlayer.getCurrentPosition());

	}

	@Test
	public void testConstructorInvalidId() {
		Player newPlayer = null;
		try {
			newPlayer = new Player("ab");
			fail();
		} catch (InvalidPlayerIdException e) {
		}

		try {
			String bigID = "A" + String.format("%030d", 4);
			newPlayer = new Player(bigID);
			fail();
		} catch (InvalidPlayerIdException e) {
		}
	}
	
	@Test
	public void testMoveWithoutMatch() throws InvalidPlayerIdException{
		Player player = new Player("abreu");
		try {
			player.moveDOWN();
			fail();
		} catch (NotInMatchException e) {
		}
	}
	
	private Match setupMovementTests() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, PlayerAlreadyInMatchException{
		Match myMatch = new Match(1,4);
		Player abreu = new Player("abreu");
		Player alice = new Player("alice");
		Player bob = new Player("bob");
		Player ricardo = new Player("ricardo");
		myMatch.addPlayer(abreu);
		myMatch.addPlayer(alice);
		myMatch.addPlayer(bob);
		myMatch.addPlayer(ricardo);
		myMatch.start();
		return myMatch;
	}
	
	@Test
	public void testMoveUp() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException, PlayerAlreadyInMatchException{
		Match match = setupMovementTests();
		testMoveUpPossible(match);
		testMoveUpImpossible(match);
	}
	
	public void testMoveUpPossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
	
		Player abreu = match.getPlayerById("abreu");
		
		Cell prevPosition = abreu.getCurrentPosition();
		try {
			abreu.moveUP();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getLine()+1 == abreu.getCurrentPosition().getLine());
		assertTrue(prevPosition.getColumn() == abreu.getCurrentPosition().getColumn());
		
	}
	
	public void testMoveUpImpossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
		
		Player bob = match.getPlayerById("bob");
		
		Cell prevPosition = bob.getCurrentPosition();
		try {
			bob.moveUP();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getLine() == bob.getCurrentPosition().getLine());
		assertTrue(prevPosition.getColumn() == bob.getCurrentPosition().getColumn());
		
	}
	
	@Test
	public void testMoveDown() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException, PlayerAlreadyInMatchException{
		Match match = setupMovementTests();
		testMoveDownPossible(match);
		testMoveDownImpossible(match);
	}
	
	public void testMoveDownPossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
	
		Player bob = match.getPlayerById("bob");
		
		Cell prevPosition = bob.getCurrentPosition();
		try {
			bob.moveDOWN();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getLine()-1 == bob.getCurrentPosition().getLine());
		assertTrue(prevPosition.getColumn() == bob.getCurrentPosition().getColumn());
		
	}
	
	public void testMoveDownImpossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
		
		Player abreu = match.getPlayerById("abreu");
		
		Cell prevPosition = abreu.getCurrentPosition();
		try {
			abreu.moveDOWN();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getLine() == abreu.getCurrentPosition().getLine());
		assertTrue(prevPosition.getColumn() == abreu.getCurrentPosition().getColumn());
		
	}
	
	@Test
	public void testMoveRight() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException, PlayerAlreadyInMatchException{
		Match match = setupMovementTests();
		testMoveRightPossible(match);
		testMoveRightImpossible(match);
	}
	
	public void testMoveRightPossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
	
		Player bob = match.getPlayerById("bob");
		
		Cell prevPosition = bob.getCurrentPosition();
		try {
			bob.moveRIGHT();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getColumn() +1 == bob.getCurrentPosition().getColumn());
		assertTrue(prevPosition.getLine() == bob.getCurrentPosition().getLine());
		
	}
	
	public void testMoveRightImpossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
		
		Player alice = match.getPlayerById("alice");
		
		Cell prevPosition = alice.getCurrentPosition();
		try {
			alice.moveRIGHT();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getColumn() == alice.getCurrentPosition().getColumn());	
		assertTrue(prevPosition.getLine() == alice.getCurrentPosition().getLine());
	}
	
	@Test
	public void testMoveLEFT() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException, PlayerAlreadyInMatchException{
		Match match = setupMovementTests();
		testMoveLeftPossible(match);
		testMoveLeftImpossible(match);
	}
	
	public void testMoveLeftPossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
	
		Player alice = match.getPlayerById("alice");
		
		Cell prevPosition = alice.getCurrentPosition();
		try {
			alice.moveLEFT();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getColumn()-1 == alice.getCurrentPosition().getColumn());
		assertTrue(prevPosition.getLine() == alice.getCurrentPosition().getLine());
		
	}
	
	public void testMoveLeftImpossible(Match match) throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException, MatchFullException, NotEnoughPlayersException, CantFindPlayerByIdException{
		
		Player abreu = match.getPlayerById("abreu");
		
		Cell prevPosition = abreu.getCurrentPosition();
		try {
			abreu.moveLEFT();
		} catch (NotInMatchException e) {
			fail();
		}
		assertTrue(prevPosition.getLine() == abreu.getCurrentPosition().getLine());
		assertTrue(prevPosition.getColumn() == abreu.getCurrentPosition().getColumn());
		
	}
}