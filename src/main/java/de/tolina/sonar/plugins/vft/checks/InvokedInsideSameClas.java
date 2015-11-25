/*
 *  (c) tolina GmbH, 2015
 */
package de.tolina.sonar.plugins.vft.checks;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;

final class InvokedInsideSameClas implements Predicate<MethodInvocationTree> {

	private final Function<MethodInvocationTree, MethodTree> getCallingMethod = new GetInvokingMethod();

	@Override
	public boolean test(final MethodInvocationTree invokedMethod) {
		final Symbol invokedMethodOwnerClass = invokedMethod.symbol().owner();
		final Symbol invokedMethodOwnerPackage = invokedMethodOwnerClass.owner();

		final MethodTree invokingMethod = getCallingMethod.apply(invokedMethod);
		final Symbol invokingMethodOwnerClass = invokingMethod.symbol().owner();
		final Symbol invokingMethodOwnerPackage = invokingMethodOwnerClass.owner();

		boolean sameClass = Objects.equals(invokingMethodOwnerClass, invokedMethodOwnerClass);
		boolean samePackage = Objects.equals(invokingMethodOwnerPackage, invokedMethodOwnerPackage);
		return sameClass && samePackage;
	}
}
