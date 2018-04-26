package aston.team15.jumazy.tests.examples;

import aston.team15.jumazy.model.PlayerModel;
import com.badlogic.gdx.Input;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test suite of the playerModel, class
 * To run the tests:
 *  - in Jumazy root folder open your favourite terminal
 *  - run command ./gradlew tests:test
 *  @author Kieran Gates
 */
public class TestPlayerModel extends BaseTest {

    /**
     * Check that each player is in their expected position when the game is started
     * Use the character enum value in the order players were added to the game in setUp, and compare to maze-space
     */
    @Test
    public void playersInCorrectPositions() {
        System.out.println("Test: playersInCorrectPositions");
        int wallBuffer = 1;
//      Subtracting 1 as well as wall buffer when in a corner as array.length gives you the length, when we want index, where 0 counts
        assertEquals(PlayerModel.CharacterName.RUBY_ROUNDHOUSE.getValue(),
                maze.getCoordinateString(wallBuffer, wallBuffer));

        assertEquals(PlayerModel.CharacterName.SHELLY_OBERON.getValue(),
                maze.getCoordinateString(1, (maze.getMaze()[0].length - 1) - wallBuffer));

        assertEquals(PlayerModel.CharacterName.FRANKLIN_FINBAR.getValue(),
                maze.getCoordinateString((maze.getMaze().length - 1) - wallBuffer, 1));

        assertEquals(PlayerModel.CharacterName.SMOLDER_BRAVESTONE.getValue(),
                maze.getCoordinateString((maze.getMaze().length - 1) - wallBuffer,
                        (maze.getMaze()[0].length - 1) - wallBuffer));
    }

    /**
     * Make player one roll the dice, the try and roll the dice again. First roll should success. Second should fail
     */
    @Test
    public void testCanRoll() {
        System.out.println("Test: testCanRoll");

        assertEquals(true, player.canRoll());
        maze.getCurrentPlayer().rollDie(maze.getWeather());
        assertEquals(false, player.canRoll());
    }

    /**
     * Roll the dice, move onto a trap
     */
    @Test
    public void testOnTrap(){
        System.out.println("Test: testOnTrap");

        player.rollDie(maze.getWeather());
        player.move(Input.Keys.UP);
        assertEquals(true, player.isOnTrap());
    }

    /**
     * Roll the dice, move onto a chest
     */
    @Test
    public void testOnChest(){
        System.out.println("Test: testOnChest");

        player.rollDie(maze.getWeather());
        player.move(Input.Keys.RIGHT);
        assertEquals(true, player.isOnChest());
    }

    /**
     * Roll the dice, move onto a door
     */
    @Test
    public void testOnDoor(){
        System.out.println("Test: testOnDoor");

        player.rollDie(maze.getWeather());
        player.move(Input.Keys.RIGHT);
        player.move(Input.Keys.UP);
        player.move(Input.Keys.RIGHT);

//        If player was lucky with intelligence and picked door, expect them to be on it, otherwise they wont be
        if(player.pickedDoor()){
            assertEquals(true, player.isOnDoor());
        } else {
            assertEquals(false, player.isOnDoor());
        }
    }

    /**
     * Roll the dice, move onto a chest
     */
    @Test
    public void testOnVictory(){
        System.out.println("Test: testOnVictory");

        player.rollDie(maze.getWeather());
        player.move(Input.Keys.RIGHT);
        player.move(Input.Keys.UP);
        player.move(Input.Keys.UP);
        player.move(Input.Keys.RIGHT);
        assertEquals(true, player.isOnVictorySquare());
    }

    /**
     * Roll the dice, move around and check the players roll's left is going down
     */
    @Test
    public void testMove(){
        System.out.println("Test: testMove");

        player.rollDie(maze.getWeather());

        int moves = player.getMovesLeft();

        player.move(Input.Keys.RIGHT);
        assertEquals(moves-1, player.getMovesLeft());
        player.move(Input.Keys.UP);
        assertEquals(moves-2, player.getMovesLeft());
    }

    /**
     * Roll the dice, move around and send player back to beginning of their turn
     */
    @Test
    public void testMoveBack(){
        System.out.println("Test: testMoveBack");

        int[] startPosition = player.getPosition();

        assertEquals("Check that X startPosition var and X startPosition for player is the same",
                startPosition[0], player.getPosition()[0]);
        assertEquals("Check that Y startPosition var and Y startPosition for player is the same",
                startPosition[1], player.getPosition()[1]);

//        Roll for the players movement and move right and up
        player.rollDie(maze.getWeather());
        player.move(Input.Keys.RIGHT);
        player.move(Input.Keys.UP);

        assertFalse("Check that player is not in start position",
                startPosition[0] == player.getPosition()[0] && startPosition[1] == player.getPosition()[1]);
        player.moveToStartOfTurn();
        assertTrue("Check that player is in start position",
                startPosition[0] == player.getPosition()[0] && startPosition[1] == player.getPosition()[1]);
    }

    @Test
    public void testItemObtain(){
        System.out.println("Test: testItemObtain");

        assertEquals("Check that player inventory is empty to begin with",
                0, player.getInventory().size());
//        Obtain an item
        player.obtainRandomItemFromChest();
        assertEquals("Check that player obtained an item, and was added to inventory",
                1, player.getInventory().size());
    }

    /**
     * Check that the players inventory can get maxed out of held items
     * IE, no more than 7 held types can be held at once
     */
    @Test
    public void testFullInventory(){
        System.out.println("Test: testFullInventory");

        for(int i = 0; i < 200; i++){
//            When returning false, a held item was rejected due to a full inventory
            if(!player.obtainRandomItemFromChest()){
                return;
            }
        }
        fail("Player inventory never rejected an item in 200 passes");
    }
}
