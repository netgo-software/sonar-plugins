/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ModifiersTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TypeTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.google.common.annotations.VisibleForTesting;


/**
 * Checks if the {@link VisibleForTesting} annotation is set only at package-protected fields and mehtods.
 */
@Rule(key = MisuseOfVisibleForTestingCheck.RULE_KEY, // 
name = MisuseOfVisibleForTestingCheck.RULE_NAME, // 
description = MisuseOfVisibleForTestingCheck.RULE_NAME, //
tags = { "bad-practice", "design" })
public class MisuseOfVisibleForTestingCheck extends BaseTreeVisitor implements JavaFileScanner {

	private static final String COM_GOOGLE_COMMON_ANNOTATIONS_VISIBLE_FOR_TESTING = VisibleForTesting.class.getCanonicalName();

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Predicate<Tree> isAtPackageVisibleTree = new IsAtPackageVisibleTree();
	private final Function<AnnotationTree, Tree> getTreeOfAnnotation = new GetTreeOfAnnotation();

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
		boolean isVisibleForTesting = annotationType.symbolType().is(COM_GOOGLE_COMMON_ANNOTATIONS_VISIBLE_FOR_TESTING);
		logger.debug("VisibleForTesting found: " + isVisibleForTesting);

		final Tree treeOfAnnotation = getTreeOfAnnotation.apply(annotationTree);
		boolean vftAtPackageVisibleTree = isAtPackageVisibleTree.test(treeOfAnnotation);

		if (!vftAtPackageVisibleTree) {
			context.addIssue(treeOfAnnotation, this, RULE_DESCRIPTION);
		}

		super.visitAnnotation(annotationTree);
	}


	private static class GetTreeOfAnnotation implements Function<AnnotationTree, Tree> {

		@Override
		public Tree apply(final AnnotationTree annotationTree) {
			final ModifiersTree parentModifiersTree = (ModifiersTree) annotationTree.parent();
			final Tree parentTree = parentModifiersTree.parent();
			return parentTree;
		}

	}

	private static class IsAtPackageVisibleTree implements Predicate<Tree> {

		@Override
		public boolean test(Tree tree) {
			Optional<Symbol> symbolOptional = Optional.empty();

			if (tree.is(Kind.VARIABLE)) {
				VariableTree parentVariableTree = (VariableTree) tree;
				symbolOptional = Optional.of(parentVariableTree.symbol());
			}
			if (tree.is(Kind.METHOD)) {
				MethodTree parentMethodTree = (MethodTree) tree;
				symbolOptional = Optional.of(parentMethodTree.symbol());
			}
			if (symbolOptional.isPresent()) {
				Symbol symbol = symbolOptional.get();
				return symbol.isPackageVisibility();
			}
			return false;
		}
	}

}
