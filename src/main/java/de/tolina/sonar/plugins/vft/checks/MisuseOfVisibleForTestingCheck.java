/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.TypeTree;

import com.google.common.annotations.VisibleForTesting;

import de.tolina.sonar.plugins.SonarQubeTags;


/**
 * Checks if the {@link VisibleForTesting} annotation is set only at package-protected fields and mehtods.
 */
@Rule(key = MisuseOfVisibleForTestingCheck.RULE_KEY, // 
name = MisuseOfVisibleForTestingCheck.RULE_NAME, // 
description = MisuseOfVisibleForTestingCheck.RULE_NAME, //
tags = { SonarQubeTags.BAD_PRACTICE, SonarQubeTags.DESIGN })
public class MisuseOfVisibleForTestingCheck extends BaseTreeVisitor implements JavaFileScanner {

	private static final String VISIBLE_FOR_TESTING = VisibleForTesting.class.getCanonicalName();

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Predicate<AnnotationTree> isAtPackageVisibleTree = new IsAnnotationAtPackageVisibleTree();


	static final String RULE_KEY = "MisuseOfVisibleForTesting";
	static final String RULE_NAME = "You must use @VisibleForTesting annotation only at package-private methods or package-private fields.";
	static final String RULE_DESCRIPTION = RULE_NAME;

	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext ctx) {
		context = ctx;
		scan(ctx.getTree());
	}

	@Override
	public void visitAnnotation(final AnnotationTree annotationTree) {
		final TypeTree annotationType = annotationTree.annotationType();
		boolean isVisibleForTesting = annotationType.symbolType().is(VISIBLE_FOR_TESTING);
		if (isVisibleForTesting) {
			logger.debug("VisibleForTesting found");
			final Optional<AnnotationTree> vftAtNotPackageProtected = Optional.of(annotationTree).filter(isAtPackageVisibleTree.negate());
			vftAtNotPackageProtected.ifPresent((tree) -> context.addIssue(tree, this, RULE_DESCRIPTION));
		}
		super.visitAnnotation(annotationTree);
	}



}
