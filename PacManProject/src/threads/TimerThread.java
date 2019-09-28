package threads;

public abstract class TimerThread extends ThreadPerso{
	
	private int counterWaits = 0;
	private int nb_waits_max;

	
	public TimerThread(int wait_time, int nb_waits) {
		super("Timer");
		setWaitTime(wait_time); 
		setNbWaitsMax(nb_waits);
	}


	@Override
	protected void doThat() {
		doThatWhileWaiting();
		counterWaits++;
		if(counterWaits >= nb_waits_max) {
			counterWaits = 0;
			finallyDoThat();
		}
	}
	
	/**
	 * Do that more often.
	 */
	protected abstract void doThatWhileWaiting();
	
	@Override
	protected void doThatAtStop() {
		// nothing to do at stop
	}
	
	/**
	 * Set the number of wait until the action defined in finallyDoThat() is called
	 * @param nb_wait_times
	 */
	public synchronized void setNbWaitsMax(int nb_wait_times) {
		nb_waits_max = nb_wait_times;
	}
	
	/**
	 * Method called when the timer waited the number of waits.
	 */
	protected abstract void finallyDoThat();
	


}
