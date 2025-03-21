package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.DataFormatException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.Task;
import model.TaskList;

/**
 * Class for reading and writing TaskList objects in CSV format
 * Built in error checking when reading Tasks from files
 * Outputs TaskLists in valid CSV format
 * @author Luke Gentri
 */
public class TaskReader {
	
	/**
	 * Loads a TaskList from a valid file
	 * Files must have a Task object on each line in the following format: (name),(description),(YYYY-MM-DD)
	 * Description may be empty
	 * Name and description each have a maximum length of 50
	 * @param file File specified by GUI for loading
	 * @return new TaskList with Tasks from specified file for use in GUI
	 * @throws DataFormatException if file is empty, sent to GUI for error messaging
	 * @throws FileNotFoundException if any Task does not match the format specified above, sent to GUI for error messaging
	 * @throws DateTimeParseException if the Due Date format is invalid, sent to GUI for error messaging
	 */
	public TaskList loadFromFile(File file) throws DataFormatException, FileNotFoundException, DateTimeParseException {
		
		// Declare empty list
		TaskList list = new TaskList();
		
		// Initialize file scanner
		Scanner scanner = new Scanner(file);
		
		// Check for empty file, throw to GUI
		if (file.length() == 0) {
			scanner.close();
			throw new DataFormatException("Empty file");
		}
		
		// Iterate through file line by line
		while (scanner.hasNextLine()) {
			
			// Separate CSV line into name, date, and description strings
			String[] line = scanner.nextLine().split(",");
			
			// Check for valid fields
			if (line.length != 3 || line[0].isEmpty() || line[0].length() >= 50 || line[1].length() >= 50 || line[2].isEmpty()) {
				scanner.close();
				throw new DataFormatException("Invalid file format");
			}
			
			// Set each field as its own variable for Task creation
			String name = line[0];
			String description = line[1];
			String date = line[2];
			
			// Convert String date to LocalDate object
			LocalDate dueDate;
			
			try {
			
			dueDate = LocalDate.parse(date);
			
			} catch (DateTimeParseException e) {
				scanner.close();
				throw new DataFormatException("Invalid file format");
			}
			
			// Create Task object
			Task task = new Task(0, name, description, dueDate);
			
			// Add Task to list
			list.addTask(task);
			
		}
		
		// Close file after reading
		scanner.close();
		
		// Return new TaskList with Tasks from file
		return list;
		
	}
	
	/**
	 * Saves current TaskList to file in valid format
	 * @param taskList current TaskList
	 * @param file File to save to
	 * @throws IOException Won't be thrown 
	 */
	public void saveToFile(TaskList taskList, File file) throws IOException {
		
		// Initialize file writer
		FileWriter writer = new FileWriter(file);
		
		// Iterate through TaskList
		for (Task task : taskList.getAllTasks()) {
			
			// Write each object to file in specified format
			writer.write(task.getName() + "," + task.getDescription() + "," + task.getDueDate() + "\n");
		}
		
		// Close file after writing
		writer.close();
	}

}
