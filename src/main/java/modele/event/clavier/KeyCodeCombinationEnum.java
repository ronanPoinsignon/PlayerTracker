package modele.event.clavier;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;

public enum KeyCodeCombinationEnum {

	COPIER(KeyCode.C, KeyCombination.CONTROL_DOWN),
	COLLER(KeyCode.V, KeyCombination.CONTROL_DOWN),
	SUPPRIMER(KeyCode.DELETE, KeyCombination.META_ANY),
	INVERSER_HAUT(KeyCode.UP, KeyCombination.ALT_DOWN),
	INVERSER_BAS(KeyCode.DOWN, KeyCombination.ALT_DOWN),
	ANNULER_ACTION(KeyCode.Z, KeyCombination.CONTROL_DOWN),
	REEXECUTER_ACTION(KeyCode.Y, KeyCombination.CONTROL_DOWN);

	private KeyCode key;
	private Modifier modifier;

	KeyCodeCombinationEnum(final KeyCode key, final Modifier modifier) {
		this.key = key;
		this.modifier = modifier;
	}

	public KeyCode getKeyCode() {
		return key;
	}

	public Modifier getModifier() {
		return modifier;
	}
}
