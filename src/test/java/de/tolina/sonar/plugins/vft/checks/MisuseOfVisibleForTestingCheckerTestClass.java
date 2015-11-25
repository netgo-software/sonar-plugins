/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import com.google.common.annotations.VisibleForTesting;

class MisuseOfVisibleForTestingCheckerTestClass {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@VisibleForTesting
	private Object privateObject = new Object();
	@VisibleForTesting
	Object defaultObject = new Object();
	@VisibleForTesting
	public Object publicObject = new Object();

	@VisibleForTesting
	private void privateMethod() {
		// NOOP
	}

	@VisibleForTesting
	void defaultMethod() {
		// NOOP
	}

	@VisibleForTesting
	public void publicMethod() {
		// NOOP
	}
}
