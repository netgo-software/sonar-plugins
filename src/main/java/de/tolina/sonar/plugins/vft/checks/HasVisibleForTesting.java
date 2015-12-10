package de.tolina.sonar.plugins.vft.checks;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

import com.google.common.annotations.VisibleForTesting;

class HasVisibleForTesting implements Predicate<Symbol> {
	private final Predicate<AnnotationInstance> isVisibleForTestingAnnotation;


	HasVisibleForTesting() {
		this(new IsVisibleForTesting());
	}

	@VisibleForTesting
	HasVisibleForTesting(final Predicate<AnnotationInstance> isVisibleForTestingAnnotation) {
		this.isVisibleForTestingAnnotation = isVisibleForTestingAnnotation;
	}


	@Override
	public boolean test(final @Nullable Symbol symbol) {
		final Optional<Collection<AnnotationInstance>> annotationsOptional = Optional.ofNullable(symbol).//
				map(Symbol::metadata).//
				map(SymbolMetadata::annotations);
		final Collection<AnnotationInstance> annotations = annotationsOptional.orElse(Collections.emptyList());
		boolean hasVisibleForTesting = annotations.stream().anyMatch(isVisibleForTestingAnnotation);
		return hasVisibleForTesting;
	}

}