package domain;

import exceptions.InvalidCellCoordinatesException;
import exceptions.InvalidPlayerIdException;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Squard extends Application {

	Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void updateScene(Scene s){
		this.primaryStage.setScene(s);
	}
	
	@Override
	public void start(Stage primaryStage) {
	
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
	}
	
	public void keyPressed(KeyEvent e) {
	    KeyCode keyCode = e.getCode();
	    System.out.println("key -> "+keyCode);
	} 
	
	public void legacyMain() {

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
		try {
			System.out.println("5;5 "+myMatch.getBoard().getCellByCoordinates(5, 5).getLine()+"#"+myMatch.getBoard().getCellByCoordinates(5, 5).getColumn());
		} catch (InvalidCellCoordinatesException e) {
			e.printStackTrace();
		}
		
		myMatch.movePlayer(0, Direction.LEFT);
		// myMatch.movePlayer(0, Direction.LEFT);
		myMatch.printBoard();
		matchManager.endMatchById(mID);

	}

}
