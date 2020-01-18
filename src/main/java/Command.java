import java.util.List;

interface Command {
    void execute(String arg, List<Task> tasks);

    default String formatReply(String str) {
        String[] lines = str.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();
        String lineBreak = "===========================================================\n";
        for (String line : lines) {
            sb.append("> ");
            sb.append(line);
            sb.append("\n");
        }
        sb.append(lineBreak);
        return sb.toString();
    }
}

interface TaskCreation {
    default String CreateTaskReply(Task newTask, List<Task> tasks) {
        return String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.", newTask,
                tasks.size());
    }
}

class ListAll implements Command {
    public void execute(String arg, List<Task> tasks) {
        int counter = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (Task task : tasks) {
            sb.append(counter);
            sb.append(".");
            sb.append(task);
            sb.append("\n");
            counter += 1;
        }
        System.out.print(formatReply(sb.toString()));
    }
}

class MarkTaskAsDone implements Command {
    public void execute(String arg, List<Task> tasks) {
        int taskNo = Integer.parseInt(arg);
        Task task = tasks.get(taskNo - 1);
        task.markAsDone();
        System.out.print(formatReply("Nice! I've marked this task as done:\n  " + task));
    }
}

class CreateTodo implements Command, TaskCreation {
    public void execute(String arg, List<Task> tasks) {
        Task newTask = new Todo(arg);
        tasks.add(newTask);
        System.out.print(formatReply(CreateTaskReply(newTask, tasks)));
    }
}

class CreateDeadline implements Command, TaskCreation {
    public void execute(String arg, List<Task> tasks) {
        String[] args = arg.split("/by");
        if (args.length < 2) {
            System.out.print(formatReply("Usage: deadline [task name] /by [time]"));
            return;
        }
        Task newTask = new Deadline(args[0].strip(), args[1].strip());
        tasks.add(newTask);
        System.out.print(formatReply(CreateTaskReply(newTask, tasks)));
    }
}

class CreateEvent implements Command, TaskCreation {
    public void execute(String arg, List<Task> tasks) {
        String[] args = arg.split("/at");
        if (args.length < 2) {
            System.out.print(formatReply("Usage: event [task name] /at [time]"));
            return;
        }
        Task newTask = new Event(args[0].strip(), args[1].strip());
        tasks.add(newTask);
        System.out.print(formatReply(CreateTaskReply(newTask, tasks)));
    }
}

class NullCommand implements Command {
    public void execute(String arg, List<Task> tasks) {
        System.out.print(formatReply("Command not found!"));
    }
}