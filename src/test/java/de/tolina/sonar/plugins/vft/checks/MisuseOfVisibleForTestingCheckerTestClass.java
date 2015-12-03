/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import com.google.common.annotations.VisibleForTesting;

class MisuseOfVisibleForTestingCheckerTestClass {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@VisibleForTesting // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}}
	private Object privateObject = new Object();
	@VisibleForTesting
	Object defaultObject = new Object();
	@VisibleForTesting // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}} 
	public Object publicObject = new Object();

	@VisibleForTesting // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}}
	private void privateMethod() {
		// NOOP
	}

	@VisibleForTesting
	void defaultMethod() {
		// NOOP
	}

	@VisibleForTesting // Noncompliant {{You must use @VisibleForTesting annotation only at package-private methods or package-private fields.}}
	public void publicMethod() {
		// NOOP
	}
}
