package octos.scrummer;

/**
 * Responsible for counting time and triggering UI updates. 
 */
class Counter implements Runnable {
	
	private static final Integer TIME_LIMIT = 60;
	
	private ScrummerActivity context;
	
	private boolean stop = false;

	public Counter(ScrummerActivity context) {
		this.context = context;
	}

	@Override
	public void run() {
		for (int i = 0; i <= TIME_LIMIT && !isStop(); i++) {
			context.updateTime(formatTime(i));
			sleep(1000);
		}
		if (!isStop()) {
			context.counterFinishedHandler();
		}
	}
	
	public synchronized boolean isStop() {
		return stop;
	}

	public synchronized void stop() {
		stop = true;
	}

	private void sleep(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String formatTime(int i) {
		String minutes = Integer.valueOf(i / 60).toString();
		String seconds = Integer.valueOf(i % 60).toString();
		seconds = seconds.length() == 1 ? "0" + seconds : seconds;
		return minutes + ":" + seconds;
	}
}