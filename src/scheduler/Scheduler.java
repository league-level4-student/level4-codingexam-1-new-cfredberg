package scheduler;

import java.util.Scanner;

/*
 * Objective: Create a weekly scheduling application.
 * 
 * You may create any additional enums, classes, methods or variables needed
 * to accomplish the requirements below:
 * 
 * - You should use an array filled with enums for the days of the week and each
 *   enum should contain a LinkedList of events that includes a time and what is 
 *   happening at the event.
 * 
 * - The user should be able to to interact with your application through the
 *   console and have the option to add events, view events or remove events by
 *   day.
 *   
 * - Each day's events should be sorted by chronological order.
 *  
 * - If the user tries to add an event on the same day and time as another event
 *   throw a SchedulingConflictException(created by you) that tells the user
 *   they tried to double book a time slot.
 *   
 * - Make sure any enums or classes you create have properly encapsulated member
 *   variables.
 */
public class Scheduler {
    public static void main(String[] args) {
    	Scheduler scheduler = new Scheduler();
    	scheduler.init();
    	scheduler.manage();
    }
    
    private Scanner scanner = new Scanner(System.in);
    
    DaysOfWeek[] week = new DaysOfWeek[7];
    
    private void init() {
    	for (int i = 0; i < DaysOfWeek.values().length; i++) {
    		week[i] = DaysOfWeek.values()[i];
    	}
    }
    
    private void manage() {
    	switch (question("Would you like to add an event, remove an event, view events, or quit? [a, r, v, q]", new String[]{"a", "r", "v", "q"})) {
    	case "a":
    		add();
    		System.out.println("out");
    		break;
    	case "r":
    		remove();
    		break;
    	case "v":
    		view();
    		break;
    	case "q":
    		System.exit(0);
    	}
    	manage();
    }
    
    private String question(String question, String[] options) {
    	String answer = prompt(question).toLowerCase();
    	for (String ans : options) {
    		if (answer.equals(ans)) {
    			return ans;
    		}
    	}
    	return question(question, options);
    }
    
    private String prompt(String question) {
    	System.out.println(question);
    	return scanner.nextLine();
    }
    
    private String[] requestTime(String question) {
    	String answer = prompt(question);
    	// hm: hours minutes
    	String[] hm = answer.split(":");
    	
    	if (hm.length != 2) {
    		System.out.println("Invalid Time");
    		return requestTime(question);
    	}
    	
    	return hm;
    }
    
    private boolean validateTime(String[] time) {
    	//true: time is good
    	//false: invalid time
    	int hour = Integer.parseInt(time[0]);
    	int min = Integer.parseInt(time[1]);
    	if (hour >= 0 && hour <= 24 && min >= 0 && min < 60) {
    		return true;
    	}
    	System.out.println("Invalid Time");
    	return false;
    }
    
    private void add() {
    	String event = prompt("What is this event?");
    	
    	String dayS = question("What day is this event?", new String[]{"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"});
    	
    	DaysOfWeek day = DaysOfWeek.SATURDAY;
    	
    	switch(dayS) {
    	case "sunday":
    		day = DaysOfWeek.SUNDAY;
    		break;
    		
    	case "monday":
    		day = DaysOfWeek.MONDAY;
    		break;
    		
    	case "tuesday":
    		day = DaysOfWeek.TUESDAY;
    		break;
    		
    	case "wednesday":
    		day = DaysOfWeek.WEDNESDAY;
    		break;
    		
    	case "thursday":
    		day = DaysOfWeek.THURSDAY;
    		break;
    		
    	case "friday":
    		day = DaysOfWeek.FRIDAY;
    	}
    	
    	String[] timeS = requestTime("What time is this event at?");
    	
    	while (!validateTime(timeS)) {
    		timeS = requestTime("What time is this event at?");
    	}
    	
    	int time = (Integer.parseInt(timeS[0])*100)+Integer.parseInt(timeS[1]);
    	
    	System.out.println(time);
    	
    	try {
    		//-----------------------------------------------
    		
    		if (timeS == null) {
    			throw new SchedulingConflictException();
    		}
    	}catch(SchedulingConflictException e) {
    		e.printStackTrace();
    	}
    }
    
    private void remove() {
    	
    }
    
    private void view() {
    	
    }
}
