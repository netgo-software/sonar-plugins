# sonar-plugins
[![wercker status](https://app.wercker.com/status/53356c399a2ec78b535c670da845465c/s "wercker status")](https://app.wercker.com/project/bykey/53356c399a2ec78b535c670da845465c)
## Description
Contains rules for detecting illegal access and illegal use of `@VisibleForTesting`
### de.tolina.sonar.plugins.vft.checks.UnexpectedAccessCheck
Checks if a other classes access variables or methods, that are annotated with `com.google.common.annotations.VisibleForTesting`.
### de.tolina.sonar.plugins.vft.checks.MisuseOfVisibleForTestingCheck
Checks if com.google.common.annotations.VisibleForTesting is only used at variables or methods with package protected visibility.

## Usage
Run `mvn package` and copy the output JAR file into `extensions\plugins` directory inside of SonarQube. 
Tested with SonarQube 5.2 runnig on Java 8.  

## Changelog

### 0.0.2
* fixed some administrative properties in the POM file

### 0.0.1  
* Initial release