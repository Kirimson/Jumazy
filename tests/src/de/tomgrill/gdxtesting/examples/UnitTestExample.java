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

import static org.junit.Assert.assertEquals;

import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.PlayerModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class UnitTestExample {

	MazeModel maze;

	@Before
	public void setUp(){
		ArrayList<PlayerModel.CharacterName> chars = new ArrayList<PlayerModel.CharacterName>();

		chars.add(PlayerModel.CharacterName.SMOLDER_BRAVESTONE);
		chars.add(PlayerModel.CharacterName.SHELLY_OBERON);
		maze = new MazeModel(1,1,2,chars);

	}

	@Test
	public void playerOneInCorrectPosition() {
		assertEquals(PlayerModel.CharacterName.SMOLDER_BRAVESTONE.getValue(), maze.getCoordinateString(1,1));
	}

}
