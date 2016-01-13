/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.sonar.check.Rule;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.annotations.VisibleForTesting;


/**
 * Checks accessing fields and methods marked as {@link VisibleForTesting}.
 * Only accesses frrm test code or the same class are legal. 
 * 
 */
@Rule(key = UnexpectedAccessCheck.RULE_KEY, // 
name = UnexpectedAccessCheck.RULE_NAME, // 
description = UnexpectedAccessCheck.RULE_DESCRIPTION, //
tags = { Tag.BAD_PRACTICE, Tag.DESIGN })
public class UnexpectedAccessCheck extends BaseTreeVisitor implements JavaFileScanner {

	static final String RULE_KEY = "UnexpectedAccessToVisibleForTesting";
	static final String RULE_NAME = "Unexpected access to @VisibleForTesting";
	static final String RULE_DESCRIPTION = "You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.";

	private JavaFileScannerContext context;

	private final Predicate<Symbol> hasVisibleForTestingPredicate = new HasVisibleForTesting();
	private Function<Tree, Symbol> getSymbol = new GetSymbol();

	@Override
	public void scanFile(final JavaFileScannerContext ctx) {
		context = ctx;
		scan(ctx.getTree());
	}


	@Override
	public void visitNewClass(final @Nonnull NewClassTree tree) {
		final Symbol symbol = getSymbol.apply(tree);
		addIssueIfNeeded(symbol, tree);
		super.visitNewClass(tree);
	}


	@Override
	public void visitMemberSelectExpression(final @Nonnull MemberSelectExpressionTree tree) {
		final Symbol symbol = tree.identifier().symbol();
		addIssueIfNeeded(symbol, tree);
		super.visitMemberSelectExpression(tree);
	}

	private void addIssueIfNeeded(final Symbol symbol, final Tree tree) {
		final Optional<Symbol> symbolWithVisibleForTesting = Optional.ofNullable(symbol).filter(hasVisibleForTestingPredicate);
		symbolWithVisibleForTesting.ifPresent(s -> context.addIssue(tree, this, String.format(RULE_DESCRIPTION)));
	}

}
