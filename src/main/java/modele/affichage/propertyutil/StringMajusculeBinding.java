package modele.affichage.propertyutil;

import javafx.beans.property.StringProperty;

/**
 * Retourne un nouveau binding en ayant mis une majuscule sur la première lettre de la valeur de la property
 * et une minuscule sur les autres.
 * @author Ronan
 *
 */
public class StringMajusculeBinding extends StringBindingFormat {

	private static final FormatableString FORMAT = value -> value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();

	public StringMajusculeBinding(final StringProperty property) {
		super(property, StringMajusculeBinding.FORMAT);
	}

}
