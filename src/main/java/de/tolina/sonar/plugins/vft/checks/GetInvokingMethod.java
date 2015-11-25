/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.function.Function;

import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;

final class GetInvokingMethod implements Function<MethodInvocationTree, MethodTree> {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@Override
	public MethodTree apply(final MethodInvocationTree t) {
		final ExpressionStatementTree callingExpression = (ExpressionStatementTree) t.parent();
		final BlockTree callingBlock = (BlockTree) callingExpression.parent();
		final MethodTree callingMethod = (MethodTree) callingBlock.parent();
		return callingMethod;
	}
}
