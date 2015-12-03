package de.tolina.sonar.plugins.vft.checks;

import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

class GetNextParentSymbol implements Function<Tree, Symbol> {
	//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Function<Tree, Symbol> getSymbol = new GetSymbol();


	@Override
	@Nullable
	public Symbol apply(final @Nullable Tree t) {
		final Optional<Tree> parent = Optional.ofNullable(t).map((tree) -> tree.parent());
		if (parent.isPresent()) {
			final Optional<Symbol> symbol = parent.map(getSymbol);
			return symbol.orElseGet(() -> apply(parent.get()));
		}
		return null;
	}


	/**
	 * This class is a dirty hack, but there is no other way to access the semantic api 
	 *
	 */
	private static class GetSymbol implements Function<Tree, Symbol> {

		@Override
		@Nullable
		public Symbol apply(@Nullable Tree t) {
			if (t instanceof ClassTree) {
				return ((ClassTree) t).symbol();
			}
			if (t instanceof IdentifierTree) {
				return ((IdentifierTree) t).symbol();
			}
			if (t instanceof MethodInvocationTree) {
				return ((MethodInvocationTree) t).symbol();
			}
			if (t instanceof MethodTree) {
				return ((MethodTree) t).symbol();
			}
			if (t instanceof NewClassTree) {
				return ((NewClassTree) t).constructorSymbol();
			}
			if (t instanceof VariableTree) {
				return ((VariableTree) t).symbol();
			}
			return null;
		}

	}
}

