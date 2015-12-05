package de.tolina.sonar.plugins.vft.checks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.Tree;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class IsAnnotationAtPackageVisibleSymbolTest {

	private Predicate<AnnotationTree> isAnnotationAtPackageVisibleTree;

	@Mock
	private Function<Tree, Symbol> getNextParentSymbol;
	@Mock
	private AnnotationTree annotationTree;
	@Mock
	private Symbol nextParentSymbol;

	@Before
	public void initTestObject() {
		isAnnotationAtPackageVisibleTree = new IsAnnotationAtPackageVisibleSymbol(getNextParentSymbol);
	}

	@Test
	public void testNoParent() {
		assertFalse(isAnnotationAtPackageVisibleTree.test(annotationTree));
	}

	@Test
	public void testParentSymbolIsPackageVisible() {
		when(getNextParentSymbol.apply(annotationTree)).thenReturn(nextParentSymbol);

		when(Boolean.valueOf(nextParentSymbol.isPackageVisibility())).thenReturn(Boolean.FALSE);
		assertFalse(isAnnotationAtPackageVisibleTree.test(annotationTree));

		when(Boolean.valueOf(nextParentSymbol.isPackageVisibility())).thenReturn(Boolean.TRUE);
		assertTrue(isAnnotationAtPackageVisibleTree.test(annotationTree));

	}

}
