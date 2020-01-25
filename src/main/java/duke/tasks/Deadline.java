package duke.tasks;

import java.time.LocalDateTime;

public class Deadline extends TimedTask {
    protected LocalDateTime dateTime;

    public Deadline(String name, LocalDateTime dateTime) {
        super(name);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", getStatus(), name, formatTime(dateTime));
    }
}