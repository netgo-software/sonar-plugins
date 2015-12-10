/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.Test;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.semantic.Symbol.TypeSymbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;


@SuppressWarnings("javadoc")
public class GetSymbolTest {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	private final Function<Tree, Symbol> getSymbol = new GetSymbol();

	@Test
	public void testApply_OtherTree() throws Exception {
		final Tree tree = mock(Tree.class);
		assertNull(getSymbol.apply(tree));
	}

	@Test
	public void testApply_ClassTree() throws Exception {
		final ClassTree tree = mock(ClassTree.class);
		final TypeSymbol symbol = mock(TypeSymbol.class);
		when(tree.symbol()).thenReturn(symbol);
		assertSame(symbol, getSymbol.apply(tree));
	}

	@Test
	public void testApply_IdentifierTree() throws Exception {
		final IdentifierTree tree = mock(IdentifierTree.class);
		final Symbol symbol = mock(Symbol.class);
		when(tree.symbol()).thenReturn(symbol);
		assertSame(symbol, getSymbol.apply(tree));
	}

	@Test
	public void testApply_MethodInvocationTree() throws Exception {
		final MethodInvocationTree tree = mock(MethodInvocationTree.class);
		final Symbol symbol = mock(Symbol.class);
		when(tree.symbol()).thenReturn(symbol);
		assertSame(symbol, getSymbol.apply(tree));
	}

	@Test
	public void testApply_MethodTree() throws Exception {
		final MethodTree tree = mock(MethodTree.class);
		final MethodSymbol symbol = mock(MethodSymbol.class);
		when(tree.symbol()).thenReturn(symbol);
		assertSame(symbol, getSymbol.apply(tree));
	}

	@Test
	public void testApply_NewClassTree() throws Exception {
		final NewClassTree tree = mock(NewClassTree.class);
		final Symbol symbol = mock(Symbol.class);
		when(tree.constructorSymbol()).thenReturn(symbol);
		assertSame(symbol, getSymbol.apply(tree));
	}

	@Test
	public void testApply_VariableTree() throws Exception {
		final VariableTree tree = mock(VariableTree.class);
		final Symbol symbol = mock(Symbol.class);
		when(tree.symbol()).thenReturn(symbol);
		assertSame(symbol, getSymbol.apply(tree));
	}
}
