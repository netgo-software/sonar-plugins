package de.tolina.sonar.plugins.vft.checks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class IsVisibleForTestingTest {

	private Predicate<AnnotationInstance> isVisibleForTesting;
	@Mock
	private AnnotationInstance annotatioInstance;
	@Mock
	private Symbol symbol;
	@Mock
	private Symbol owner;

	@Before
	public void initTestObject() {
		isVisibleForTesting = new IsVisibleForTesting();
	}

	@Before
	public void initMocks() {
		when(annotatioInstance.symbol()).thenReturn(symbol);
		when(symbol.owner()).thenReturn(owner);
	}

	@Test
	public void testTest() throws Exception {
		when(symbol.name()).thenReturn("other.package");
		when(owner.name()).thenReturn("OtherClass");
		assertFalse(isVisibleForTesting.test(annotatioInstance));

		when(symbol.name()).thenReturn(IsVisibleForTesting.ANNOTAION_CLASS);
		when(owner.name()).thenReturn(IsVisibleForTesting.ANNOTAION_PACKAGE);
		assertTrue(isVisibleForTesting.test(annotatioInstance));
	}

}
