package service;

public interface IService {

	/**
	 * Méthode d'initialisation appelée après la création du service.
	 * Permet d'éviter la création circulaire des services lors de dépendances mutuelles.
	 *
	 */
	default void init() {

	}

}