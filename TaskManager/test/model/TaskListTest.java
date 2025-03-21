package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the creation and manipulation of TaskList objects
 * @author Luke Gentri
 */
class TaskListTest {
	
	/** taskList for testing */
	TaskList taskList;
	
	/** due dates for different tasks */
	LocalDate dueDate;
	LocalDate dueDate2;
	
	/** task fields */
	Task task;
	Task task2;

	/**
	 * Create a taskList with two tasks
	 */
	@BeforeEach
	void setUp() {
		taskList = new TaskList();
		dueDate = LocalDate.of(2026, 3, 6);
		dueDate2 = LocalDate.of(2026, 5, 8);
		task = new Task(1, "task1", "short description", dueDate);
		task2 = new Task(-12, "task2", "", dueDate2);
	} 
	
	/**
	 * Test that constructor creates taskList and that adding tasks updates the list
	 * Test that copy constructor copies all tasks to new list
	 */
	@Test
	void testConstructorsAddTaskGetTaskById() {
		assertEquals(0, taskList.size());

		taskList.addTask(task);
		assertEquals(1, taskList.size());

		taskList.addTask(task2);
		assertEquals(2, taskList.size());
	
		TaskList taskList2 = new TaskList(taskList);
		assertEquals(2, taskList2.size());
	}
	
	/**
	 * Test removing a task from the list
	 * Test that clearing removes all tasks
	 * Test that IDs are reassigned when a task is removed
	 */
	@Test
	void testRemoveTaskClearResetIDs() {
		taskList.addTask(task);
		taskList.addTask(task2);
		assertEquals(2, taskList.size());
	
		taskList.removeTask(task);
		assertEquals(1, taskList.size());
		assertEquals(1, task2.getId());
		taskList.addTask(task);
		taskList.clear();
		assertEquals(0, taskList.size());
	}
	
	/**
	 * Test that completion lists are returned correctly
	 */
	@Test
	void testGetCompletedTasksGetIncompleteTasksGetAllTasks() {
		taskList.addTask(task);
		taskList.addTask(task2);
		
		task2.setCompleted(true);
		assertEquals(1, taskList.getCompletedTasks().size());
		assertEquals(1, taskList.getIncompleteTasks().size());
		assertEquals(2, taskList.getAllTasks().size());
	}
	
	/**
	 * Test that sortByName sorts correctly
	 */
	@Test
	void testSortByName() {
		taskList.addTask(task2);
		taskList.addTask(task);
		
		assertEquals(1, task2.getId());
		assertEquals(2, task.getId());
		
		taskList.sortByName();
		
		assertEquals(2, task2.getId());
		assertEquals(1, task.getId());
	}
	
	/**
	 * Test that sortByDueDate sorts correctly
	 */
	@Test
	void testSortByDueDate() {
		taskList.addTask(task2);
		taskList.addTask(task);
		
		assertEquals(1, task2.getId());
		assertEquals(2, task.getId());
		
		taskList.sortByDueDate();
		
		assertEquals(2, task2.getId());
		assertEquals(1, task.getId());
	}

}
