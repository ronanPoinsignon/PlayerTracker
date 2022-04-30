package modele.observer;

import modele.request.data.SummonerData;
import modele.request.data.SummonerInGame;

public interface ObservateurWeb {

	void notifyData(SummonerData data);
	void notifyData(SummonerInGame data);

}
