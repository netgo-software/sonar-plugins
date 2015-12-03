package de.tolina.sonar.plugins.vft;

import java.util.Arrays;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import com.google.common.annotations.VisibleForTesting;

/**
 * A {@link CheckRegistrar} for {@link VisibleForTesting} Checks
 */
public class VisibleForTestingCheckRegistrar implements CheckRegistrar {

	@Override
	public void register(RegistrarContext registrarContext) {
		registrarContext.registerClassesForRepository(DefaultChecks.REPOSITORY_KEY, DefaultChecks.getChecks(), Arrays.asList(testCheckClasses()));
	}


	Class<? extends JavaCheck>[] testCheckClasses() {
		return new Class[] {};
	}
}
