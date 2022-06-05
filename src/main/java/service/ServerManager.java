package service;

import java.util.ArrayList;
import java.util.List;

import modele.joueur.Serveur;

public class ServerManager implements IService {

	private PropertiesService ps;

	private final String[][] serverNames = { { "br1", "BR" }, { "eun1", "EUNE" }, { "euw1", "EUW" }, { "j1", "JP" }, { "kr", "KR" }, { "la1", "LAN" }, { "la2", "LAS"}, { "na1", "NA" }, { "oc1", "OCE" }, { "ru", "RU" }, { "tr1", "TR" } };

	List<Serveur> servers = new ArrayList<>();

	public ServerManager() {
		for(var i = 0; i < serverNames.length; i++) {
			servers.add(new Serveur(serverNames[i][0], serverNames[i][1]));
		}
	}

	public List<Serveur> getServers() {
		return servers;
	}

	public Serveur getDefaultServer() {
		final var defaultName = ps.get("default_server");
		return servers.stream()
				.filter(server -> server.getServerId().equals(defaultName))
				.findFirst()
				.orElse(getServerByName("euw1"));
	}

	public Serveur getServerByName(final String name) {
		return servers.stream()
				.filter(server -> server.getServerId().equals(name))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void init() {
		ps = ServiceManager.getInstance(PropertiesService.class);
	}
}
