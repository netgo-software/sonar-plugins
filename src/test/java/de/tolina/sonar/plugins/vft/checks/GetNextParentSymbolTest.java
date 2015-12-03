package de.tolina.sonar.plugins.vft.checks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Symbol.TypeSymbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class GetNextParentSymbolTest {
	private Function<Tree, Symbol> getNextParentSymbol;
	@Mock
	private Tree tree;
	@Mock
	private Tree parentWithoutSymbol1;
	@Mock
	private Tree parentWithoutSymbol2;

	@Before
	public void initTestObject() {
		getNextParentSymbol = new GetNextParentSymbol();
	}

	@Before
	public void initMocks() {
		when(tree.parent()).thenReturn(parentWithoutSymbol1);
		when(parentWithoutSymbol1.parent()).thenReturn(parentWithoutSymbol2);
	}

	@Test
	public void testNullCheck() throws Exception {
		assertNull(getNextParentSymbol.apply(null));
	}

	@Test
	public void testNoParent() throws Exception {
		assertNull(getNextParentSymbol.apply(tree));
	}


	@Test
	public void testParentClassTree() throws Exception {
		ClassTree classTree = mock(ClassTree.class);
		when(parentWithoutSymbol2.parent()).thenReturn(classTree);
		TypeSymbol symbol = mock(TypeSymbol.class);
		when(classTree.symbol()).thenReturn(symbol);

		assertEquals(symbol, getNextParentSymbol.apply(tree));
	}

	@Test
	public void testParentVariableTree() throws Exception {
		VariableTree variableTree = mock(VariableTree.class);
		when(parentWithoutSymbol2.parent()).thenReturn(variableTree);
		Symbol symbol = mock(Symbol.class);
		when(variableTree.symbol()).thenReturn(symbol);

		assertEquals(symbol, getNextParentSymbol.apply(tree));
	}
}
