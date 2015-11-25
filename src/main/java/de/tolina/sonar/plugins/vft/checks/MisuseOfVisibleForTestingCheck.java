/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.TypeTree;

import com.google.common.annotations.VisibleForTesting;


/**
 * Checks if the {@link VisibleForTesting} annotation is set only at package-protected fields and mehtods.
 */
@Rule(key = MisuseOfVisibleForTestingCheck.RULE_KEY, // 
name = MisuseOfVisibleForTestingCheck.RULE_NAME, // 
description = MisuseOfVisibleForTestingCheck.RULE_NAME, //
tags = { "bad-practice", "design" })
public class MisuseOfVisibleForTestingCheck extends BaseTreeVisitor implements JavaFileScanner {

	private static final String COM_GOOGLE_COMMON_ANNOTATIONS_VISIBLE_FOR_TESTING = "com.google.common.annotations.VisibleForTesting";


	private final Logger logger = LoggerFactory.getLogger(getClass());


	static final String RULE_KEY = "MisuseOfVisibleForTesting";
	static final String RULE_NAME = "You must use @VisibleForTesting annotation only at package-private methods or package-private fields.";
	static final String RULE_DESCRIPTION = "You must use  @VisibleForTesting annotation only at package-private methods or package-private fields.";

	//private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext ctx) {
		//context = ctx;
		scan(ctx.getTree());
	}

	@Override
	public void visitAnnotation(AnnotationTree annotationTree) {
		final TypeTree annotationType = annotationTree.annotationType();
		boolean isVisibleForTesting = annotationType.symbolType().is(COM_GOOGLE_COMMON_ANNOTATIONS_VISIBLE_FOR_TESTING);
		logger.debug("VisibleForTesting found: " + isVisibleForTesting);
		super.visitAnnotation(annotationTree);
	}

}
