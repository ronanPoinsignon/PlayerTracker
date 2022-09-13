package service;

import java.util.ArrayList;
import java.util.List;

import modele.localization.Langage;

public class LangagesManager implements IService {
	private PropertiesService ps;
	
	private List<Langage> langages;
		
	@Override
	public void init() {
		ps = ServiceManager.getInstance(PropertiesService.class);
		
		langages = new ArrayList<>();
		
		langages.add(new Langage("Français", "fr_FR"));
		langages.add(new Langage("English", "en_EN"));
		langages.add(new Langage("Deutsch", "de_DE"));
	}
	
	public List<Langage> getLangages() {
		return langages;
	}
	
	public Langage getLangage(String fileName) {
		return langages.stream()
				.filter(langage -> langage.getFileName().equals(fileName))
				.findFirst()
				.orElse(null);
	}
	
	public Langage getDefaultLangage() {
		final var file_name = ps.get("default_language");
		
		return langages.stream()
				.filter(langage -> langage.getFileName().equals(file_name))
				.findFirst()
				.orElse(getLangage("fr_FR"));
	}
}
