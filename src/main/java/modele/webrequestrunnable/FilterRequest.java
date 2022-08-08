package modele.webrequestrunnable;

import java.util.List;

import modele.request.data.player.PlayerRequest;

public interface FilterRequest {

	boolean accept(List<? extends PlayerRequest> joueurs);

}
