/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;

import com.google.common.annotations.VisibleForTesting;

import de.tolina.sonar.plugins.Tags;


/**
 * Checks accessing fields and methods marked as {@link VisibleForTesting}.
 * Only accesses form test code or the same class are legal. 
 * 
 */
@Rule(key = UnexpectedAccessCheck.RULE_KEY, // 
name = UnexpectedAccessCheck.RULE_NAME, // 
description = UnexpectedAccessCheck.RULE_NAME, //
tags = { Tags.BAD_PRACTICE, Tags.DESIGN })
public class UnexpectedAccessCheck extends BaseTreeVisitor implements JavaFileScanner {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	static final String RULE_KEY = "UnexpectedAccessToVisibleForTesting";
	static final String RULE_NAME = "You must not access to package-private method or field which is annotated by @VisibleForTesting.";
	static final String RULE_DESCRIPTION = "<p>You must not access to package-private method or field which is annotated by @VisibleForTesting.</p>"//
			+ "<p>This annotation means that visibility was widened only for test code, so your implementation code</p>"//
			+ "<p>shouldn't access to method which is annotated by this annotation.</p>";

	private JavaFileScannerContext context;

	private final Predicate<Symbol> hasVisibleForTestingPredicate = new HasVisibleForTestingPredicate();

	@Override
	public void scanFile(final JavaFileScannerContext ctx) {
		context = ctx;
		scan(ctx.getTree());
	}



	@Override
	public void visitMemberSelectExpression(final MemberSelectExpressionTree tree) {
		final Symbol selectedMemberSymbol = tree.identifier().symbol();
		boolean hasVisibleForTesting = hasVisibleForTestingPredicate.test(selectedMemberSymbol);
		if (hasVisibleForTesting) {
			logger.debug("Issue at: " + selectedMemberSymbol.name());
			context.addIssue(tree, this, String.format(RULE_NAME));
		}
		super.visitMemberSelectExpression(tree);
	}
}
