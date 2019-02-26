/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.dice.result;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DieRollTest {
	private static final Random random = new SecureRandom();

	private static final EnumSet<DieRoll.DieRollFlags> SUCCESS = EnumSet.of(DieRoll.DieRollFlags.SUCCESS);
	private static final EnumSet<DieRoll.DieRollFlags> FAILURE = EnumSet.of(DieRoll.DieRollFlags.FAILURE);
	private static final EnumSet<DieRoll.DieRollFlags> FUMBLE = EnumSet.of(DieRoll.DieRollFlags.FUMBLE);
	private static final EnumSet<DieRoll.DieRollFlags> CRITICAL = EnumSet.of(DieRoll.DieRollFlags.CRITICAL);

	private static final EnumSet<DieRoll.DieRollFlags> ALL = EnumSet.copyOf(Arrays.asList(DieRoll.DieRollFlags.values()));

	/**
	 * Helper function to create a {@link DieRoll} with a successful flag.
	 *
	 * @param val
	 *            The result of the dice roll.
	 * @return a {@link DieRoll} with a successful flag.
	 */
	private static DieRoll successFlagRoll(int val) {
		return new DieRoll(val, SUCCESS);
	}

	/**
	 * Helper function to create a {@link DieRoll} with a failure flag.
	 *
	 * @param val
	 *            The result of the dice roll.
	 * @return a {@link DieRoll} with a failure flag.
	 */
	private static DieRoll failureFlagRoll(int val) {
		return new DieRoll(val, FAILURE);
	}

	/**
	 * Helper function to create a {@link DieRoll} with a fumble flag.
	 *
	 * @param val
	 *            The result of the dice roll.
	 * @return a {@link DieRoll} with a fumble flag.
	 */
	private static DieRoll fumbleFlagRoll(int val) {
		return new DieRoll(val, FUMBLE);
	}

	/**
	 * Helper function to create a {@link DieRoll} with a critical flag.
	 *
	 * @param val
	 *            The result of the dice roll.
	 * @return a {@link DieRoll} with a critical flag.
	 */
	private static DieRoll criticalFlagRoll(int val) {
		return new DieRoll(val, CRITICAL);
	}

	/**
	 * Helper function to create a {@link DieRoll} with all flags set.
	 *
	 * @param val
	 *            The result of the dice roll.
	 * @return a {@link DieRoll} with all flags set.
	 */
	private static DieRoll allFlagsRoll(int val) {
		return new DieRoll(val, ALL);
	}

	@Test
	void getValue() {
		random.ints(100).forEach(r -> {
			DieRoll dr = new DieRoll(r);
			assertEquals(dr.getValue(), r);
		});
	}

	@Test
	void getFlags() {
		random.ints(100).forEach(r -> {
			DieRoll drSuccess = successFlagRoll(r);
			DieRoll drFailure = failureFlagRoll(r);
			DieRoll drFumble = fumbleFlagRoll(r);
			DieRoll drCritical = criticalFlagRoll(r);
			DieRoll drAll = allFlagsRoll(r);

			// Success
			assertTrue(drSuccess.getFlags().contains(DieRoll.DieRollFlags.SUCCESS));
			assertFalse(drSuccess.getFlags().contains(DieRoll.DieRollFlags.FUMBLE));
			assertFalse(drSuccess.getFlags().contains(DieRoll.DieRollFlags.FAILURE));
			assertFalse(drSuccess.getFlags().contains(DieRoll.DieRollFlags.CRITICAL));
			// Failure
			assertFalse(drFailure.getFlags().contains(DieRoll.DieRollFlags.SUCCESS));
			assertFalse(drFailure.getFlags().contains(DieRoll.DieRollFlags.FUMBLE));
			assertTrue(drFailure.getFlags().contains(DieRoll.DieRollFlags.FAILURE));
			assertFalse(drFailure.getFlags().contains(DieRoll.DieRollFlags.CRITICAL));
			// Fumble
			assertFalse(drFumble.getFlags().contains(DieRoll.DieRollFlags.SUCCESS));
			assertTrue(drFumble.getFlags().contains(DieRoll.DieRollFlags.FUMBLE));
			assertFalse(drFumble.getFlags().contains(DieRoll.DieRollFlags.FAILURE));
			assertFalse(drFumble.getFlags().contains(DieRoll.DieRollFlags.CRITICAL));
			// Critical
			assertFalse(drCritical.getFlags().contains(DieRoll.DieRollFlags.SUCCESS));
			assertFalse(drCritical.getFlags().contains(DieRoll.DieRollFlags.FUMBLE));
			assertFalse(drCritical.getFlags().contains(DieRoll.DieRollFlags.FAILURE));
			assertTrue(drCritical.getFlags().contains(DieRoll.DieRollFlags.CRITICAL));
			// All
			assertTrue(drAll.getFlags().containsAll(Arrays.asList(DieRoll.DieRollFlags.values())));
		});
	}

	@Test
	void isSuccess() {
		random.ints(100).forEach(r -> {
			DieRoll dr = new DieRoll(r);
			assertFalse(dr.isSuccess());

			DieRoll dr2 = successFlagRoll(r);
			assertTrue(dr2.isSuccess());
			assertFalse(dr2.isCritical());
			assertFalse(dr2.isFailure());
			assertFalse(dr2.isFumble());
			assertTrue(allFlagsRoll(r).isSuccess());
		});
	}

	@Test
	void isFailure() {
		random.ints(100).forEach(r -> {
			DieRoll dr = new DieRoll(r);
			assertFalse(dr.isFailure());

			DieRoll dr2 = failureFlagRoll(r);
			assertFalse(dr2.isSuccess());
			assertFalse(dr2.isCritical());
			assertTrue(dr2.isFailure());
			assertFalse(dr2.isFumble());
			assertTrue(allFlagsRoll(r).isSuccess());
		});
	}

	@Test
	void isCritical() {
		random.ints(100).forEach(r -> {
			DieRoll dr = new DieRoll(r);
			assertFalse(dr.isCritical());

			DieRoll dr2 = criticalFlagRoll(r);
			assertFalse(dr2.isSuccess());
			assertTrue(dr2.isCritical());
			assertFalse(dr2.isFailure());
			assertFalse(dr2.isFumble());
			assertTrue(allFlagsRoll(r).isSuccess());
		});
	}

	@Test
	void isFumble() {
		random.ints(100).forEach(r -> {
			DieRoll dr = new DieRoll(r);
			assertFalse(dr.isFumble());

			DieRoll dr2 = fumbleFlagRoll(r);
			assertFalse(dr2.isSuccess());
			assertFalse(dr2.isCritical());
			assertFalse(dr2.isFailure());
			assertTrue(dr2.isFumble());
			assertTrue(allFlagsRoll(r).isSuccess());
		});
	}
}