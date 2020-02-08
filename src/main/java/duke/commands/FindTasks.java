package duke.commands;

import java.util.List;

import duke.ui.Ui;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.storage.Storage;
import duke.exceptions.DukeException;

class FindTasks implements Command {
    public void execute(String arg, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        StringBuilder sb = new StringBuilder();
        List<Task> matchingTasks = tasks.search(arg.strip());

        if (matchingTasks.isEmpty()) {
            ui.showReply("No matching tasks found!");
            return;
        }
        assert matchingTasks.size() > 0;
        int counter = 1;
        sb.append("Here are the matching tasks in your list:\n");
        for (Task task : matchingTasks) {
            sb.append(counter + ".");
            sb.append(task);
            sb.append("\n");
            counter += 1;
        }

        ui.showReply(sb.toString());
    }
}