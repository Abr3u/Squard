package domain;

import exceptions.MatchFullException;
import exceptions.NotEnoughPlayersException;

public class Squard {

	public static void main(String[] args) {
		Board myBoard = new Board(3,5);
		
		Player abreu = new Player("abreu");
		Player alice = new Player("alice");
		Player bob = new Player("bob");
		Player foo = new Player("foo");
		
		Match example = new Match(4);
		
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
