package sk.train;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArithmeticTest {

	@Test
	void testSum() {
		assertEquals(0.2, Arithmetic.sum(0.1, 0.1), () -> "Mein Error");
		assertTrue(Arithmetic.sum(0.1, 0.1) > 0.1);
	}

	@Test()
	void testFakultaet() {
		assertEquals(120, Arithmetic.fakultaet(5));
	}

}
