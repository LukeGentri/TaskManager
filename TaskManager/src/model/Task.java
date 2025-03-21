package model;

import java.time.LocalDate;

/**
 * Class for managing Task objects
 * Tasks have an id, name, description, dueDate, and completion status
 * Ids are automatically set (ordered) by TaskList class, everything else is manual
 * @author Luke Gentri
 */
public class Task {
	
	/** Id */
	private int id;
	
	/** Name */
    private String name;
    
    /** Description */
    private String description;
    
    /** Due Date */
    private LocalDate dueDate;
    
    /** Completion status */
    private boolean isCompleted;

    /**
     * Constructor
     * @param id Id
     * @param name Name
     * @param description Description
     * @param dueDate Due Date
     */
    public Task(int id, String name, String description, LocalDate dueDate) {
        setId(id);
    	setName(name);
        setDescription(description);
        setDueDate(dueDate);
        setCompleted(false);
    }
    
    /**
     * Copy Constructor
     * Creates a new Task with same fields as input Task
     * Used by TaskList when creating copy TaskList
     * @param other Task to copy
     */
    public Task(Task other) {
        setId(other.id);
        setName(other.name);
        setDescription(other.description);
        setDueDate(other.dueDate);
        setCompleted(other.isCompleted);
    }
    
    /**
     * getId
     * @return Task Id
     */
    public int getId() {
        return id;
    }

    /**
     * setId
     * @param id Task Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getName
     * @return Task Name
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * @param name Task Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getDescription
     * @return Task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDescription
     * @param description Task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getDueDate
     * @return Task Due Date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * setDueDate
     * @param dueDate Task Due Date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * setCompleted
     * @param isCompleted Task completion status
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    /**
     * isCompleted
     * @return Task completion status
     */
    public boolean isCompleted() {
        return isCompleted;
    }

}