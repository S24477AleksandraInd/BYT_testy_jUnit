package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	/**
	 * Check if name getter works correctly.
	 */
	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	/**
	 * Check if rate getter works correctly.
	 */
	@Test
	public void testGetRate() {
		assertEquals((Double) 0.15, SEK.getRate());
		assertEquals((Double) 0.20, DKK.getRate());
		assertEquals((Double) 1.5, EUR.getRate());
	}

	/**
	 * Check if rate setter works correctly.
	 */
	@Test
	public void testSetRate() {
		SEK.setRate(0.5);
		assertEquals((Double) 0.5, SEK.getRate());
	}

	/**
	 * Check if universalValue() works correctly (returns converted value).
	 */
	@Test
	public void testGlobalValue() {
		assertEquals((Integer) 1500, SEK.universalValue(10000));
		assertEquals((Integer) 2000, DKK.universalValue(10000));
		assertEquals((Integer) 15000, EUR.universalValue(10000));
	}

	/**
	 * Check if valueInThisCurrency() works correctly (double conversion of converted value).
	 */
	@Test
	public void testValueInThisCurrency() {
		assertEquals((Integer) 10000, SEK.valueInThisCurrency(1000, EUR));
	}
}
