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


/**
 * Checks accessing fields and methods marked as {@link VisibleForTesting}.
 * Only accesses form test code or the same class are legal. 
 * 
 */
@Rule(key = UnexpectedAccessCheck.RULE_KEY, // 
name = UnexpectedAccessCheck.RULE_NAME, // 
description = UnexpectedAccessCheck.RULE_NAME, //
tags = { "bad-practice", "design" })
public class UnexpectedAccessCheck extends BaseTreeVisitor implements JavaFileScanner {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	static final String RULE_KEY = "UnexpectedAccessToVisibleForTesting";
	static final String RULE_NAME = "You must not access to package-private method or field which is annotated by @VisibleForTesting.";
	static final String RULE_DESCRIPTION = "<p>You must not access to package-private method or field which is annotated by @VisibleForTesting.</p>"//
			+ "<p>This annotation means that visibility was widened only for test code, so your implementation code</p>"//
			+ "<p>shouldn't access to method which is annotated by this annotation.</p>";

	private JavaFileScannerContext context;


	//private final Function<Tree, Symbol> getAccesingSymbol = new GetAccessingSymbol();
	private final Predicate<Symbol> hasVisibleForTestingPredicate = new HasVisibleForTestingPredicate();

	@Override
	public void scanFile(final JavaFileScannerContext ctx) {
		context = ctx;
		scan(ctx.getTree());
	}



	@Override
	public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
		Symbol selectedMemberSymbol = tree.identifier().symbol();

		boolean hasVisibleForTesting = hasVisibleForTestingPredicate.test(selectedMemberSymbol);

		String selectedMemberName = selectedMemberSymbol.name();
		//		Optional<Symbol> accessingSymbolOptional = Optional.of(tree).map(getAccesingSymbol);
		//		String accessingSymbolName = accessingSymbolOptional.map(symbol -> symbol.name()).//
		//				orElse("No symbol");

		if (hasVisibleForTesting) {
			//			if (hasVisibleForTesting && accessingSymbolOptional.isPresent()) {
			context.addIssue(tree, this, String.format(RULE_NAME));
		}
		logger.debug("visitMemberSelectExpression invoked. Name of the symbol of identifier: " + selectedMemberName);
		//		logger.debug("visitMemberSelectExpression invoked. Name of the symbol of accesing symbol: " + accessingSymbolName);
		super.visitMemberSelectExpression(tree);
	}
}
