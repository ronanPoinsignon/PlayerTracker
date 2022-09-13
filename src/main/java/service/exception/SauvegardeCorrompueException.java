package service.exception;

import java.io.IOException;
import java.nio.file.Files;

import modele.exception.AException;
import service.AlertFxService;
import service.FileManager;
import service.ServiceManager;

public class SauvegardeCorrompueException extends AException {

	private static final long serialVersionUID = 4377631700576701768L;

	private final transient FileManager fm = ServiceManager.getInstance(FileManager.class);

	@Override
	public String getDescription() {
		return dictionnaire.getText("sauvegardeCorrompueExceptionDescription").getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getText("sauvegardeCorrompueExceptionMessage").getValue();
	}

	@Override
	public Runnable next() {
		final var fichier = fm.getOrCreateFile("data.txt");

		return () -> {
			try {
				Files.delete(fichier.toPath());
			} catch (final IOException e) {
				ServiceManager.getInstance(AlertFxService.class).alert(e);
			}
		};
	}
}