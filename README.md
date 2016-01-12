# VisibleForTesting plugin for SonarQube
[![Build Status](https://travis-ci.org/arxes-tolina/sonar-plugins.svg?branch=master)](https://travis-ci.org/arxes-tolina/sonar-plugins)

## Description
Contains rules for detecting illegal access and illegal use of guava's `@VisibleForTesting`
### Unexpected access to @VisibleForTesting
Checks if production code accesses members or methods, that are annotated with `@VisibleForTesting`.

The annotation's goal is to mark members or methods, whose visibility is increased from `private` to default only for testing purposes. If production code acesses those members or methods, this may lead to unexpected behaviours.

_Compliant code_

	/** src/main/java/MyObject.java */
	@VisibleForTesting
	String foo;

	/** src/test/java/MyObjectTest.java */
	new MyObject().foo; // --> ok, accesses member from test code

_Noncompliant code_

	/** src/main/java/MyObject.java */
	@VisibleForTesting
	String foo;

	/** src/main/java/Service.java */
	new MyObject().foo; // --> not ok, accesses member from production code


### Misuse of @VisibleForTesting
Checks if `@VisibleForTesting` is only used on members or methods with default visibility, as its purpose is to mark an increased visibility from `private` to default.

_Compliant code_

	@VisibleForTesting
	String foo;

_Noncompliant code_

	@VisibleForTesting
	private String foo;

	@VisibleForTesting
	protected String foo;

	@VisibleForTesting
	public String foo;

## Prerequisites
*   SonarQube 5.1.2+ (maybe below but not tested)
*   Java 8

## Installation
Run `mvn package` and copy the output JAR file into `extensions\plugins` directory inside of SonarQube. 
Tested with SonarQube 5.2 runnig on Java 8.

## Changelog

### 0.0.2
* fixed some administrative properties in the POM file

### 0.0.1  
* Initial release
