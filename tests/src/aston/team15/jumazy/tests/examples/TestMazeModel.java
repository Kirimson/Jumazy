package aston.team15.jumazy.tests.examples;

import com.badlogic.gdx.Input;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test suite of the mazeModel, class
 * To run the tests:
 *  - in Jumazy root folder open your favourite terminal
 *  - run command ./gradlew tests:test
 *  @author Kieran Gates
 */
public class TestMazeModel extends BaseTest{

    /**
     * Check that the maze dimensions match with what is expected from the test layout file
     */
    @Test
    public void testMazeSize(){
        System.out.println("Test: testMazeSize");

        assertEquals(10, maze.getMaze().length);
        assertEquals(10, maze.getMaze()[0].length);
    }

    /**
     * Test that the correct player symbols are in the expect positions
     */
    @Test
    public void testMazeSymbols(){
        System.out.println("Test: testMazeSymbols");

        assertEquals("Test that player 1 is in lower left corner",
                chars.get(0).getValue(), maze.getCoordinateString(1,1));
    }

    /**
     * Test that the maze switches the players turns correctly
     */
    @Test
    public void testPassPlayerTurn() {
        System.out.println("Test: testPassPlayerTurn");

        assertEquals("Check maze passes turn to player 2, index 1",
                1, maze.passTurnToNextPlayer());
        assertEquals("Check maze passes turn to player 3, index 2",
                2, maze.passTurnToNextPlayer());
        assertEquals("Check maze passes turn to player 4, index 3",
                3, maze.passTurnToNextPlayer());
        assertEquals("Check maze passes turn to player 1, index 0",
                0, maze.passTurnToNextPlayer());
    }

    /**
     * Test other half of door is unlocked when unlockDoor is invoked
     * First half of door is unlocked by player
     */
    @Test
    public void testUnlockDoor() {
        System.out.println("Test: testUnlockDoor");

        maze.unlockDoor(2,3);

        System.out.println(maze.toString());

        assertEquals("d", maze.getCoordinateString(2,4));
    }

    /**
     * Check a monster's coordinate is returned when findMonsterCoordinate is called when coordinates one
     * block away are supplied
     */
    @Test
    public void testFindMonsterCoordinate() {
        System.out.println("Test: testFindMonsterCoordinate");

        player.rollDie(maze.getWeather());
        player.move(Input.Keys.RIGHT);

//        Refer to roomlayout file to check coordinates match up. The room as a one big buffer of walls, each coordinate
//        needs +1 to it to get to correct in-game position
        int[] expectedCoordinates = {1,3};

        int[] actual = maze.removeMonster(player.getPosition());

        assertEquals(expectedCoordinates[0], actual[0]);
        assertEquals(expectedCoordinates[1], actual[1]);

    }

}
