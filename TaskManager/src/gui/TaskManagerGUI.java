package gui;

import model.Task;
import model.TaskList;

import javax.swing.*;

import io.TaskReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Stack;
import java.util.zip.DataFormatException;

/**
 * GUI for managing TaskList 
 * Displays TaskList in center of window
 * Has private helper methods for performing operations on the TaskList/display
 * Has a file button top-left for loading/saving TaskList to file
 * Has the following buttons at the bottom:
 * Add Task, Remove Task, Mark Completed, Clear List, Sort By Due Date, Sort by Name (each of these operations can be undone)
 * Show Completed, Show Incomplete, Show All Tasks, Undo (these operations cannot be directly undone)
 */
@SuppressWarnings("serial")
public class TaskManagerGUI extends JFrame {
	
	/** TaskList */
    private TaskList taskList;
    
    /** List model */
    private DefaultListModel<Task> listModel;
    
    /** JList */
    private JList<Task> taskJList;
    
    /** Task Name */
    private JTextField taskName;
    
    /** Task Description */
    private JTextField taskDescription;
    
    /** Task Due Date */
    private JTextField taskDueDate;
    
    /** Stack for storing/restoring Task List */
    private Stack<TaskList> undoStack;

    /**
     * Constructor
     */
    public TaskManagerGUI() {
    	
    	// Initialize TaskList
        taskList = new TaskList();
        
        // Initialize Stack
        undoStack = new Stack<>();
        
        // Initialize UI
        initializeUI();
    }

