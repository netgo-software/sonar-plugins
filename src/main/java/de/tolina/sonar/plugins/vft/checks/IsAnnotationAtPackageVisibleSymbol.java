package de.tolina.sonar.plugins.vft.checks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.annotations.VisibleForTesting;

/**
 * {@link Predicate} that answers if an {@link AnnotationTree} is placed at a {@link Symbol}
 * with "package protected / default" visibility.
 */
class IsAnnotationAtPackageVisibleSymbol implements Predicate<AnnotationTree> {
	private final Function<Tree, Symbol> getNextParentSymbol;

	@VisibleForTesting
	IsAnnotationAtPackageVisibleSymbol(final Function<Tree, Symbol> getNextParentSymbol) {
		this.getNextParentSymbol = getNextParentSymbol;
	}

	IsAnnotationAtPackageVisibleSymbol() {
		this(new GetNextParentSymbol());
	}


	@Override
	public boolean test(final AnnotationTree annotationTree) {
		final Optional<Symbol> symbol = Optional.ofNullable(annotationTree).map(getNextParentSymbol);
		final Boolean isAtPackageVisibleTree = symbol.map(Symbol::isPackageVisibility).//
				orElse(Boolean.FALSE);
		return isAtPackageVisibleTree.booleanValue();
	}
}