package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	/**
	 * Error: AccountDoesNotExistException not thrown, when it should be.
	 *
	 * @throws AccountExistsException
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Alice");

		try {
			SweBank.openAccount("Alice");
			fail("AccountExistsException not thrown");
		} catch (AccountExistsException e) {
			// expected
		}

		try {
			SweBank.getBalance("Alice");
		} catch (AccountDoesNotExistException e) {
			fail("AccountDoesNotExistException thrown");
		}
	}

	/**
	 * Error: NullPointerException thrown.
	 *
	 * @throws AccountExistsException
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		assertEquals(10000, SweBank.getBalance("Ulrika"), 0);
	}

	/**
	 * Error: AccountDoesNotExistException thrown.
	 *
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.withdraw("Ulrika", new Money(5000, SEK));
		assertEquals(5000, SweBank.getBalance("Ulrika"), 0);
	}

	/**
	 * Error: AccountDoesNotExistException thrown.
	 *
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, SweBank.getBalance("Ulrika"), 0);
	}

	/**
	 * Error: NullPointerException thrown.
	 *
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		SweBank.transfer("Ulrika", "Bob", new Money(5000, SEK));

		assertEquals(5000, SweBank.getBalance("Ulrika"), 0);
		assertEquals(5000, SweBank.getBalance("Bob"), 0);
	}

	/**
	 * Error: NullPointerException thrown.
	 *
	 * @throws AccountDoesNotExistException
	 */
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		SweBank.addTimedPayment("Ulrika", "1", 1, 1, new Money(1000, SEK), Nordea, "Bob");

		SweBank.tick();
		assertEquals(9000, SweBank.getBalance("Ulrika"), 0);
		assertEquals(1000, Nordea.getBalance("Bob"), 0);

		SweBank.tick();
		assertEquals(8000, SweBank.getBalance("Ulrika"), 0);
		assertEquals(2000, Nordea.getBalance("Bob"), 0);

		SweBank.removeTimedPayment("Ulrika", "1");
		SweBank.tick();
		assertEquals(8000, SweBank.getBalance("Ulrika"), 0);
		assertEquals(2000, Nordea.getBalance("Bob"), 0);
	}
}
