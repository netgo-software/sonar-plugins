# VisibleForTesting plugin for SonarQube
[![Build Status](https://travis-ci.org/arxes-tolina/sonar-plugins.svg?branch=master)](https://travis-ci.org/arxes-tolina/sonar-plugins)

## Description
This plugin for [SonarQube](http://www.sonarqube.org/) allows developers to detect common mistakes with [Guava](https://github.com/google/guava)'s `@VisibleForTesting` annotation.

It detects illegal access of annotated members and methods as well as illegal use of guava's `@VisibleForTesting` annotation.

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
*   SonarQube 6+ (maybe below but not tested)
*   Java 8

## Installation
* Download the [latest release](https://github.com/arxes-tolina/sonar-plugins/releases/latest)'s plugin jar and put it into the `extensions\plugins` folder of your SonarQube instance. 
* Restart SonarQube.
* Add the rules to your quality profile.

## Changelog

### 0.0.3
* SonarQube 6 compatibility

### 0.0.2
* fixed some administrative properties in the POM file

### 0.0.1  
* Initial release
