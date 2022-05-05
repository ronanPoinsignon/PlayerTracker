package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;

public class LoadService implements IService {
	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	private AlertFxService alertService = ServiceManager.getInstance(AlertFxService.class);
	
	public List<Joueur> load() {
		File fichier = null;
		List<Joueur> joueurs = new ArrayList<>();

		//fichier = fm.getFileFromResources("joueurs.txt");
		fichier = new File("joueurs.txt");
		try {
			fichier.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
			Joueur joueur = null;
			int size = ois.readInt();
			
			for(int i = 1; i <= size; i++) {
				joueur = (Joueur) ois.readObject();
				joueurs.add(joueur);
			}
			
			return joueurs;
			
		} catch (IOException|ClassNotFoundException e) {
			alertService.alert(e);
			return new ArrayList<>();
		}
	}
}
