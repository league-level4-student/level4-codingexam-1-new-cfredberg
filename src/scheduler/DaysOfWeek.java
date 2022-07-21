package scheduler;

public enum DaysOfWeek {
	SATURDAY, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;
	
	private LinkedList<String[]> schedule = new LinkedList<String[]>();
	
	public LinkedList<String[]> getSchedule() {
		return schedule;
	}
	
	public void setSchedule(LinkedList<String[]> schedule) {
		this.schedule = schedule;
	}
}
