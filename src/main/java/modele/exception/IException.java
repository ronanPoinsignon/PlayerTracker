package modele.exception;

public interface IException {

	String getMessageError();
	String getDescription();
	default Runnable next() {
		return () -> {

		};
	}
}