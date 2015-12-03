package de.tolina.sonar.plugins.vft;

import java.util.Collections;

import org.sonar.plugins.java.api.CheckRegistrar;

import com.google.common.annotations.VisibleForTesting;

/**
 * A {@link CheckRegistrar} for {@link VisibleForTesting} Checks
 */
public class VisibleForTestingCheckRegistrar implements CheckRegistrar {

	@Override
	public void register(RegistrarContext registrarContext) {
		registrarContext.registerClassesForRepository(DefaultChecks.REPOSITORY_KEY, DefaultChecks.getChecks(), Collections.emptyList());
	}
}
