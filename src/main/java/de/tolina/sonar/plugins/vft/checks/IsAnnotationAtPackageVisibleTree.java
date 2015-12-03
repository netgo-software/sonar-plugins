package de.tolina.sonar.plugins.vft.checks;

import java.util.function.Function;
import java.util.function.Predicate;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ModifiersTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

class IsAnnotationAtPackageVisibleTree implements Predicate<AnnotationTree> {
	private final Function<AnnotationTree, Tree> getTreeOfAnnotation = new GetTreeOfAnnotation();

	@Override
	public boolean test(final AnnotationTree annotationTree) {
		Symbol symbol = null;
		final Tree treeOfAnnotation = getTreeOfAnnotation.apply(annotationTree);
		if (treeOfAnnotation.is(Kind.VARIABLE)) {
			VariableTree parentVariableTree = (VariableTree) treeOfAnnotation;
			symbol = parentVariableTree.symbol();
		}
		if (treeOfAnnotation.is(Kind.METHOD)) {
			MethodTree parentMethodTree = (MethodTree) treeOfAnnotation;
			symbol = parentMethodTree.symbol();
		}
		if (symbol != null) {
			return symbol.isPackageVisibility();
		}
		return false;
	}

	private static class GetTreeOfAnnotation implements Function<AnnotationTree, Tree> {
		@Override
		public Tree apply(final AnnotationTree annotationTree) {
			final ModifiersTree parentModifiersTree = (ModifiersTree) annotationTree.parent();
			final Tree parentTree = parentModifiersTree.parent();
			return parentTree;
		}

	}
}