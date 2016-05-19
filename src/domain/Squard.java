package domain;

import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;

public class Squard {

	public static void main(String[] args) {
		try {
			Board myBoard = new Board(3, 5);
		} catch (InvalidBoardDimensionsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Player abreu = new Player("abreu");
		Player alice = new Player("alice");
		Player bob = new Player("bob");
		Player foo = new Player("foo");

		Match example = null;
		try {
			example = new Match(4);
		} catch (InvalidMaxPlayersForMatchException e1) {
			e1.printStackTrace();
		}

		try {
			example.addPlayer(abreu);
			example.addPlayer(alice);
			example.addPlayer(bob);
			example.addPlayer(foo);
		} catch (MatchFullException e) {
			e.printStackTrace();
		}

		try {
			example.startMatch();
		} catch (NotEnoughPlayersException e) {
			e.printStackTrace();
		}

		example.printPlayersCurrentPositions();

	}

}
