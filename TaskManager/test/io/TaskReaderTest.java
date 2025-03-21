package io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Task;
import model.TaskList;

/**
 * Class for testing loading lists from and saving lists to files using TaskReader
 * @author Luke Gentri
 */
class TaskReaderTest {
	
	/** taskReader for testing */
    private TaskReader taskReader;
    
    /** taskList for testing */
    private TaskList taskList;
    
    /** declare file types for testing */
    private File validFile;
    private File invalidFile;
    private File emptyFile;
    private File saveFile;

    /**
     * Initialize testing files
     * @throws IOException for file creation, should not happen
     */
    @BeforeEach
    void setUp() throws IOException {
    	
        taskReader = new TaskReader();

        validFile = new File("validTasks.csv");
        invalidFile = new File("invalidTasks.csv");
        emptyFile = new File("emptyTasks.csv");
        saveFile = new File("saveTasks.csv");

        taskList = new TaskList();
        taskList.addTask(new Task(1, "Task 1", "Description 1", LocalDate.of(2026, 3, 6)));
        taskList.addTask(new Task(2, "Task 2", "Description 2", LocalDate.of(2026, 4, 10)));

        emptyFile.createNewFile();
        validFile.createNewFile();
        taskReader.saveToFile(taskList, validFile);
    }

    /**
     * Test loading valid files populates taskList
     * @throws DataFormatException if date is invalid
     * @throws FileNotFoundException if file not found
     */
    @Test
    void testLoadValidFile() throws DataFormatException, FileNotFoundException {
    	
        TaskList loaded = taskReader.loadFromFile(validFile);

        assertNotNull(loaded);
        assertEquals(2, loaded.getAllTasks().size());

        Task task1 = loaded.getAllTasks().get(0);
        assertEquals("Task 1", task1.getName());
        assertEquals("Description 1", task1.getDescription());
        assertEquals(LocalDate.of(2026, 3, 6), task1.getDueDate());
    }

    /**
     * Test invalid file with missing fields throws exception
     * Uses FileWriter to write invalid testing file
     * @throws IOException if file cannot be created
     */
    @Test
    void testLoadInvalidFileMissingFields() throws IOException {
    	
        invalidFile.createNewFile();
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("Task 1,Description 1\n");
        }
        
        assertThrows(DataFormatException.class, () -> taskReader.loadFromFile(invalidFile), "Invalid file format");
    }
    
    /**
     * Test invalid file with extra fields throws exception
     * Uses FileWriter to write invalid testing file
     * @throws IOException if file cannot be created
     */
    @Test
    void testLoadInvalidFileExtraFields() throws IOException {
    	
        invalidFile.createNewFile();
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("Task 1,Description 1,2026-03-32,extra\n");
        }
        
        assertThrows(DataFormatException.class, () -> taskReader.loadFromFile(invalidFile), "Invalid file format");
    }
    
    /**
     * Test invalid file with date format error throws exception
     * Uses FileWriter to write invalid testing file
     * @throws IOException if file cannot be created
     */
    @Test
    void testLoadInvalidFileDateFormat() throws IOException {
    	
        invalidFile.createNewFile();
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("Task 1,Description 1,03-32-2026\n");
        }
        
        assertThrows(DataFormatException.class, () -> taskReader.loadFromFile(invalidFile), "Invalid file format");
    }
    
    /**
     * Test empty file throws exception
     */
    @Test
    void testLoadEmptyFile() {
    	
        assertThrows(DataFormatException.class, () -> taskReader.loadFromFile(emptyFile), "Empty file");
    }

    /**
     * Test saving to a file (taskList assumed valid)
     * Ensures formatting of saved tasks is correct
     */
    @Test
    void testSaveToFile() throws IOException {
    	
        taskReader.saveToFile(taskList, saveFile);

        assertTrue(saveFile.exists());

        Scanner scanner = new Scanner(saveFile);

        assertEquals("Task 1,Description 1,2026-03-06", scanner.nextLine());
        assertEquals("Task 2,Description 2,2026-04-10", scanner.nextLine());
        
        scanner.close();
    }
}
