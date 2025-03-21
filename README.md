# TaskManager

TaskManager is a simple Java application built using Eclipse IDE. It's a to-do list that allows users to manage tasks through a GUI. Users can add tasks, remove tasks, and mark tasks complete. Users can also sort tasks by due date or name, list completed or incompleted tasks, clear all tasks, and undo any of the previous operations. TaskManager also supports file IO. Users can load a task list from a CSV file and save the current list to a file.

Prerequisites

To run or modify this project, you need:

    Java 8 or higher
    Eclipse IDE (or any Java IDE that supports Maven or JUnit)
    JUnit (for testing)

Running the Program

1. Clone the repository:

	git clone https://github.com/LukeGentri/TaskManager.git

2. In Eclipse:

    Select File - Import - Existing Projects into Workspace.
    Select the cloned repository.
    Click Finish.

3. Running the Program:

    Run the TaskManagerGUI class as a Java Application.

Usage

    Add Task: Click the "Add Task" button to create a new task. Fill in the name, description, and due date. Description may be empty.
    Remove Task: Select a task from the list and click "Remove Task" to delete it.
    Mark Completed: Mark a task as completed by clicking the checkbox next to it.
	Clear List: Delete the entire list.
	Undo: Reverse previous operation.
    Sort Tasks: You can sort tasks by due date or name.
	Show Tasks: You can show completed, incompleted, or all tasks.
    Load/Save Tasks: Use the "Load" and "Save" buttons under the "File" panel to load from and save tasks to a CSV file.

Example CSV Format

When saving or loading tasks from a file, the format is as follows: Name,Description,DueDate

For example, the following is a valid file to load with two tasks:

Task 1,Description 1,2025-03-21

Task 2, Description 2,2025-03-22

The file must not be empty when loading. Description may be empty. Name and DueDate must be present. Name and Description each have a max length of 50. There must be no spaces between fields. Tasks are automatically formatted as such when saving to a file.

License

This project is licensed under the MIT License - see the LICENSE file for details.