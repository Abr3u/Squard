package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Match;
import domain.MatchState;
import domain.Player;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.InvalidPlayerIdException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;

public class TestMatch {

	private Integer MAX_PLAYERS = 4;
	private Integer count;
	
	@Before
	public void setup(){
		count = 0;
	}
	
	@Test
	public void testConstructorSuccessNormal() {
		Match myMatch = null;
		try {
			myMatch = new Match(count++,4);
		} catch (InvalidMaxPlayersForMatchException e) {
			fail();
		}

		assertTrue(myMatch.getState().equals(MatchState.CREATED));
		assertTrue(myMatch.getMaxPlayers() == 4);
		assertTrue(myMatch.getPlayers().size() == 0);
		assertTrue(myMatch.getBoard().getTotalLines() == 10);
		assertTrue(myMatch.getBoard().getTotalColumns() == 20);
	}

	@Test
	public void testConstructorInvalidMaxPlayers() {
		Match myMatch = null;
		try {
			myMatch = new Match(count++,0);
			fail();
		} catch (InvalidMaxPlayersForMatchException e) {
		}

		try {
			myMatch = new Match(count++,MAX_PLAYERS + 1);
			fail();
		} catch (InvalidMaxPlayersForMatchException e) {
		}
	}

	@Test
	public void testConstructorBoundariesMaxPlayers() {
		Match myMatch = null;
		try {
			myMatch = new Match(count++,1);
		} catch (InvalidMaxPlayersForMatchException e) {
			fail();
		}

		try {
			myMatch = new Match(count++,MAX_PLAYERS);
		} catch (InvalidMaxPlayersForMatchException e) {

			fail();
		}
	}

	@Test
	public void testAddPlayerSuccess() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException {
		Match myMatch = new Match(count++,2);
		try {
			myMatch.addPlayer(new Player("abreu"));
		} catch (MatchFullException e) {
			fail();
		}
		assertTrue(myMatch.getPlayers().size() == 1);
	}
	
	@Test
	public void testAddPlayerMoreThanMax() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException {
		Match myMatch = new Match(count++,1);
		try {
			myMatch.addPlayer(new Player("abreu"));
			myMatch.addPlayer(new Player("ricardo"));
			fail();
		} catch (MatchFullException e) {
		}
	}
	
	@Test
	public void testAddPlayerBoundaries() throws InvalidMaxPlayersForMatchException, InvalidPlayerIdException {
		Match myMatch = new Match(count++,1);
		try {
			myMatch.addPlayer(new Player("abreu"));
		} catch (MatchFullException e) {
			fail();
		}
		
		myMatch = new Match(count++,MAX_PLAYERS);
		try {
			myMatch.addPlayer(new Player("abreu"));
			myMatch.addPlayer(new Player("ricardo"));
			myMatch.addPlayer(new Player("alice"));
			myMatch.addPlayer(new Player("bob"));
		} catch (MatchFullException e) {
			fail();
		}
	}
	
	@Test
	public void testStartMatchSuccess() throws InvalidMaxPlayersForMatchException, MatchFullException, InvalidPlayerIdException {
		Match myMatch = new Match(count++,4);
		myMatch.addPlayer(new Player("abreu"));
		myMatch.addPlayer(new Player("ricardo"));
		myMatch.addPlayer(new Player("alice"));
		myMatch.addPlayer(new Player("bob"));
		try {
			myMatch.start();
		} catch (NotEnoughPlayersException e) {
			fail();
		}
		assertTrue(myMatch.getState().equals(MatchState.ONGOING));
		myMatch.printPlayersCurrentPositions();
		myMatch.printPlayersCurrentColors();
	}
	
	@Test
	public void testStartMatchNotEnoughPlayers() throws InvalidMaxPlayersForMatchException, MatchFullException, InvalidPlayerIdException {
		Match myMatch = new Match(count++,2);
		myMatch.addPlayer(new Player("abreu"));
		try {
			myMatch.start();
			fail();
		} catch (NotEnoughPlayersException e) {
		}
	}
	
}
