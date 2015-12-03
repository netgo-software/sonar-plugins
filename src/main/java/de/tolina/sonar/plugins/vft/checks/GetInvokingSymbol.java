/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;
import org.sonar.plugins.java.api.tree.StaticInitializerTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

final class GetInvokingSymbol implements Function<MethodInvocationTree, Symbol> {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@Override
	public Symbol apply(final MethodInvocationTree t) {
		final Tree parentTree = t.parent();
		if (parentTree instanceof ExpressionStatementTree) {
			return getSymbolFromMethodCall((ExpressionStatementTree) parentTree);
		}

		if (parentTree instanceof VariableTree) {
			return getSymbolFromClassCall((VariableTree) parentTree);
		}

		if (parentTree instanceof ReturnStatementTree) {
			return getSymbolFromReturnCall((ReturnStatementTree) parentTree);
		}


		throw new IllegalStateException("Aufrufkomnstrukt gefunden" + parentTree.getClass());
	}


	Tree findRecursiveNextTreeWithSymbol(Tree tree) {
		Tree parent = tree.parent();
		if (parent == null) {
			return tree;
		}

		if (parent instanceof ClassTree) {
			return parent;
		}

		if (parent instanceof VariableTree) {

		}

		return null;
	}



	static class IssuableTree implements Predicate<Tree> {

		@Override
		public boolean test(Tree tree) {
			tree.is(Kind.METHOD);

			return false;
		}

	}



	private Symbol getSymbolFromReturnCall(ReturnStatementTree parentTree) {
		BlockTree parent = (BlockTree) parentTree.parent();
		MethodTree methodTree = (MethodTree) parent.parent();
		Objects.nonNull(parent);
		return methodTree.symbol();
	}

	private Symbol getSymbolFromClassCall(final VariableTree varTree) {
		final ClassTree callingClass = (ClassTree) varTree.parent();
		return callingClass.symbol();
	}

	private Symbol getSymbolFromMethodCall(final ExpressionStatementTree callingExpression) {
		final BlockTree callingBlock = (BlockTree) callingExpression.parent();

		if (callingBlock.parent().is(Kind.METHOD)) {
			final MethodTree callingMethod = (MethodTree) callingBlock.parent();
			return callingMethod.symbol();
		}

		if (callingBlock.is(Kind.STATIC_INITIALIZER)) {
			final StaticInitializerTree callingMethod = (StaticInitializerTree) callingBlock;
			ClassTree parent = (ClassTree) callingMethod.parent();
			return parent.symbol();
		}


		final MethodTree callingMethod = (MethodTree) callingBlock.parent();
		callingMethod.is(Kind.STATIC_INITIALIZER);
		return callingMethod.symbol();
	}
}
