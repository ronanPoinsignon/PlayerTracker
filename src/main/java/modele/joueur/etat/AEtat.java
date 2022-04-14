package modele.joueur.etat;

import service.FileManager;
import service.ServiceManager;

public abstract class AEtat implements IEtat {

	protected FileManager fm = ServiceManager.getInstance(FileManager.class);

}
