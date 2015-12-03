/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.SonarPlugin;

import de.tolina.sonar.plugins.vft.VisibleForTestingCheckRegistrar;
import de.tolina.sonar.plugins.vft.VisibleForTestingRulesDefinition;

/**
 * A collection of tolinas Sonar-Plugins
 */
public class TolinaSonarPlugin extends SonarPlugin {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@SuppressWarnings("javadoc")
	public static final String JAVA_LANG = "java";


	@Override
	public List getExtensions() {
		return Arrays.asList(VisibleForTestingRulesDefinition.class, VisibleForTestingCheckRegistrar.class);
	}

}
