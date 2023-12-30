package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    Currency SEK, DKK;
    Bank Nordea;
    Bank DanskeBank;
    Bank SweBank;
    Account testAccount;

    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        SweBank.openAccount("Alice");
        testAccount = new Account("Hans", SEK);
        testAccount.deposit(new Money(10000000, SEK));

        SweBank.deposit("Alice", new Money(1000000, SEK));
    }

    @Test
    public void testAddRemoveTimedPayment() {
        testAccount.addTimedPayment("1", 1, 0, new Money(1000000, SEK), SweBank, "Alice");
        testAccount.removeTimedPayment("1");
    }

    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        var hansBalanceBefore = testAccount.getBalance();

        testAccount.addTimedPayment("1", 1, 0, new Money(10000, SEK), SweBank, "Alice");
        testAccount.tick();

        var hansBalanceAfter = testAccount.getBalance();

        assertEquals(hansBalanceBefore.universalValue(), hansBalanceAfter.add(new Money(10000, SEK)).universalValue());
    }

    @Test
    public void testAddWithdraw() {
        testAccount.deposit(new Money(1000000, SEK));
        testAccount.withdraw(new Money(1000000, SEK));
    }

    @Test
    public void testGetBalance() {
        assertEquals(new Money(10000000, SEK).universalValue(), testAccount.getBalance().universalValue());
    }
}
