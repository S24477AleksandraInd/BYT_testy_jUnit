package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/**
	 * Check if amount getter works correctly.
	 */
	@Test
	public void testGetAmount() {
		assertEquals("100,00", (Integer) 10000, SEK100.getAmount());
		assertEquals("200,00", (Integer) 20000, SEK200.getAmount());
		assertEquals("0,00", (Integer) 0, SEK0.getAmount());
		assertEquals("-100,00", (Integer) (-10000), SEKn100.getAmount());
	}

	/**
	 * Check if currency getter works correctly.
	 */
	@Test
	public void testGetCurrency() {
		assertEquals("SEK", SEK100.getCurrency().getName());
		assertEquals("EUR", EUR10.getCurrency().getName());
		assertEquals("SEK", SEK200.getCurrency().getName());
		assertEquals("EUR", EUR20.getCurrency().getName());
		assertEquals("SEK", SEK0.getCurrency().getName());
		assertEquals("EUR", EUR0.getCurrency().getName());
		assertEquals("SEK", SEKn100.getCurrency().getName());
	}

	/**
	 * Check if toString() works correctly.
	 */
	@Test
	public void testToString() {
		assertEquals("100,00 SEK", SEK100.toString());
		assertEquals("200,00 SEK", SEK200.toString());
		assertEquals("0,00 SEK", SEK0.toString());
		assertEquals("-100,00 SEK", SEKn100.toString());
		assertEquals("10,00 EUR", EUR10.toString());
		assertEquals("20,00 EUR", EUR20.toString());
		assertEquals("0,00 EUR", EUR0.toString());
	}

	/**
	 * Check if universalValue() works correctly (returns converted value).
	 */
	@Test
	public void testGlobalValue() {
		assertEquals("100,00 SEK", (Integer) 1500, SEK100.universalValue());
		assertEquals("200,00 SEK", (Integer) 3000, SEK200.universalValue());
		assertEquals("0,00 SEK", (Integer) 0, SEK0.universalValue());
		assertEquals("-100,00 SEK", (Integer) (-1500), SEKn100.universalValue());
		assertEquals("10,00 EUR", (Integer) 1500, EUR10.universalValue());
		assertEquals("20,00 EUR", (Integer) 3000, EUR20.universalValue());
		assertEquals("0,00 EUR", (Integer) 0, EUR0.universalValue());
	}

	/**
	 * Check if equals() works correctly (compares converted values between Money instances).
	 */
	@Test
	public void testEqualsMoney() {
		assertEquals("SEK0 == EUR0", true, SEK0.equals(EUR0)); // both are 0
		assertEquals("SEK100 == EUR10", true, SEK100.equals(EUR10)); // universal value of both is 1500
		assertNotEquals("SEK100 != EUR20", true, SEK100.equals(EUR20)); // universal value of SEK100 is 1500, but EUR20 is 3000
	}

	/**
	 * Check if add() works correctly (adds two Money instances).
	 */
	@Test
	public void testAdd() {
		assertEquals("SEK100 + EUR10 = SEK200", true, SEK200.equals(SEK100.add(EUR10)));
		assertEquals("SEK100 + EUR10 = EUR20", true, EUR20.equals(EUR10.add(SEK100)));
	}

	/**
	 * Check if sub() works correctly (subtracts two Money instances).
	 */
	@Test
	public void testSub() {
		assertEquals("SEK100 - EUR10 = SEK0", true, SEK0.equals(SEK100.sub(EUR10)));
		assertEquals("SEK100 - EUR10 = EUR0", true, EUR0.equals(EUR10.sub(SEK100)));
		assertEquals("SEK200 - EUR10 = SEK100", true, SEK100.equals(SEK200.sub(EUR10)));
		assertEquals("EUR20 - SEK100 = EUR10", true, EUR10.equals(EUR20.sub(SEK100)));
	}

	/**
	 * Check if isZero() works correctly (checks if Money instance is 0).
	 */
	@Test
	public void testIsZero() {
		assertEquals("SEK0 is zero", true, SEK0.isZero());
		assertEquals("EUR0 is zero", true, EUR0.isZero());
		assertEquals("SEK100 is not zero", false, SEK100.isZero());
		assertEquals("EUR10 is not zero", false, EUR10.isZero());
		assertEquals("SEK200 is not zero", false, SEK200.isZero());
		assertEquals("EUR20 is not zero", false, EUR20.isZero());
		assertEquals("SEKn100 is not zero", false, SEKn100.isZero());
	}

	/**
	 * Check if negate() works correctly (negates Money instance).
	 */
	@Test
	public void testNegate() {
		assertEquals("SEK100 negated is SEKn100", true, SEKn100.equals(SEK100.negate()));
		assertEquals("SEK0 negated is SEK0", true, SEK0.equals(SEK0.negate()));
	}

	/**
	 * Check if compare() works correctly (compares two Money instances). Also test error handling.
	 */
	@Test
	public void testCompareTo() {
		assertEquals("SEK100 < EUR10", 0, SEK100.compareTo(EUR10));
		assertEquals("SEK200 > EUR10", 1, SEK200.compareTo(EUR10));
		assertEquals("SEK100 < EUR20", -1, SEK100.compareTo(EUR20));

		// test for other objects
		try {
			SEK100.compareTo(null);
			fail("Should throw NullPointerException");
		} catch (ClassCastException e) {
			// expected
		}
	}
}
