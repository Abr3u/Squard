package domain;

import exceptions.InvalidBoardDimensionsException;
import exceptions.InvalidMaxPlayersForMatchException;
import exceptions.InvalidPlayerIdException;
import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;

public class Squard {

	public static void main(String[] args) {
		Player abreu = null;
		Player alice = null;
		Player bob = null;
		Player foo = null;
		try {
			abreu = new Player("abreu");
			alice = new Player("alice");
			bob = new Player("bob");
			foo = new Player("foo");
		} catch (InvalidPlayerIdException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		

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
		example.printPlayersCurrentColors();
	}

}
