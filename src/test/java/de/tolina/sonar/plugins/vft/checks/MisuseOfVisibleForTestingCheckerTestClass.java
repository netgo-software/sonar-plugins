/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import com.google.common.annotations.VisibleForTesting;

class MisuseOfVisibleForTestingCheckerTestClass {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	private Object privateObject = new Object();
	@VisibleForTesting
	Object defaultObject = new Object();
	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}} 
	protected Object protectedObject = new Object();
	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}} 
	public Object publicObject = new Object();

	@VisibleForTesting
	MisuseOfVisibleForTestingCheckerTestClass() {

	}

	@SuppressWarnings("unused")
	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	private MisuseOfVisibleForTestingCheckerTestClass(Object obj) {
		// NOOP
	}

	@SuppressWarnings("unused")
	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	public MisuseOfVisibleForTestingCheckerTestClass(Object obj1, Object obj2) {
		// NOOP
	}



	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	private void privateMethod() {
		// NOOP
	}

	@VisibleForTesting
	void defaultMethod() {
		// NOOP
	}

	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	protected void protectedMethod() {
		// NOOP
	}


	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	public void publicMethod() {
		// NOOP
	}

	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	private static class PrivateClass {
		// NOOP
	}


	@VisibleForTesting // Noncompliant {{You can use <code>@VisibleForTesting</code> annotation only at package-private methods or package-private fields.}}
	public static class PublicClass {
		// NOOP
	}

	@VisibleForTesting
	static class DefaultClass {
		// NOOP
	}
}
