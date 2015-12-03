/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.function.Function;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

/**
 * Searches in the {@link Tree} for the next tree element that has a {@link Symbol}.
 *  
 *
 */
final class GetAccessingSymbol implements Function<Tree, Symbol> {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Function<Tree, Symbol> getSymbol = new GetSymbol();


	@Override
	public Symbol apply(final Tree t) {
		final Tree parent = t.parent();
		if (parent == null) {
			//			throw new IllegalStateException("Class has no parent " + t.getClass() + ". Symbol not found");
			return null;
		}
		final Symbol symbol = getSymbol.apply(parent);
		if (symbol == null) {
			return apply(parent);
		}
		logger.debug("Found symbol: " + symbol.name() + " in " + parent.getClass());
		return symbol;
	}


	/**
	 * This class is a dirty hack. 
	 *
	 */
	static class GetSymbol implements Function<Tree, Symbol> {

		@Override
		@Nullable
		public Symbol apply(Tree t) {
			if (t instanceof ClassTree) {
				return ((ClassTree) t).symbol();
			}
			if (t instanceof IdentifierTree) {
				return ((IdentifierTree) t).symbol();
			}
			if (t instanceof MethodInvocationTree) {
				return ((MethodInvocationTree) t).symbol();
			}
			if (t instanceof MethodTree) {
				return ((MethodTree) t).symbol();
			}
			if (t instanceof NewClassTree) {
				return ((NewClassTree) t).constructorSymbol();
			}
			if (t instanceof VariableTree) {
				return ((VariableTree) t).symbol();
			}
			return null;
		}

	}
}
