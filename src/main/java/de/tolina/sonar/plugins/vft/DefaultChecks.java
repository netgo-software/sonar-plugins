/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nonnull;

import org.sonar.plugins.java.api.JavaCheck;

import de.tolina.sonar.plugins.vft.checks.MisuseOfVisibleForTestingCheck;
import de.tolina.sonar.plugins.vft.checks.UnexpectedAccessCheck;

class DefaultChecks {

	private static final Collection<Class<? extends JavaCheck>> defaultChecks = new ArrayList<Class<? extends JavaCheck>>();

	static final String REPOSITORY_KEY = "VisibleForTesting-Rules";


	static {
		initChecks(defaultChecks);
	}

	private static void initChecks(final @Nonnull Collection<Class<? extends JavaCheck>> checks) {
		checks.add(UnexpectedAccessCheck.class);
		checks.add(MisuseOfVisibleForTestingCheck.class);
	}

	static Collection<Class<? extends JavaCheck>> getChecks() {
		return Collections.unmodifiableCollection(defaultChecks);
	}

	static Class<?>[] getChecksAsArray() {
		return getClassArray(getChecks());
	}

	private static Class<?>[] getClassArray(final @Nonnull Collection<Class<? extends JavaCheck>> checks) {
		return checks.toArray(new Class[checks.size()]);
	}
}
