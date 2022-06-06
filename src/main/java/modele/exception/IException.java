package modele.exception;

public interface IException {

	String getMessage();
	String getDescription();
	default Runnable next() {
		return () -> {

		};
	}
}