package duke.tasks;

/**
 * Represents a task to be completed by the user.
 */
public abstract class Task {
    protected String name;
    protected boolean isDone;

    public Task(String name) {
        this.name = name;
        isDone = false;
    }

    /**
     * Marks the Task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    public String getName() {
        return name;
    }

    public boolean getIsDone() {
        return isDone;
    }

    /**
     * @return A unicode tick or cross to represent Task completion status.
     */
    public String getStatus() {
        return isDone ? "\u2713" : "\u2718";
    }
}