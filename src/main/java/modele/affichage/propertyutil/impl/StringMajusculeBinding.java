package modele.affichage.propertyutil.impl;

import javafx.beans.property.StringProperty;
import modele.affichage.propertyutil.FormatableString;
import modele.affichage.propertyutil.StringBindingFormat;

/**
 * Retourne un nouveau binding en ayant mis une majuscule sur la première lettre de la valeur de la property
 * et une minuscule sur les autres.
 * @author Ronan
 *
 */
public class StringMajusculeBinding extends StringBindingFormat {

	public StringMajusculeBinding(final StringProperty property) {
		super(property);
	}

	@Override
	public FormatableString getFormat() {
		return value -> value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
	}

}
