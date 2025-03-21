package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the construction of Task objects
 * @author Luke Gentri
 */
class TaskTest {
	
	/** dueDate field */
	LocalDate dueDate;
	
	/** task field */
	Task task;

	/** 
	 * Set up task with due date 
	 */
	@BeforeEach
	void setUp() {
		dueDate = LocalDate.of(2026, 3, 6);
		task = new Task(1, "task1", "short description", dueDate);
	} 

	/**
	 * Test constructor correctly sets fields
	 */
	@Test
	void testConstructor() {
		assertEquals(1, task.getId());
		assertEquals("task1", task.getName());
		assertEquals("short description", task.getDescription());
		assertEquals(dueDate, task.getDueDate());
		assertEquals(false, task.isCompleted());
	}
	
	/**
	 * Test copy constructor correctly sets fields
	 */
	@Test
	void testCopyConstructor() {
		Task task2 = new Task(task);
		assertEquals(task2.getId(), task.getId());
		assertEquals(task2.getName(), task.getName());
		assertEquals(task2.getDescription(), task.getDescription());
		assertEquals(task2.getDueDate(), task.getDueDate());
	}

}
