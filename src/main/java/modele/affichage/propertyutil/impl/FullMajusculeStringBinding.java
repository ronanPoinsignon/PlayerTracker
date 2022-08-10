package modele.affichage.propertyutil.impl;

import javafx.beans.property.StringProperty;
import modele.affichage.propertyutil.FormatableString;
import modele.affichage.propertyutil.StringBindingFormat;

public class FullMajusculeStringBinding extends StringBindingFormat {

	private static final FormatableString FORMAT = String::toUpperCase;

	public FullMajusculeStringBinding(final StringProperty property, final FormatableString formatFunction) {
		super(property, FullMajusculeStringBinding.FORMAT);
	}

}
