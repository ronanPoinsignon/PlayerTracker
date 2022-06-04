package modele.event.clavier;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.input.KeyEvent;
import modele.event.eventaction.AddEvent;
import modele.event.eventaction.CollerEvent;
import modele.event.eventaction.DeleteEvent;
import modele.event.eventaction.GetPseudoEvent;
import modele.event.eventaction.SwapDownEvent;
import modele.event.eventaction.SwapUpEvent;
import modele.joueur.JoueurFx;

/**
 * Classe traitant la récéption d'événement clavier.
 * @author ronan
 *
 */
public class ClavierEventHandler implements EventHandler<KeyEvent> {

	public static final KeyCode KEY_CODE_COPIER = KeyCode.C,
			KEY_CODE_COLLER = KeyCode.V,
			KEY_CODE_SUPPRIMER = KeyCode.DELETE,
			KEY_CODE_INVERSER_HAUT = KeyCode.UP,
			KEY_CODE_INVERSER_BAS = KeyCode.DOWN;

	public static final Modifier MODIFIER_COPIER = KeyCombination.CONTROL_DOWN,
			MODIFIER_COLLER = KeyCombination.CONTROL_DOWN,
			MODIFIER_SUPPRIMER = KeyCombination.META_ANY,
			MODIFIER_INVERSER_HAUT = KeyCombination.ALT_DOWN,
			MODIFIER_INVERSER_BAS = KeyCombination.ALT_DOWN;

	private final KeyCombination copier = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_COPIER, ClavierEventHandler.MODIFIER_COPIER);
	private final KeyCombination coller = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_COLLER, ClavierEventHandler.MODIFIER_COLLER);
	private final KeyCombination supprimer = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_SUPPRIMER, ClavierEventHandler.MODIFIER_SUPPRIMER);
	private final KeyCombination inverserHaut = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_INVERSER_HAUT, ClavierEventHandler.MODIFIER_INVERSER_HAUT);
	private final KeyCombination inverserBas = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_INVERSER_BAS, ClavierEventHandler.MODIFIER_INVERSER_BAS);

	private final TableView<JoueurFx> table;

	public ClavierEventHandler(final TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public void handle(final KeyEvent event) {
		if(event.getEventType() != KeyEvent.KEY_PRESSED) {
			return;
		}
		if(KeyCode.ALT.equals(event.getCode())) {
			event.consume();
			return;
		}

		if(copier.match(event)) {
			copier();
			event.consume();
			return;
		}

		if(coller.match(event)) {
			coller();
			event.consume();
			return;
		}

		if(supprimer.match(event)) {
			supprimer();
			event.consume();
			return;
		}

		if(inverserHaut.match(event)) {
			swapUp();
			event.consume();
			return;
		}

		if(inverserBas.match(event)) {
			swapDown();
			event.consume();
		}
	}

	/**
	 * Copie la ligne séléctionnée de la table dans le presse-papiers.
	 */
	public void copier() {
		final var clipboard = Clipboard.getSystemClipboard();
		final var content = new ClipboardContent();
		try {
			content.putString(getSelectedLink());
			clipboard.setContent(content);
		}
		catch(final ArrayIndexOutOfBoundsException e) {
			// le copier collé rate, pas grave
		}
	}

	/**
	 * Colle le contenu du presse-papiers dans la table afin d'essayer d'en ajouter le contenu.
	 */
	public void coller() {
		new CollerEvent(table).execute();
	}

	/**
	 * Supprime une ligne de la table.
	 */
	public void supprimer() {
		final var th = new Thread(new DeleteEvent(table));
		th.setDaemon(true);
		th.start();
	}


	/**
	 * Récupère le lien de la ligne séléctionnée dans la table.
	 * @return
	 */
	public String getSelectedLink() {
		return new GetPseudoEvent(table).execute();
	}

	/**
	 * Inverse la ligne séléctionnée avec celle du haut.
	 */
	public void swapUp() {
		final var th = new Thread(new SwapUpEvent<>(table));
		th.setDaemon(true);
		th.start();
	}

	/**
	 * Inverse la ligne séléctionnée avec celle du bas.
	 */
	public void swapDown() {
		final var th = new Thread(new SwapDownEvent<>(table));
		th.setDaemon(true);
		th.start();
	}

	public void addJoueurFxToTable(final String nom, final String pseudo) {
		final var th = new Thread(new AddEvent(table, nom, pseudo));
		th.setDaemon(true);
		th.start();
	}

}