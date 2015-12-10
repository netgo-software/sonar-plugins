package de.tolina.sonar.plugins.vft;

import java.util.Collections;

import javax.annotation.Nonnull;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.plugins.java.api.CheckRegistrar;

import com.google.common.annotations.VisibleForTesting;

import de.tolina.sonar.plugins.TolinaSonarPlugin;

/**
 * A {@link CheckRegistrar} for {@link VisibleForTesting} Checks
 */
public class VisibleForTestingCheckRegistrarAndRulesDefinition implements CheckRegistrar, RulesDefinition {

	private static final String VISIBLE_FOR_TESTING = "VisibleForTesting";

	@Override
	public void register(final @Nonnull RegistrarContext registrarContext) {
		registrarContext.registerClassesForRepository(DefaultChecks.REPOSITORY_KEY, DefaultChecks.getChecks(), Collections.emptyList());
	}

	@Override
	public void define(final @Nonnull Context context) {
		final NewRepository repository = context.createRepository(DefaultChecks.REPOSITORY_KEY, TolinaSonarPlugin.JAVA_LANG);
		repository.setName(VISIBLE_FOR_TESTING);
		final RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();

		final Class<?>[] classArray = DefaultChecks.getChecksAsArray();
		annotationLoader.load(repository, classArray);
		repository.done();
	}
}
