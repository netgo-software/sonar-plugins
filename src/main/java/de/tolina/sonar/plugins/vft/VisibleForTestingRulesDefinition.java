/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.plugins.java.api.JavaCheck;

import com.google.common.annotations.VisibleForTesting;

import de.tolina.sonar.plugins.TolinaSonarPlugin;
import de.tolina.sonar.plugins.vft.checks.UnexpectedAccessCheck;

/**
 * {@link RulesDefinition} for {@link VisibleForTesting}.
 * @see #initChecks() for contained checks
 * 
 */
public class VisibleForTestingRulesDefinition implements RulesDefinition {

	@SuppressWarnings("javadoc")
	public static final String REPOSITORY_KEY = "VisibleForTesting-Rules";

	private final Collection<Class<? extends JavaCheck>> checkClasses;

	@SuppressWarnings("javadoc")
	public VisibleForTestingRulesDefinition() {
		checkClasses = initChecks();
	}

	private Collection<Class<? extends JavaCheck>> initChecks() {
		final Collection<Class<? extends JavaCheck>> checks = new ArrayList<Class<? extends JavaCheck>>();

		checks.add(UnexpectedAccessCheck.class);

		return Collections.unmodifiableCollection(checks);
	}

	@Override
	public void define(Context context) {
		final NewRepository repository = context.createRepository(REPOSITORY_KEY, TolinaSonarPlugin.JAVA_LANG);
		repository.setName("VisibleForTesting");
		final RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();

		final Class<?>[] classArray = getClassArray(checkClasses);
		annotationLoader.load(repository, classArray);
		repository.done();
	}

	private Class<?>[] getClassArray(Collection<Class<? extends JavaCheck>> checks) {
		return checks.toArray(new Class[checks.size()]);
	}

}
