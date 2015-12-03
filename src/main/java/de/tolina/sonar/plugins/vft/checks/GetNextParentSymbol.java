package de.tolina.sonar.plugins.vft.checks;

import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.Tree;

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
}

