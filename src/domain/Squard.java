package domain;

import exceptions.InvalidPlayerIdException;

public class Squard {

	public static void main(String[] args) {

		MatchManager matchManager = new MatchManager();

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
			e2.printStackTrace();
		}

		Integer mID = matchManager.createNewMatch(4, BoardType.SMALL);
		matchManager.addPlayerToMatch(abreu, mID);
		matchManager.addPlayerToMatch(alice, mID);
		matchManager.addPlayerToMatch(bob, mID);
		matchManager.addPlayerToMatch(foo, mID);
		matchManager.startMatchById(mID);
		
		Match myMatch = matchManager.getMatchById(mID);
		myMatch.movePlayer(0, Direction.LEFT);
//		myMatch.movePlayer(0, Direction.LEFT);
		myMatch.printBoard();
		matchManager.endMatchById(mID);
		
	}

}
