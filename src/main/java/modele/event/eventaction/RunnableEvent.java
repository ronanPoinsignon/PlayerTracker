package modele.event.eventaction;

public abstract class RunnableEvent extends EventAction<Void> implements Runnable {

	@Override
	public void run() {
		execute();
	}
}
