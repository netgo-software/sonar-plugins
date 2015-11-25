/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

final class IsAnAnnotation implements Predicate<AnnotationInstance> {

	private final Logger logger = LoggerFactory.getLogger(IsAnAnnotation.class);

	private final String packageName;
	private final String className;

	IsAnAnnotation(final String packageName, final String className) {
		this.packageName = packageName;
		this.className = className;
	}

	@Override
	public boolean test(final AnnotationInstance annotationInstance) {
		final String annotationName = annotationInstance.symbol().name();
		final String annotationPackage = annotationInstance.symbol().owner().name();
		logger.debug("Checking Annotation. Expected [" + packageName + "." + className + "] got [" + // 
				annotationPackage + "." + annotationName + "]");

		boolean sameAnnotation = Objects.equals(className, annotationName);
		boolean samePackage = Objects.equals(packageName, annotationPackage);
		return sameAnnotation && samePackage;
	}
}