package de.tolina.sonar.plugins.vft.checks;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

import com.google.common.annotations.VisibleForTesting;

class HasVisibleForTestingPredicate implements Predicate<Symbol> {
	private final Predicate<AnnotationInstance> isVisibleForTestingAnnotation;


	HasVisibleForTestingPredicate() {
		this(new IsVisibleForTesting());
	}

	@VisibleForTesting
	HasVisibleForTestingPredicate(final Predicate<AnnotationInstance> isVisibleForTestingAnnotation) {
		this.isVisibleForTestingAnnotation = isVisibleForTestingAnnotation;
	}


	@Override
	public boolean test(Symbol symbol) {
		final Optional<Collection<AnnotationInstance>> annotationsOptional = Optional.ofNullable(symbol).map(s -> s.metadata()).map(m -> m.annotations());
		final Collection<AnnotationInstance> annotations = annotationsOptional.orElse(Collections.emptyList());
		final Optional<AnnotationInstance> annotationOptional = annotations.stream().filter(isVisibleForTestingAnnotation).findAny();
		boolean hasVisibleForTesting = annotationOptional.isPresent();
		return hasVisibleForTesting;
	}

}