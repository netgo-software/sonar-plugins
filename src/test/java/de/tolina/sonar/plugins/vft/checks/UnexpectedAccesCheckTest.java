/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Test: {@link UnexpectedAccesCheck}
 */
public class UnexpectedAccesCheckTest {

	private UnexpectedAccesCheck check;

	@SuppressWarnings("javadoc")
	@Before
	public void initVerifier() {
		check = new UnexpectedAccesCheck();
	}

	@SuppressWarnings("javadoc")
	@Test
	public void test_InvokedFromOtherClass() {
		JavaCheckVerifier.verify("src/junit/de/tolina/sonar/plugins/vft/checks/UnexpectedAccesCheckTestClassCaller.java", check);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void test_InvokedFromSameClass() {
		JavaCheckVerifier.verifyNoIssue("src/junit/de/tolina/sonar/plugins/vft/checks/UnexpectedAccesCheckTestClassCallee.java", check);
	}
}
