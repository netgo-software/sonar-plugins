/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import com.google.common.annotations.VisibleForTesting;

class MisuseOfVisibleForTestingCheckerTestClass {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@VisibleForTesting
	private Object privateObject = new Object(); // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}}
	@VisibleForTesting
	Object defaultObject = new Object();
	@VisibleForTesting
	public Object publicObject = new Object(); // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}}

	@VisibleForTesting
	private void privateMethod() { // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}}
		// NOOP
	}

	@VisibleForTesting
	void defaultMethod() {
		// NOOP
	}

	@VisibleForTesting
	public void publicMethod() { // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}}
		// NOOP
	}
}
