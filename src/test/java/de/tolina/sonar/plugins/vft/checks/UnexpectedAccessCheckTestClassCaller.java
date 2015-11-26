/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Objects;

class UnexpectedAccessCheckTestClassCaller {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());


	private final UnexpectedAccessCheckTestClassCallee callee = new UnexpectedAccessCheckTestClassCallee();

	void defaultMethodeCaller() {
		callee.methodeCallee();
	}

	@SuppressWarnings("unused")
	private void privateMethodeCaller() { // Noncompliant {{You must not access to package-private method or field which is annotated by @VisibleForTesting.}}
		callee.methodeCallee();
	}

	public void publicMethodeCaller() { // Noncompliant {{You must not access to package-private method or field which is annotated by @VisibleForTesting.}}
		callee.methodeCallee();
	}

	protected void protectedMethodeCaller() { // Noncompliant {{You must not access to package-private method or field which is annotated by @VisibleForTesting.}}
		callee.methodeCallee();
	}


	void defaultAccesMember() {
		Objects.requireNonNull(callee.visibleForAll);
		Objects.requireNonNull(callee.visibleForTesting); // Noncompliant {{You must not access to package-private method or field which is annotated by @VisibleForTesting.}}		
	}
}