    /**
     * Main functionality for UI 
     * Specifies layout
     * Calls methods for updating UI
     */
    private void initializeUI() {
    	
    	// Title of application
        setTitle("Task Manager");
        
        // Medium-sized window
        setSize(800, 600);
        
        // Default closing operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Default location
        setLocationRelativeTo(null);
        
        // Default list model
        listModel = new DefaultListModel<>();
        
        // Single selection JList
        taskJList = new JList<>(listModel);
        taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskJList.setCellRenderer(new TaskRenderer());
        taskJList.setFixedCellHeight(80); // Set click-able area for selecting tasks
        
        // Scroll pane
        JScrollPane listScrollPane = new JScrollPane(taskJList);

        // Menu Bar for file IO
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Add "File" button to Menu Bar
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Add "Load Tasks" option within "File" button
        JMenuItem loadMenuItem = new JMenuItem("Load Tasks");
        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTasks(); // Private helper method for operation
            }
        });
        fileMenu.add(loadMenuItem);

        // Add "Save Tasks" option within "File" button
        JMenuItem saveMenuItem = new JMenuItem("Save Tasks");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTasks(); // Private helper method for operation
            }
        });
        fileMenu.add(saveMenuItem);

        // Set up input area for adding Tasks
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 4));

        // Task Name text field
        inputPanel.add(new JLabel("Task Name:"));
        taskName = new JTextField();
        inputPanel.add(taskName);

        // Task Description text field
        inputPanel.add(new JLabel("Task Description:"));
        taskDescription = new JTextField();
        inputPanel.add(taskDescription);

        // Task Due Date text field
        inputPanel.add(new JLabel("Task Due Date (YYYY-MM-DD):"));
        taskDueDate = new JTextField();
        inputPanel.add(taskDueDate);

        // Task Name button
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	saveState(); // Save list on Stack before operation (in case of undo)
                addTask(); // Private helper method for operation
            }
        });
        inputPanel.add(addButton);
        
        // Sort by Due Date button
        JButton sortByDueDate = new JButton("Sort by Due Date");
        sortByDueDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	saveState(); // Save list on Stack before operation (in case of undo)
                sortByDueDate(); // Private helper method for operation
            }
        });
        inputPanel.add(sortByDueDate);
        
        // Remove Task button
        JButton removeButton = new JButton("Remove Task");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	saveState(); // Save list on Stack before operation (in case of undo)
                removeTask(); // Private helper method for operation
            }
        });
        inputPanel.add(removeButton);
        
        // Sort by Name button
        JButton sortByName = new JButton("Sort by Name");
        sortByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	saveState(); // Save list on Stack before operation (in case of undo)
                sortByName(); // Private helper method for operation
            } 
        });
        inputPanel.add(sortByName);

        // Mark Completed button
        JButton markCompleted = new JButton("Mark Completed");
        markCompleted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	saveState(); // Save list on Stack before operation (in case of undo)
            	markCompleted(); // Private helper method for operation
            }
        });
        inputPanel.add(markCompleted);
        
        // Show Completed button
        JButton showCompletedTasksButton = new JButton("Show Completed");
        showCompletedTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCompleted(); // Private helper method for operation
            }
        });
        inputPanel.add(showCompletedTasksButton);
        
        // Clear List button
        JButton clearList = new JButton("Clear List");
        clearList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	saveState(); // Save list on Stack before operation (in case of undo)
            	clearList(); // Private helper method for operation
            }
        });
        inputPanel.add(clearList);

        // Show Incomplete button
        JButton showIncompleteButton = new JButton("Show Incomplete");
        showIncompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIncomplete(); // Private helper method for operation
            }
        });
        inputPanel.add(showIncompleteButton);
        
        // Undo button
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo(); // Private helper method for operation
            }
        });
        inputPanel.add(undoButton);

        // Show All Tasks button
        JButton showAllTasksButton = new JButton("Show All Tasks");
        showAllTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTasks();
            }
        });
        inputPanel.add(showAllTasksButton);
        
        // Place buttons bottom-center
        getContentPane().add(listScrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Displays Tasks on GUI
     */
    private void displayTasks() {
    	
    	// Clear List Model
        listModel.clear();
        
        // Update List Model with TaskList
        for (Task task : taskList.getAllTasks()) {
            listModel.addElement(task);
        }
    }
    
    /**
     * Saves TaskList to Stack (popped in case of undo)
     */
    private void saveState() {
        undoStack.push(new TaskList(taskList));
    }
    
    /**
     * Restores previous TaskList from Stack
     */
    private void undo() {
    	
    	// Retrieve TaskList
        if (!undoStack.isEmpty()) {
            taskList = undoStack.pop();
            displayTasks();
            
        // If Stack is empty, no operations can be undone
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to undo.");
        }
    }
    
    /**
     * Load TaskList from file
     */
    private void loadTasks() {
    	
    	// Allow user to choose file
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        	File loadFile = fileChooser.getSelectedFile();
        	TaskReader reader = new TaskReader();
            try {
            	
            	// Load file using TaskReader class
                taskList = reader.loadFromFile(loadFile);
                
                // Update display
                displayTasks();
                
            // If invalid file, display error message to user specified by TaskReader
            } catch (DataFormatException | FileNotFoundException e) {
            	 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Save TaskList to file
     */
    private void saveTasks() {
    	
    	// Allow user to choose file
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();
            
            // Check if file already exists
            if (saveFile.exists()) {
            	
            	// Prompt user to overwrite
                int response = JOptionPane.showConfirmDialog(this, "File already exists. Do you want to overwrite it?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                // If user doesn't want to overwrite, let them choose a different filename
                if (response == JOptionPane.NO_OPTION) {
                	saveTasks();
                }
            }
            TaskReader reader = new TaskReader();
            try {
            	
            	// Save TaskList to file using TaskReader
                reader.saveToFile(taskList, saveFile);
                
            // This should not happen
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Add Task to TaskList
     */
    private void addTask() {
    	
    	// Get name from user
        String name = taskName.getText();
        
        // Ensure name isn't empty
        if (!name.isEmpty()) {
        	
        	// Get description from user
            String description = taskDescription.getText();
            
            // Declare dueDate for parsing
            LocalDate dueDate;
            
            // Ensure valid name and description
            if (name.length() >= 50 || description.length() >= 50) {
            	JOptionPane.showMessageDialog(this, "Maximum name/description lengths: 50");
            } else {
                try {
                	
                	// Get due date from user
                    dueDate = LocalDate.parse(taskDueDate.getText());
                    
                // Ensure valid date format    
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Invalid date. Please enter the date in format YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create Task with user-specified fields
                Task task = new Task(0, name, description, dueDate);
                
                // Add to TaskList
                taskList.addTask(task);
                
                // Update display
                displayTasks();
            }
           
        // Task Name cannot be empty    
        } else {
        	JOptionPane.showMessageDialog(this, "Please enter a task name.");
        }
    }

    /**
     * Remove a Task from the TaskList
     */
    private void removeTask() {
    	
    	// Make sure user has a Task selected
        if (taskJList.getSelectedIndex() != -1) {
            Task task = taskList.getAllTasks().get(taskJList.getSelectedIndex());
            
            // Remove selected Task
            taskList.removeTask(task);
            
            // Update display
            displayTasks();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task.");
        }
    }
    
    /**
     * Mark a Task as completed
     */
    private void markCompleted() {
    	
    	// Make sure user has a Task selected
        if (taskJList.getSelectedIndex() != -1) {
            Task task = taskList.getAllTasks().get(taskJList.getSelectedIndex());
            
            // Mark the selected Task as completed
            task.setCompleted(true);
            
            // Update display
            displayTasks();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task.");
        }
    }
    
    /**
     * Clears the TaskList
     */
    private void clearList() {
    	
    	// Ask user for confirmation
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the list?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
        	
        	// Clear the list
            taskList.clear();
            
            // Update display
            displayTasks();
        }
    }

    /**
     * Sort by due date
     */
    private void sortByDueDate() {
    	
        if (taskList != null) {
        	
        	// Call TaskList method for sorting
            taskList.sortByDueDate();
            
            // Update display
            displayTasks();
        }
    }

    /**
     * Sort by name
     */
    private void sortByName() {
    	
        if (taskList != null) {
        	
        	// Call TaskList method for sorting
            taskList.sortByName();
            
            // Update display
            displayTasks();
        }
    }

    /**
     * Show completed tasks
     */
    private void showCompleted() {
    	
    	// Clear List Model
        listModel.clear();
        
        // Use TaskList method for getting completed tasks
        for (Task task : taskList.getCompletedTasks()) {
        	
        	// Update display
            listModel.addElement(task);
        }
    }
    
    /**
     * Show incomplete tasks
     */
    private void showIncomplete() {
    	
    	// Clear List Model
        listModel.clear();
        
        // Use TaskList method for getting incomplete tasks
        for (Task task : taskList.getIncompleteTasks()) {
        	
        	// Update display
            listModel.addElement(task);
        }
    }

    /**
     * Custom ListCellRenderer for displaying TaskList in desired format
     */
	private static class TaskRenderer extends JTextArea implements ListCellRenderer<Task> {
		
		/**
		 * Basic constructor
		 */
        public TaskRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        /**
         * Basic component formatting
         */
        @Override
        public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(formatTask(value));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return this;
        }

        /**
         * Custom Task formatter
         * @param task Task
         * @return formatted Task for display
         */
        private String formatTask(Task task) {
        	
        	// Format using String format method
            return String.format("%d. %s\nDescription: %s\nDue Date: %s\n%s\n",
                    task.getId(),
                    task.getName(),
                    task.getDescription(),
                    task.getDueDate().toString(),
                    task.isCompleted() ? "Completed." : "Incomplete.");
        }
    }

	/**
	 * Main method for invoking GUI
	 * @param args
	 */
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
        	
        	// Display GUI
            @Override
            public void run() {
                new TaskManagerGUI().setVisible(true);
            }
        });
    }
}