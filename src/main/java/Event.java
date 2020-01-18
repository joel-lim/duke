class Event extends Task {
    protected String dateTime;

    public Event(String name, String dateTime) {
        super(name);
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (at: %s)", getStatus(), name, dateTime);
    }
}