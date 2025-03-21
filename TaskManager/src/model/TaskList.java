package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class for managing a list of Task objects
 * Has methods for adding, removing, removing all, reassigning IDs,
 * getting all, getting completed, getting incomplete, sorting by due date, and sorting by name
 * Used directly by TaskManagerGUI
 * @author Luke Gentri
 */
public class TaskList {
	
	/** Store tasks using List class */
    private List<Task> taskList;

    /**
     * Constructor
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }
    
    /**
     * Copy Constructor
     * Creates a new TaskList with same fields as input TaskList
     * Used by GUI for pushing TaskList to Stack before changing
     * @param other TaskList to copy
     */
    public TaskList(TaskList other) {
        this.taskList = new ArrayList<>();

        for (Task task : other.getAllTasks()) {
            this.taskList.add(new Task(task));
        }
    }

    /**
     * Adds a task to the list
     * Sets id to the lowest available integer starting at 1
     * @param task
     */
    public void addTask(Task task) {
    	
    	// ID 1 by default
        int id = 1;
        
        // Find lowest available ID
        while (getTaskById(id) != null) {
            id++;
        }
        
        // Set ID
        task.setId(id);
        
        // Add to list
        taskList.add(task);
    }
    
    /**
     * Private helper method used by addTask to find lowest available ID
     * @param id ID to check if Task exists with that ID
     * @return null if Task with that ID does not exist
     */
    private Task getTaskById(int id) {
    	
    	// Iterate through TaskList
        for (Task task : taskList) {
        	
        	// If ID found, return task
            if (task.getId() == id) {
                return task;
            }
        }
        
        // If ID available, return null
        return null;
    }

    /**
     * Remove Task from list
     * Reassign IDs after removal to maintain ordering
     * @param task
     */
    public void removeTask(Task task) {
        taskList.remove(task);
        resetIDs();
    }
    
    /**
     * Remove all Tasks from list
     */
    public void clear() {
        taskList.clear();
    }

    /**
     * Reset Task IDs after removal
     */
    private void resetIDs() {
    	
    	// Lowest ID 1
        int id = 1;
        
        // Set Task IDs in increasing order
        for (Task task : taskList) {
            task.setId(id++);
        }
    }

    /**
     * Return Task List
     * Used for GUI display
     * @return taskList All Tasks
     */
    public List<Task> getAllTasks() {
        return taskList;
    }

    /**
     * Get completed tasks
     * Used for GUI display
     * @return new ArrayList of only Tasks marked isCompeleted
     */
    public List<Task> getCompletedTasks() {
    	
    	// Declare empty list
        List<Task> completedTasks = new ArrayList<>();
        
        // Iterate through all Tasks
        for (Task task : taskList) {
        	
        	// If completed, add to new list
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }
        
        // Return new list of completed Tasks
        return completedTasks;
    }

    /**
     * Get incomplete tasks
     * Used for GUI display
     * @return new ArrayList of only Tasks not completed
     */
    public List<Task> getIncompleteTasks() {
    	
    	// Declare empty list
        List<Task> incompleteTasks = new ArrayList<>();
        
     // Iterate through all Tasks
        for (Task task : taskList) {
        	
        	// If incomplete, add to new list
            if (!task.isCompleted()) {
                incompleteTasks.add(task);
            }
        }
        
        // Return new list of incomplete Tasks
        return incompleteTasks;
    }
    
    /**
     * Sort Tasks by due date using Collections class
     * Used for GUI display
     * @return new ArrayList of Tasks in natural order of due date
     */
    public void sortByDueDate() {
    	
    	// Collections sort method for Comparator
        Collections.sort(taskList, new Comparator<Task>() {
        	
        	// Override Compare to compare due dates
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getDueDate().compareTo(t2.getDueDate());
            }
        });
        
        // Update IDs for display after ordering
        resetIDs();
    }
    
    /**
     * Sort Tasks by name using Collections class
     * Used for GUI display
     * @return new ArrayList of Tasks in natural order of name
     */
    public void sortByName() {
    	
    	// Collections sort method for Comparator
        Collections.sort(taskList, new Comparator<Task>() {
        	
        	// Override Compare to compare names
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getName().compareTo(t2.getName());
            }
        });
        
        // Update IDs for display after ordering
        resetIDs();
    }
    
    public int size() {
    	return taskList.size();
    }
}