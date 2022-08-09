package modele.affichage.strategie;

import javafx.application.Platform;
import modele.affichage.PaneViewElement;

public class StrategieSuppression<T> implements IStrategiePaneViewElement<T> {
	private PaneViewElement<T> viewElement;
	
	public StrategieSuppression(PaneViewElement<T> viewElement) {
		this.viewElement = viewElement;
	}

	@Override
	public void execute(T element) {
		final var set = viewElement.getPaneMap()
				.entrySet()
				.stream()
				.filter(predicate -> predicate.getValue().equals(element))
				.findFirst()
				.orElse(null);
		
		if(set == null)
			return;
		
		final var node = set.getKey();
		
		viewElement.getPaneMap().remove(node);
		viewElement.getSortedPane().remove(node);
		
		Platform.runLater(() -> {
			viewElement.getChildren().remove(node);
			viewElement.updateSort();
		});
		
	}
}
