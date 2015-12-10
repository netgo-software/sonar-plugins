/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

import com.google.common.annotations.VisibleForTesting;

class IsVisibleForTesting implements Predicate<AnnotationInstance> {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());


	@VisibleForTesting
	final static String ANNOTAION_PACKAGE = "com.google.common.annotations";
	@VisibleForTesting
	final static String ANNOTAION_CLASS = "VisibleForTesting";


	@Override
	public boolean test(final @Nullable AnnotationInstance annotationInstance) {
		final Optional<String> annotationClass = Optional.ofNullable(annotationInstance).//
				map(AnnotationInstance::symbol).//
				map(Symbol::name);
		final Optional<String> annotationPackage = Optional.ofNullable(annotationInstance).//
				map(AnnotationInstance::symbol).//
				map(Symbol::owner).//
				map(Symbol::name);

		boolean samePackage = Objects.equals(ANNOTAION_PACKAGE, annotationPackage.orElse(null));
		boolean sameClass = Objects.equals(ANNOTAION_CLASS, annotationClass.orElse(null));

		return sameClass && samePackage;
	}

}
