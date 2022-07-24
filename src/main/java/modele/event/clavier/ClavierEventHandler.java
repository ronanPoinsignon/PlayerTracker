package modele.event.clavier;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import modele.event.eventaction.AddEvent;
import modele.event.eventaction.AnnulerEvent;
import modele.event.eventaction.CollerEvent;
import modele.event.eventaction.DeleteEvent;
import modele.event.eventaction.GetPseudoEvent;
import modele.event.eventaction.ReexecuterEvent;
import modele.event.eventaction.SwapDownEvent;
import modele.event.eventaction.SwapUpEvent;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;

/**
 * Classe traitant la récéption d'événement clavier.
 * @author ronan
 *
 */
public class ClavierEventHandler implements EventHandler<KeyEvent> {

	private static final KeyCombination COPIER = new KeyCodeCombination(KeyCodeCombinationEnum.COPIER.getKeyCode(), KeyCodeCombinationEnum.COPIER.getModifier());
	private static final KeyCombination COLLER = new KeyCodeCombination(KeyCodeCombinationEnum.COLLER.getKeyCode(), KeyCodeCombinationEnum.COLLER.getModifier());
	private static final KeyCombination SUPPRIMER = new KeyCodeCombination(KeyCodeCombinationEnum.SUPPRIMER.getKeyCode(), KeyCodeCombinationEnum.SUPPRIMER.getModifier());
	private static final KeyCombination INVERSER_HAUT = new KeyCodeCombination(KeyCodeCombinationEnum.INVERSER_HAUT.getKeyCode(), KeyCodeCombinationEnum.INVERSER_HAUT.getModifier());
	private static final KeyCombination INVERSER_BAS = new KeyCodeCombination(KeyCodeCombinationEnum.INVERSER_BAS.getKeyCode(), KeyCodeCombinationEnum.INVERSER_BAS.getModifier());
	private static final KeyCombination ANNULER_ACTION = new KeyCodeCombination(KeyCodeCombinationEnum.ANNULER_ACTION.getKeyCode(), KeyCodeCombinationEnum.ANNULER_ACTION.getModifier());
	private static final KeyCombination REEXECUTER_ACTION = new KeyCodeCombination(KeyCodeCombinationEnum.REEXECUTER_ACTION.getKeyCode(), KeyCodeCombinationEnum.REEXECUTER_ACTION.getModifier());

	private final TableView<JoueurFx> table;

	private final Map<KeyCombination, Runnable> keyFunctionMap = new HashMap<>();

	public ClavierEventHandler(final TableView<JoueurFx> table) {
		this.table = table;
		keyFunctionMap.put(ClavierEventHandler.COPIER, this::copier);
		keyFunctionMap.put(ClavierEventHandler.COLLER, this::coller);
		keyFunctionMap.put(ClavierEventHandler.SUPPRIMER, this::supprimer);
		keyFunctionMap.put(ClavierEventHandler.INVERSER_HAUT, this::swapUp);
		keyFunctionMap.put(ClavierEventHandler.INVERSER_BAS, this::swapDown);
		keyFunctionMap.put(ClavierEventHandler.ANNULER_ACTION, this::annuler);
		keyFunctionMap.put(ClavierEventHandler.REEXECUTER_ACTION, this::reexecuter);
	}

	@Override
	public void handle(final KeyEvent event) {
		if(KeyCode.ALT.equals(event.getCode())) {
			event.consume();
			return;
		}
		keyFunctionMap.entrySet()
		.stream()
		.filter(entry -> entry.getKey().match(event))
		.findFirst()
		.ifPresent(entry -> {
			entry.getValue().run();
		});
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

	public void annuler() {
		final var th = new Thread(new AnnulerEvent());
		th.setDaemon(true);
		th.start();
	}

	public void reexecuter() {
		final var th = new Thread(new ReexecuterEvent());
		th.setDaemon(true);
		th.start();
	}

	public void addJoueurFxToTable(final String nom, final String pseudo, final Serveur serveur) {
		final var th = new Thread(new AddEvent(table, nom, pseudo, serveur));
		th.setDaemon(true);
		th.start();
	}

}