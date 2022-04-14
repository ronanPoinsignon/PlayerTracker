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

	private KeyCombination copier = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_COPIER, ClavierEventHandler.MODIFIER_COPIER);
	private KeyCombination coller = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_COLLER, ClavierEventHandler.MODIFIER_COLLER);
	private KeyCombination supprimer = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_SUPPRIMER, ClavierEventHandler.MODIFIER_SUPPRIMER);
	private KeyCombination inverserHaut = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_INVERSER_HAUT, ClavierEventHandler.MODIFIER_INVERSER_HAUT);
	private KeyCombination inverserBas = new KeyCodeCombination(ClavierEventHandler.KEY_CODE_INVERSER_BAS, ClavierEventHandler.MODIFIER_INVERSER_BAS);

	private TableView<JoueurFx> table;

	public ClavierEventHandler(TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public void handle(KeyEvent event) {
		System.out.println("oui");
		if(event.getCode().equals(KeyCode.ALT)) {
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
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		try {
			content.putString(getSelectedLink());
			clipboard.setContent(content);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			// le copier collé rate, pas grave
		}
	}

	/**
	 * Colle le contenu du presse-papiers dans la table afin d'essayer d'en ajouter le contenu.
	 */
	public void coller() {
		new CollerEvent(table);
	}

	/**
	 * Supprime une ligne de la table.
	 */
	public void supprimer() {
		Thread th = new Thread(new DeleteEvent(table));
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
		Thread th = new Thread(new SwapUpEvent(table));
		th.setDaemon(true);
		th.start();
	}

	/**
	 * Inverse la ligne séléctionnée avec celle du bas.
	 */
	public void swapDown() {
		Thread th = new Thread(new SwapDownEvent(table));
		th.setDaemon(true);
		th.start();
	}

	public void addJoueurFxToTable(String nom, String pseudo) {
		Thread th = new Thread(new AddEvent(table, nom, pseudo));
		th.setDaemon(true);
		th.start();
	}

}
