/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;

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

	private final Predicate<AnnotationInstance> isVisibleForTestingAnnotation = new IsVisibleForTesting();
	private final Function<MethodInvocationTree, MethodTree> getInvokingMethod = new GetInvokingMethod();
	private final Predicate<MethodInvocationTree> isCallInsideSameClass = new InvokedInsideSameClas();

	@Override
	public void scanFile(final JavaFileScannerContext ctx) {
		context = ctx;
		scan(ctx.getTree());
	}

	@Override
	public void visitMethodInvocation(final MethodInvocationTree methodInvocationTree) {
		final Symbol methodInvocationSymbol = methodInvocationTree.symbol();
		final List<AnnotationInstance> annotations = methodInvocationSymbol.metadata().annotations();
		final Optional<AnnotationInstance> annotationOptional = annotations.stream().filter(isVisibleForTestingAnnotation).findAny();

		boolean hasVisibleForTesting = annotationOptional.isPresent();
		boolean callFromOtheClass = !isCallInsideSameClass.test(methodInvocationTree);

		if (hasVisibleForTesting && callFromOtheClass) {
			final MethodTree invokingMethod = getInvokingMethod.apply(methodInvocationTree);
			final boolean isDefault = invokingMethod.symbol().isPackageVisibility();
			if (!isDefault) {
				context.addIssue(invokingMethod, this, String.format(RULE_NAME));
			}
		}
		super.visitMethodInvocation(methodInvocationTree);
	}

	@Override
	public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
		String name = tree.identifier().symbol().name();

		logger.debug("visitMemberSelectExpression invoked. Name of the symbol of identifier: " + name);
		super.visitMemberSelectExpression(tree);
	}

}
