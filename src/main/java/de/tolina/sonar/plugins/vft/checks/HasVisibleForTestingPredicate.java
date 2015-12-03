package de.tolina.sonar.plugins.vft.checks;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

class HasVisibleForTestingPredicate implements Predicate<Symbol> {
	private final Predicate<AnnotationInstance> isVisibleForTestingAnnotation = new IsVisibleForTesting();

	@Override
	public boolean test(Symbol symbol) {
		final Collection<AnnotationInstance> annotations = symbol.metadata().annotations();
		final Optional<AnnotationInstance> annotationOptional = annotations.stream().filter(isVisibleForTestingAnnotation).findAny();
		boolean hasVisibleForTesting = annotationOptional.isPresent();
		return hasVisibleForTesting;
	}

}