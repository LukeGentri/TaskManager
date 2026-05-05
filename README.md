# TaskManager

A robust, GUI-driven Java application designed to streamline personal productivity. Built with a focus on clean code principles, **TaskManager** allows users to manage, sort, and persist tasks through an intuitive interface.

---

## Key Features

*   **Full CRUD Operations**: Create, Read, Update, and Delete tasks with ease.
*   **State Management**: Features a dedicated **Undo** functionality to reverse recent operations.
*   **Dynamic Sorting**: Organize tasks by **Due Date** or **Alphabetical Name**.
*   **Smart Filtering**: Toggle views between Completed, Incomplete, or All tasks.
*   **Data Persistence**: Full File I/O support for saving and loading task lists via **CSV format**.
*   **Unit Tested**: Core logic verified using **JUnit** to ensure reliability and edge-case handling.

---

## Tech Stack

*   **Language:** Java 8+
*   **GUI Framework:** Swing/AWT
*   **Testing:** JUnit
*   **IDE:** Optimized for Eclipse IDE
*   **Version Control:** Git

---

## Installation & Setup

### Prerequisites
*   **Java Development Kit (JDK) 8** or higher.
*   **Eclipse IDE** (or any Java-compatible IDE).

### Step-by-Step Instructions

1. **Clone the Repository**
   `git clone https://github.com/LukeGentri/TaskManager.git`

2. **Import into Eclipse**
   *   Navigate to **File** > **Import**.
   *   Select **General** > **Existing Projects into Workspace**.
   *   Browse to the cloned directory and click **Finish**.

3. **Launch the App**
   *   Locate the `TaskManagerGUI.java` file in the source folder.
   *   Right-click the file and select **Run As** > **Java Application**.

---

## Usage Guide

| Feature | Action |
| :--- | :--- |
| **Add Task** | Click "Add Task" and input the Name, Description, and Date. |
| **Remove Task** | Select a task from the list and hit "Remove Task." |
| **Complete Task**| Click the checkbox next to any task to toggle its status. |
| **Undo** | Reverses the last action taken (Add/Remove/Clear). |
| **File I/O** | Use the "File" panel to **Save** your list or **Load** an existing CSV. |

### CSV Data Format
For manual file creation, ensure the following format (Max 50 characters for Name/Description):
`Name,Description,DueDate`

**Example:**
`Submit Project,Finalize documentation and push to GitHub,2026-05-15`
`Review PRs,,2026-05-16`

> **Note:** The file must not be empty. Description is optional; Name and DueDate are required. No spaces between fields.

---

## License

This project is licensed under the **MIT License**.

## Contact

**Luke Gentri**
CS @ NC State · Graduated May 2026
[lukegentri1@gmail.com](mailto:lukegentri1@gmail.com) · [LinkedIn](https://www.linkedin.com/in/luke-gentri-384b033a4/)
