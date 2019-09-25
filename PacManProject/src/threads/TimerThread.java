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
	public void doThatAtStart() {
		// nothing to do at start
	}


	@Override
	public void doThat() {
		counterWaits++;
		if(counterWaits >= nb_waits_max) {
			counterWaits = 0;
			finallyDoThat();
		}
	}
	
	@Override
	public void doThatAtStop() {
		// nothing to do at stop
	}
	
	/**
	 * Set the number of wait until the action defined in finallyDoThat() is called
	 * @param nb_wait_times
	 */
	public void setNbWaitsMax(int nb_wait_times) {
		nb_waits_max = nb_wait_times;
	}
	
	/**
	 * Method called when the timer waited the number of waits.
	 */
	public abstract void finallyDoThat();
	


}
