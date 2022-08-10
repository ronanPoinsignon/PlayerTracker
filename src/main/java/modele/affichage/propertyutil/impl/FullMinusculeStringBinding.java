package modele.affichage.propertyutil.impl;

import javafx.beans.property.StringProperty;
import modele.affichage.propertyutil.FormatableString;
import modele.affichage.propertyutil.StringBindingFormat;

public class FullMinusculeStringBinding extends StringBindingFormat {

	private static final FormatableString FORMAT = String::toLowerCase;

	public FullMinusculeStringBinding(final StringProperty property, final FormatableString formatFunction) {
		super(property, FullMinusculeStringBinding.FORMAT);
	}

}
