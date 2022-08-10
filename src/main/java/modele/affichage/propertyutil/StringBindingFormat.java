package modele.affichage.propertyutil;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.StringProperty;

public abstract class StringBindingFormat extends StringBinding {

	private final StringProperty property;
	private final FormatableString formatFunction;

	public StringBindingFormat(final StringProperty property, final FormatableString formatFunction) {
		this.property = property;
		this.formatFunction = formatFunction;
		bind(property);
	}

	@Override
	protected String computeValue() {
		final var value = property.get();
		if(value == null) {
			return "";
		}
		return formatFunction.format(value);
	}
}
