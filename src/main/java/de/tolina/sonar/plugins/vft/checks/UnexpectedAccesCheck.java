/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;

import com.google.common.annotations.VisibleForTesting;


/**
 * Checks accessing fields and methods marked as {@link VisibleForTesting}.
 * Only accesses form test code or the same class are legal. 
 * 
 */
@Rule(key = UnexpectedAccesCheck.RULE_KEY, // 
name = UnexpectedAccesCheck.RULE_NAME, // 
description = UnexpectedAccesCheck.RULE_NAME, //
tags = { "bad-practice", "design" })
public class UnexpectedAccesCheck extends BaseTreeVisitor implements JavaFileScanner {

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

		if (!annotationOptional.isPresent()) {
			return;
		}

		if (isCallInsideSameClass.test(methodInvocationTree)) {
			return;
		}

		final MethodTree invokingMethod = getInvokingMethod.apply(methodInvocationTree);
		final boolean isDefault = invokingMethod.symbol().isPackageVisibility();
		if (!isDefault) {
			context.addIssue(invokingMethod, this, String.format("You must not access to package-private method or field which is annotated by @VisibleForTesting."));
		}
		super.visitMethodInvocation(methodInvocationTree);
	}


}
