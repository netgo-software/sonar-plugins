/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Objects;

class UnexpectedAccessCheckTestClassCaller {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());


	private static final UnexpectedAccessCheckTestClassCallee staticCallee = new UnexpectedAccessCheckTestClassCallee(); // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}
	static final Object staticObject = staticCallee.visibleForTesting; // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}
	static final Object staticObjectFromStatic = UnexpectedAccessCheckTestClassCallee.staticVisibleForTesting; // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}

	static {
		staticCallee.methodeCallee(); // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}
	}

	private final UnexpectedAccessCheckTestClassCallee callee = new UnexpectedAccessCheckTestClassCallee(new Object());// Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}

	void defaultMethodeCaller() {
		callee.methodeCallee(); // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}
	}

	@SuppressWarnings("unused")
	private void privateMethodeCaller() {
		callee.methodeCallee(); // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}
	}

	public void publicMethodeCaller() {
		callee.methodeCallee(); // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}
	}

	protected void protectedMethodeCaller() {
		callee.methodeCallee(); // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}
	}


	void defaultAccesMember() {
		Objects.requireNonNull(callee.visibleForAll);
		Objects.requireNonNull(callee.visibleForTesting); // Noncompliant {{You must not access package-private method or field which is annotated by <code>@VisibleForTesting</code>.}}		
	}
}
