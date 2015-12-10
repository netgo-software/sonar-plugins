/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.SonarPlugin;

import de.tolina.sonar.plugins.vft.VisibleForTestingCheckRegistrarAndRulesDefinition;

/**
 * A collection of tolinas Sonar-Plugins
 */
public class TolinaSonarPlugin extends SonarPlugin {
	//	/** commons-logging Logger für diese Klasse. Per Default auskommentiert */
	//	private final transient Log log = LogFactory.getLog(this.getClass());

	@SuppressWarnings("javadoc")
	public static final String JAVA_LANG = "java";


	@SuppressWarnings("deprecation")
	@Override
	public List<Class<?>> getExtensions() {
		return Arrays.asList(VisibleForTestingCheckRegistrarAndRulesDefinition.class);
	}

}
