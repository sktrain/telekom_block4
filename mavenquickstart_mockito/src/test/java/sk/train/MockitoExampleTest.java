package sk.train;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isA;

import java.util.Iterator;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockitoExampleTest {

	@Mock
	Iterator<String> i;

	Comparable<String> c;

	// demonstrates the return of multiple values
	@Test
	void testMoreThanOneReturnValue() {
		when(i.next()).thenReturn("Mockito").thenReturn("rocks");
		String result = i.next() + " " + i.next();
		// assert
		assertEquals("Mockito rocks", result);
	}

	// this test demonstrates how to return values based on the input
	// and that @Mock can also be used for a method parameter
	@Test
	void testReturnValueDependentOnMethodParameter(@Mock Comparable<String> c) {
		when(c.compareTo("Mockito")).thenReturn(1);
		when(c.compareTo("Eclipse")).thenReturn(2);
		// assert
		assertEquals(1, c.compareTo("Mockito"));
		assertEquals(2, c.compareTo("Eclipse"));
	}

	// return a value based on the type of the provide parameter

	@Test
	void testReturnValueInDependentOnMethodParameter2(@Mock Comparable<Integer> c) {
		when(c.compareTo(isA(Integer.class))).thenReturn(0);
		// assert
		assertEquals(0, c.compareTo(Integer.valueOf(4)));
	}

	// demonstrates the configuration of a throws with Mockito
	// not a read test, just "testing" Mockito behavior
	@Test
	void testMockitoThrows() {
		Properties properties = Mockito.mock(Properties.class);

		when(properties.get(Mockito.anyString())).thenThrow(new IllegalArgumentException("Stuff"));

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> properties.get("A"));

		assertEquals("Stuff", exception.getMessage());
	}

}