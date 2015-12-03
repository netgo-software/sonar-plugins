/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.semantic.SymbolMetadata.AnnotationInstance;

class IsVisibleForTesting implements Predicate<AnnotationInstance> {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());


	private final String ANNOTAION_PACKAGE = "com.google.common.annotations";
	private final String ANNOTAION_NAME = "VisibleForTesting";

	private final Predicate<AnnotationInstance> isVisibleForTestingAnnotation = new IsAnAnnotation(ANNOTAION_PACKAGE, ANNOTAION_NAME);

	@Override
	public boolean test(final AnnotationInstance t) {
		return isVisibleForTestingAnnotation.test(t);
	}


	private static class IsAnAnnotation implements Predicate<AnnotationInstance> {

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
}
