/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.dice.result;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * This class represents the result of an expression in the dice rolling scripting language..
 */
public final class DiceExprResult {

	/** The type of the result. */
	private final DiceResultType type;

	/** The result as an integer (if valid). */
	private final int intResult;

	/** The result as an number (if valid). */
	private final double doubleResult;

	/** The result as an string. */
	private final String stringResult;

	/** Can this result be numeric. */
	private final boolean hasNumericRepresentation;

	/** The dice rolls if any that go to make up this result. */
	private final DiceRolls diceRolls;

	/**
	 * Create a DiceExpressionResult of type undefined.
	 */
	private DiceExprResult() {
		type = DiceResultType.UNDEFINED;
		intResult = 0;
		doubleResult = 0.0;
		hasNumericRepresentation = false;
		stringResult = null;
		diceRolls = DiceRolls.NO_ROLLS;
	}

	/**
	 * Creates an Integer result.
	 *
	 * @param result
	 *            the integer value of the result.
	 * @param rolls
	 *            the rolls that go to make up
	 */
	public DiceExprResult(int result, DiceRolls rolls) {
		type = DiceResultType.INTEGER;
		intResult = result;
		doubleResult = result;
		stringResult = Integer.toString(result);
		hasNumericRepresentation = true;

		diceRolls = rolls;
	}

	/**
	 * Creates an Integer result.
	 *
	 * @param result
	 *            the integer value of the result.
	 */
	public DiceExprResult(int result) {
		this(result, DiceRolls.NO_ROLLS);
	}

	/**
	 * Creates a Double result.
	 *
	 * @param result
	 *            the double representation of the result.
	 * @param rolls
	 *            the rolls that go to make up
	 */
	private DiceExprResult(double result, DiceRolls rolls) {
		type = DiceResultType.DOUBLE;
		intResult = (int) result;
		doubleResult = result;
		stringResult = Double.toString(result);
		hasNumericRepresentation = true;

		diceRolls = rolls;
	}

	/**
	 * Creates a Double result.
	 *
	 * @param result
	 *            the double representation of the result.
	 */
	private DiceExprResult(double result) {
		this(result, DiceRolls.NO_ROLLS);
	}

	/**
	 * Creates a String result.
	 *
	 * @param result
	 *            the String representation of the result.
	 * @param rolls
	 *            the rolls that go to make up
	 */
	private DiceExprResult(String result, DiceRolls rolls) {
		type = DiceResultType.STRING;
		stringResult = result;
		double dres = 0.0;
		boolean numeric;
		try {
			dres = Double.parseDouble(result);
			numeric = true;
		} catch (NumberFormatException nfe) {
			numeric = false;
		}

		hasNumericRepresentation = numeric;
		doubleResult = dres;
		intResult = (int) dres;

		diceRolls = rolls;
	}

	/**
	 * Creates a String result.
	 *
	 * @param result
	 *            the String representation of the result.
	 */
	private DiceExprResult(String result) {
		this(result, DiceRolls.NO_ROLLS);
	}

	/** Undefined <code>DiceExprResult</code>. */
	@SuppressWarnings("WeakerAccess") public static DiceExprResult UNDEFINED = new DiceExprResult();

	/**
	 * Gets the type of the result.
	 *
	 * @return the type of the result.
	 */
	public DiceResultType getType() {
		return type;
	}

	/**
	 * Gets the integer representation of the result.
	 *
	 * @return the integer representation of the result.
	 */
	public OptionalInt getIntResult() {
		return hasNumericRepresentation ? OptionalInt.of(intResult) : OptionalInt.empty();
	}

	/**
	 * Gets the double representation of the result.
	 *
	 * @return the double representation of the result.
	 */
	@SuppressWarnings("WeakerAccess")
	public OptionalDouble getDoubleResult() {
		return hasNumericRepresentation ? OptionalDouble.of(doubleResult) : OptionalDouble.empty();
	}

	/**
	 * Gets the string representation of the result.
	 *
	 * @return the string representation of the result.
	 */
	public String getStringResult() {
		return stringResult;
	}

	/**
	 * Returns the {@link DieRoll}'s that make up the value.
	 *
	 * @return the {@link DieRoll}'s that make up the value.
	 */
	public DiceRolls getDiceRolls() {
		return diceRolls;
	}

	/**
	 * Returns if there are any rolls that make up this result.
	 *
	 * @return <code>true</code> if the result is made of of rolls, otherwise <code>false</code>.
	 */
	@SuppressWarnings("WeakerAccess")
	public boolean hasRolls() {
		return diceRolls.getNumberOfRolls() > 0;
	}

