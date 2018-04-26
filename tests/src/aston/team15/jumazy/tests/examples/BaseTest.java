package aston.team15.jumazy.tests.examples;

import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.PlayerModel;
import org.junit.Before;

import java.util.ArrayList;

/**
 * Base test class with setup method to be shared with player and maze test
 *
 * @author Kirimson
 */
public class BaseTest {

    protected MazeModel maze;
    protected PlayerModel player;
    protected ArrayList<PlayerModel.CharacterName> chars;

    /**
     * Create a basic maze thats repeatable and testable. One room given in the test roomlayout,
     * contains a V, T, E, C for testing. Room layout is below:
     * OOOOOOOO
     * OOOOOOOO
     * OOOOOOOO
     * OOOOOOOO
     * OOOOOOOO
     * OOVOOOOO
     * TODDOOOO
     * OCEOOOOO
     */
    @Before
    public void setUp() {
//        Create a list of character
        chars = new ArrayList<PlayerModel.CharacterName>();
        chars.add(PlayerModel.CharacterName.RUBY_ROUNDHOUSE);
        chars.add(PlayerModel.CharacterName.SHELLY_OBERON);
        chars.add(PlayerModel.CharacterName.FRANKLIN_FINBAR);
        chars.add(PlayerModel.CharacterName.SMOLDER_BRAVESTONE);

        maze = new MazeModel(1, 1, chars.size(), chars);
        player = maze.getCurrentPlayer();
    }
}
