package de.tolina.sonar.plugins.vft.checks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.Tree;

/**
 * Searches in the parent hierarchy for a {@link Tree} with a {@link Symbol}.
 * Returns this {@link Symbol} or null, if no {@link Symbol} was found.
 *
 */
class GetNextParentSymbol implements Function<Tree, Symbol> {
	//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Function<Tree, Symbol> getSymbol = new GetSymbol();


	@Override
	@Nullable
	public Symbol apply(final @Nullable Tree t) {
		final Optional<Tree> treeNullable = Optional.ofNullable(t);
		if (!treeNullable.isPresent()) {
			return null;
		}
		final Optional<Tree> parent = treeNullable.map((tree) -> tree.parent());
		final Optional<Symbol> symbol = parent.map(getSymbol);
		final Supplier<Symbol> getFromParent = () -> (apply(parent.orElse(null)));
		return symbol.orElseGet(getFromParent);
	}
}

