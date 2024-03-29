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
    		Node<String[]> firstNode = new Node<String[]>(new String[]{"-1", "first"});
        	Node<String[]> lastNode = new Node<String[]>(new String[]{"2460", "last"});
    		week[i] = DaysOfWeek.values()[i];
    		LinkedList<String[]> list = week[i].getSchedule();
    		list.setHead(firstNode);
    		firstNode.setNext(lastNode);
    		week[i].setSchedule(list);
    		System.out.println(week[i].getSchedule().size());
    	}
    }
    
    private void manage() {
    	switch (question("Would you like to add an event, remove an event, view events, or quit? [a, r, v, q]", new String[]{"a", "r", "v", "q"})) {
    	case "a":
    		try {
    			add();
    		} catch(SchedulingConflictException e) {
    			e.printStackTrace();
    		}
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
    
    private void add() throws SchedulingConflictException {
    	String event = prompt("What is this event?").toLowerCase();
    	
    	String dayS = question("What day is this event?", new String[]{"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"});
    	
    	int day = 0;
    	
    	switch(dayS) {
    	case "sunday":
    		day = 1;
    		break;
    		
    	case "monday":
    		day = 2;
    		break;
    		
    	case "tuesday":
    		day = 3;
    		break;
    		
    	case "wednesday":
    		day = 4;
    		break;
    		
    	case "thursday":
    		day = 5;
    		break;
    		
    	case "friday":
    		day = 6;
    	}
    	
    	String[] timeS = requestTime("What time is this event at?");
    	
    	while (!validateTime(timeS)) {
    		timeS = requestTime("What time is this event at?");
    	}
    	
    	int time = (Integer.parseInt(timeS[0])*100)+Integer.parseInt(timeS[1]);
    	
    	System.out.println(time);
    	
    	// check to make sure there isn't already one that exists at this time
    	LinkedList<String[]> list = week[day].getSchedule();
		Node<String[]> node = list.getHead();
		for (int i = 0; i < list.size(); i++) {
			int tempTime = (Integer.parseInt(node.getValue()[0]));
			System.out.println(tempTime);
			if (tempTime == time) {
				throw new SchedulingConflictException();
			}
			if (i < list.size()-1) {
				node = node.getNext();
			}
		}
		
		// add it to the list
		node = list.getHead();
		Node<String[]> newNode = new Node<String[]>(new String[] {""+time, event});
		for (int i = 0; i < list.size(); i++) {
			if (Integer.parseInt(node.getValue()[0]) < time && Integer.parseInt(node.getNext().getValue()[0]) > time) {
				System.out.println(day);
				node.getNext().setPrev(newNode);
				newNode.setNext(node.getNext());
				newNode.setPrev(node);
				node.setNext(newNode);
				break;
			}
			if (i < list.size()-1) {
				node = node.getNext();
			}
		}
		week[day].setSchedule(list);
		
		for (int i = 0; i < week.length; i++) {
			System.out.println(week[i].getSchedule().size());
		}
    }
    
    private void remove() {
    	String dayS = question("What day is this event?", new String[]{"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"});
    	
    	int day = 0;
    	
    	switch(dayS) {
    	case "sunday":
    		day = 1;
    		break;
    		
    	case "monday":
    		day = 2;
    		break;
    		
    	case "tuesday":
    		day = 3;
    		break;
    		
    	case "wednesday":
    		day = 4;
    		break;
    		
    	case "thursday":
    		day = 5;
    		break;
    		
    	case "friday":
    		day = 6;
    	}
    	
    	String[] timeS = requestTime("What time is this event at?");
    	
    	while (!validateTime(timeS)) {
    		timeS = requestTime("What time is this event at?");
    	}
    	
    	int time = (Integer.parseInt(timeS[0])*100)+Integer.parseInt(timeS[1]);
    	
    	LinkedList<String[]> list = week[day].getSchedule();
    	Node<String[]> current = list.getHead();
    	
    	for (int i = 0; i < list.size(); i++) {
    		if (Integer.parseInt(current.getValue()[0]) == time) {
    			current.getPrev().setNext(current.getNext());
    			current.getNext().setPrev(current.getPrev());
    			break;
    		}
    		if (i < list.size()-1) {
    			current = current.getNext();
    		}
    	}
    	week[day].setSchedule(list);
    }
    
    private void view() {
    	for (int i = 0; i < 7; i++) {
    		System.out.println("Day " + i);
    		LinkedList<String[]> list = week[i].getSchedule();
    		Node<String[]> current = list.getHead();
    		for (int j = 0; j < list.size(); j++) {
    			System.out.println(current.getValue()[1] + " at " + current.getValue()[0]);
    			if (j < list.size()-1) {
    				current = current.getNext();
    			}
    		}
    	}
    }
}
