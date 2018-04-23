/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxtesting.examples;

import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.PlayerModel;
import com.badlogic.gdx.Input;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.InputEvent;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test suite of the playerMpde, class
 * To run the tests:
 *  - in Jumazy root folder open your favourite terminal
 *  - run command ./gradlew tests:test
 */
public class TestPlayerModel {

    MazeModel maze;
    PlayerModel player;

    /**
     * Create a basic maze thats repeatable and testable. One room given in the test roomlayout,
     * contains a V, T, E, C for testing. Room layout is below:
     * OOOOOOOO
     * OOOOOOOO
     * OOOOOOOO
     * OOOOOOOO
     * OOOO#DD#
     * OOVOOOOO
     * TOOOOOOO
     * OCEOOOOO
     */
    @Before
    public void setUp() {
//        Create a list of character
        ArrayList<PlayerModel.CharacterName> chars = new ArrayList<PlayerModel.CharacterName>();
        chars.add(PlayerModel.CharacterName.RUBY_ROUNDHOUSE);
        chars.add(PlayerModel.CharacterName.SHELLY_OBERON);
        chars.add(PlayerModel.CharacterName.FRANKLIN_FINBAR);
        chars.add(PlayerModel.CharacterName.SMOLDER_BRAVESTONE);

        maze = new MazeModel(1, 1, chars.size(), chars);
        player = maze.getCurrentPlayer();
    }

    /**
     * Check that each player is in their expected position when the game is started
     * Use the character enum value in the order players were added to the game in setUp, and compare to maze-space
     */
    @Test
    public void playersInCorrectPositions() {

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
        assertEquals(true, player.canRoll());
        maze.getCurrentPlayer().rollDie(maze.getWeather());
        assertEquals(false, player.canRoll());
    }

    /**
     * Roll the dice, move onto a trap
     */
    @Test
    public void testOnTrap(){
        player.rollDie(maze.getWeather());
        player.move(Input.Keys.UP);
        assertEquals(true, player.isOnTrap());
    }

    /**
     * Roll the dice, move onto a chest
     */
    @Test
    public void testOnChest(){
        player.rollDie(maze.getWeather());
        player.move(Input.Keys.RIGHT);
        assertEquals(true, player.isOnChest());
    }

    /**
     * Roll the dice, move onto a chest
     */
    @Test
    public void testMoveBack(){
        int[] startPosition = player.getPosition();

        assertEquals("Check that X startPosition var and X startPosition for player is the same",
                startPosition[0], player.getPosition()[0]);
        assertEquals("Check that Y startPosition var and Y startPosition for player is the same",
                startPosition[1], player.getPosition()[1]);

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

        assertEquals("Check that player inventory is empty to begin with",
                0, player.getInventory().size());

        player.obtainRandomItemFromChest();

        assertEquals("Check that player obtained an item, and was added to inventory",
                1, player.getInventory().size());
    }

}
