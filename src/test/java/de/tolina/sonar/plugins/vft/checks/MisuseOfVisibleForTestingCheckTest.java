/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Tests  {@link MisuseOfVisibleForTestingCheck}
 */
public class MisuseOfVisibleForTestingCheckTest {

	private MisuseOfVisibleForTestingCheck check;

	@SuppressWarnings("javadoc")
	@Before
	public void initVerifier() {
		check = new MisuseOfVisibleForTestingCheck();
	}

	@SuppressWarnings("javadoc")
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/java/de/tolina/sonar/plugins/vft/checks/MisuseOfVisibleForTestingCheckerTestClass.java", check, "target/test-jars");
	}
}
