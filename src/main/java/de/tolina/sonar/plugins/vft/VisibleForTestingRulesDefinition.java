/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;

import com.google.common.annotations.VisibleForTesting;

import de.tolina.sonar.plugins.TolinaSonarPlugin;

/**
 * {@link RulesDefinition} for {@link VisibleForTesting}.
 * @see DefaultChecks for contained checks
 * 
 */
public class VisibleForTestingRulesDefinition implements RulesDefinition {


	@Override
	public void define(Context context) {
		final NewRepository repository = context.createRepository(DefaultChecks.REPOSITORY_KEY, TolinaSonarPlugin.JAVA_LANG);
		repository.setName("VisibleForTesting");
		final RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();

		final Class<?>[] classArray = DefaultChecks.getChecksAsArray();
		annotationLoader.load(repository, classArray);
		repository.done();
	}



}
