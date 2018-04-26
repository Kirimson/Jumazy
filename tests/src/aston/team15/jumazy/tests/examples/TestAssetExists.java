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

package aston.team15.jumazy.tests.examples;

import static org.junit.Assert.assertTrue;

import aston.team15.jumazy.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;

@RunWith(GdxTestRunner.class)
public class TestAssetExists {

	@Test
	public void handImageExists() {
		assertTrue("Test that the hand image is in the filestructure, and that libGDX can find files", Gdx.files
				.internal("../core/assets/hand.png").exists());
	}

	@Test
	public void skinExists() {
		assertTrue("Test that libGDX can find our skin file for the current texturepack", Gdx.files
				.internal("../core/assets/jumazyskin/current/jumazy-skin.json").exists());

		assertTrue("Test that libGDX can find our skin file for the old english texturepack", Gdx.files
				.internal("../core/assets/jumazyskin/oldenglish/jumazy-skin.json").exists());

		assertTrue("Test that libGDX can find our skin file for the medievel texturepack", Gdx.files
				.internal("../core/assets/jumazyskin/medievel/jumazy-skin.json").exists());
	}

}
