package modele.affichage.propertyutil.impl;

import javafx.beans.property.StringProperty;
import modele.affichage.propertyutil.FormatableString;
import modele.affichage.propertyutil.StringBindingFormat;

public class FullMajusculeStringBinding extends StringBindingFormat {

	public FullMajusculeStringBinding(final StringProperty property) {
		super(property);
	}

	@Override
	public FormatableString getFormat() {
		return String::toUpperCase;
	}

}