	/**
	 * Returns if the value is a number.
	 *
	 * @return <code>true</code> if the result is number, <code>false</code> otherwise.
	 */
	@SuppressWarnings("WeakerAccess")
	public boolean isNumber() {
		return hasNumericRepresentation;
	}

	/**
	 * Gets a <code>DiceExprResult</code> that is for an integer value.
	 *
	 * @param res
	 *            the integer value of the result.
	 * @return the <code>DiceExprResult</code> representing the integer value.
	 */
	public static DiceExprResult getIntResult(int res) {
		return new DiceExprResult(res);
	}

	/**
	 * Gets a <code>DiceExprResult</code> that is for a double value.
	 *
	 * @param res
	 *            the double value of the result.
	 * @return the <code>DiceExprResult</code> representing the double value.
	 */
	public static DiceExprResult getDoubleResult(double res) {
		return new DiceExprResult(res);
	}

	/**
	 * Gets a <code>DiceExprResult</code> that is for a String value.
	 *
	 * @param res
	 *            the String value of the result.
	 * @return the <code>DiceExprResult</code> representing the String value.
	 */
	public static DiceExprResult getStringResult(String res) {
		return new DiceExprResult(res);
	}

	/**
	 * Add two results together coercing types as needed.
	 *
	 * @param left
	 *            the result to the left of the addition operand.
	 * @param right
	 *            the result to the right of the addition operand.
	 * @return the result of the addition.
	 * @throws IllegalArgumentException
	 *             when either <code>left</code> or <code>right</code> is {@link DiceResultType#UNDEFINED}
	 */
	public static DiceExprResult add(DiceExprResult left, DiceExprResult right) throws IllegalArgumentException {
		if (left.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Left hand side of addition is undefined.");
		}
		if (right.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Right hand side of addition is undefined.");
		}

		if (left.getType() == DiceResultType.STRING || right.getType() == DiceResultType.STRING) {
			return getStringResult(left.getStringResult() + right.getStringResult());
		} else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
			left.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of addition is undefined."));
			double leftVal = left.getDoubleResult().getAsDouble();
			right.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of addition is undefined."));
			double rightVal = right.getDoubleResult().getAsDouble();

			return getDoubleResult(leftVal + rightVal);
		} else {
			left.getIntResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of addition is undefined."));
			int leftVal = left.getIntResult().getAsInt();
			right.getIntResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of addition is undefined."));
			int rightVal = right.getIntResult().getAsInt();

			return getIntResult(leftVal + rightVal);
		}
	}

	/**
	 * Subtract one result from another coercing types as needed.
	 *
	 * @param left
	 *            the result to the left of the subtraction operand.
	 * @param right
	 *            the result to the right of the subtraction operand.
	 * @return the result of the subtraction.
	 * @throws IllegalArgumentException
	 *             when either <code>left</code> or <code>right</code> is {@link DiceResultType#UNDEFINED} or mixing numbers and strings.
	 */
	public static DiceExprResult subtract(DiceExprResult left, DiceExprResult right) throws IllegalArgumentException {
		if (left.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Left hand side of subtraction is undefined.");
		}
		if (right.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Right hand side of subtraction is undefined.");
		}

		if (left.getType() == DiceResultType.STRING && right.getType() == DiceResultType.STRING) {
			return getStringResult(left.getStringResult().replace(right.getStringResult(), ""));
		} else if (left.getType() == DiceResultType.STRING) {
			throw new IllegalArgumentException("Can not subtract a number from a string");
		} else if (right.getType() == DiceResultType.STRING) {
			throw new IllegalArgumentException("Can not subtract a string from a number");
		} else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
			left.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of subtraction is undefined."));
			double leftVal = left.getDoubleResult().getAsDouble();
			right.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of subtraction is undefined."));
			double rightVal = right.getDoubleResult().getAsDouble();

			return getDoubleResult(leftVal - rightVal);
		} else {
			left.getIntResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of subtraction is undefined."));
			int leftVal = left.getIntResult().getAsInt();
			right.getIntResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of subtraction is undefined."));
			int rightVal = right.getIntResult().getAsInt();

			return getIntResult(leftVal - rightVal);
		}
	}

	/**
	 * Multiply two results together coercing types as needed.
	 *
	 * @param left
	 *            the result to the left of the multiplication operand.
	 * @param right
	 *            the result to the right of the multiplication operand.
	 * @return the result of the multiplication.
	 * @throws IllegalArgumentException
	 *             when either <code>left</code> or <code>right</code> is {@link DiceResultType#UNDEFINED} or both arguments are strings.
	 */
	public static DiceExprResult multiply(DiceExprResult left, DiceExprResult right) throws IllegalArgumentException {
		if (left.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Left hand side of multiplication is undefined.");
		}
		if (right.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Right hand side of multiplication is undefined.");
		}

		if (left.getType() == DiceResultType.STRING && right.getType() == DiceResultType.STRING) {
			throw new IllegalArgumentException("You can not multiply two strings.");
		} else if (left.getType() == DiceResultType.STRING || right.getType() == DiceResultType.STRING) {
			int times;
			String str;

			if (left.getType() == DiceResultType.STRING) {
				right.getIntResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of multiplication is undefined."));
				times = right.getIntResult().getAsInt();
				str = left.getStringResult();
			} else {
				left.getIntResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of multiplication is undefined."));
				times = left.getIntResult().getAsInt();
				str = right.getStringResult();
			}
			StringBuilder sb = new StringBuilder();
			sb.ensureCapacity(times * str.length());
			for (int i = 0; i < times; i++) {
				sb.append(str);
			}
			return getStringResult(sb.toString());
		} else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
			left.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of multiplication is undefined."));
			double leftVal = left.getDoubleResult().getAsDouble();
			right.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of multiplication is undefined."));
			double rightVal = right.getDoubleResult().getAsDouble();

			return getDoubleResult(leftVal * rightVal);
		} else {
			left.getIntResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of multiplication is undefined."));
			int leftVal = left.getIntResult().getAsInt();
			right.getIntResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of multiplication is undefined."));
			int rightVal = right.getIntResult().getAsInt();

			return getIntResult(leftVal * rightVal);
		}
	}

	/**
	 * Divide one result from another coercing types as needed.
	 *
	 * @param left
	 *            the result to the left of the division operand.
	 * @param right
	 *            the result to the right of the division operand.
	 * @return the result of the division.
	 * @throws IllegalArgumentException
	 *             when either <code>left</code> or <code>right</code> is {@link DiceResultType#UNDEFINED} or a String.
	 */
	public static DiceExprResult divide(DiceExprResult left, DiceExprResult right) throws IllegalArgumentException {
		if (left.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Left hand side of division is undefined.");
		}
		if (right.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Right hand side of division is undefined.");
		}

		if (left.getType() == DiceResultType.STRING || right.getType() == DiceResultType.STRING) {
			throw new IllegalArgumentException("Strings can not take part in division.");
		} else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
			left.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of division is undefined."));
			double leftVal = left.getDoubleResult().getAsDouble();
			right.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of division is undefined."));
			double rightVal = right.getDoubleResult().getAsDouble();

			return getDoubleResult(leftVal / rightVal);
		} else {
			left.getIntResult().orElseThrow(() -> new IllegalArgumentException("Left hand side of division is undefined."));
			int leftVal = left.getIntResult().getAsInt();
			right.getIntResult().orElseThrow(() -> new IllegalArgumentException("Right hand side of division is undefined."));
			int rightVal = right.getIntResult().getAsInt();

			return getIntResult(leftVal / rightVal);
		}
	}

	/**
	 * Negates the passed in value.
	 *
	 * @param value
	 *            the value to negate.
	 *
	 * @return the negative value of the argument.
	 * @throws IllegalArgumentException
	 *             when <code>value</code> is undefined or a string.
	 */
	public static DiceExprResult negate(DiceExprResult value) throws IllegalArgumentException {
		if (value.getType() == DiceResultType.UNDEFINED) {
			throw new IllegalArgumentException("Can't negate undefined.");
		}

		if (value.getType() == DiceResultType.STRING) {
			throw new IllegalArgumentException("Strings can not be negated.");
		} else if (value.getType() == DiceResultType.DOUBLE) {
			value.getDoubleResult().orElseThrow(() -> new IllegalArgumentException("Can't negate undefined."));
			double val = value.getDoubleResult().getAsDouble();

			return getDoubleResult(-val);
		} else {
			value.getIntResult().orElseThrow(() -> new IllegalArgumentException("Can't negate undefined."));
			int val = value.getIntResult().getAsInt();

			return getIntResult(-val);
		}
	}
}
