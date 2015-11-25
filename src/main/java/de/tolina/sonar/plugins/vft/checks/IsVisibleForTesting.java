/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.function.Predicate;

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

}
