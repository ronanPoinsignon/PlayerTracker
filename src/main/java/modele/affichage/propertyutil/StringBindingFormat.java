package modele.affichage.propertyutil;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.StringProperty;

public abstract class StringBindingFormat extends StringBinding {

	private final StringProperty property;

	protected StringBindingFormat(final StringProperty property) {
		this.property = property;
		bind(property);
	}

	@Override
	protected String computeValue() {
		final var value = property.get();
		if(value == null) {
			return "";
		}
		return getFormat().format(value);
	}

	public abstract FormatableString getFormat();
}
