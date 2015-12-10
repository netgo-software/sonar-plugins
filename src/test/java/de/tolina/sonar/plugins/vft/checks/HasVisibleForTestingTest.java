package de.tolina.sonar.plugins.vft.checks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class HasVisibleForTestingTest {

	private Predicate<Symbol> hasVisibleForTestingPredicate;

	@Mock
	private Predicate<AnnotationInstance> isVisibleForTestingAnnotation;
	@Mock
	private Symbol symbol;
	@Mock
	private SymbolMetadata metaData;

	@Before
	public void initTestObject() {
		hasVisibleForTestingPredicate = new HasVisibleForTesting(isVisibleForTestingAnnotation);
	}

	@Before
	public void initMock() {
		when(symbol.metadata()).thenReturn(metaData);
	}

	@Test
	public void testNoAnnotationsAtSymbol() throws Exception {
		assertFalse(hasVisibleForTestingPredicate.test(symbol));
	}

	@Test
	public void testOtherAnnotationsAtSymbol() throws Exception {
		final AnnotationInstance annotation1 = mock(AnnotationInstance.class);
		final AnnotationInstance annotation2 = mock(AnnotationInstance.class);
		final AnnotationInstance annotation3 = mock(AnnotationInstance.class);
		final List<AnnotationInstance> annotations = Arrays.asList(annotation1, annotation2, annotation3);
		when(metaData.annotations()).thenReturn(annotations);

		when(Boolean.valueOf(isVisibleForTestingAnnotation.test(annotation1))).thenReturn(Boolean.FALSE);
		when(Boolean.valueOf(isVisibleForTestingAnnotation.test(annotation2))).thenReturn(Boolean.FALSE);
		when(Boolean.valueOf(isVisibleForTestingAnnotation.test(annotation3))).thenReturn(Boolean.FALSE);
		assertFalse(hasVisibleForTestingPredicate.test(symbol));
	}

	@Test
	public void testVisibleForTestingAtSymbol() throws Exception {
		final AnnotationInstance annotation1 = mock(AnnotationInstance.class);
		final AnnotationInstance annotation2 = mock(AnnotationInstance.class);
		final AnnotationInstance annotation3 = mock(AnnotationInstance.class);
		final List<AnnotationInstance> annotations = Arrays.asList(annotation1, annotation2, annotation3);
		when(metaData.annotations()).thenReturn(annotations);

		when(Boolean.valueOf(isVisibleForTestingAnnotation.test(annotation1))).thenReturn(Boolean.FALSE);
		when(Boolean.valueOf(isVisibleForTestingAnnotation.test(annotation2))).thenReturn(Boolean.TRUE);
		when(Boolean.valueOf(isVisibleForTestingAnnotation.test(annotation3))).thenReturn(Boolean.FALSE);
		assertTrue(hasVisibleForTestingPredicate.test(symbol));
	}
}
