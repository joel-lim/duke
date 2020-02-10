package duke;

import java.util.concurrent.Semaphore;
import java.io.IOException;
import java.io.FileNotFoundException;

import duke.ui.Ui;
import duke.ui.TextUi;
import duke.tasks.TaskList;
import duke.storage.Storage;
import duke.storage.TextStorage;
import duke.commands.CommandHandler;
import duke.exceptions.DukeException;

/**
 * Logic for Duke. Uses TextUi by default.
 */
public class Duke {
    private Semaphore inputLock;
    private Ui ui = new TextUi();

    /**
     * Runs the main loop of Duke.
     */
    public void run() {
        String filePath = "data/tasks.txt";
        assert filePath != null && filePath.length() > 0 : "filepath should not be empty";
        Storage storage = new TextStorage(filePath);
        TaskList tasks = new TaskList();

        ui.showGreeting();

        // Load existing save file
        try {
            tasks.add(storage.load());
            ui.showReply("Save file loaded!");
        } catch (FileNotFoundException e) {
            ui.showError("Save file not found!");
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }

        // Main loop of Duke to handle user commands
        CommandHandler handler = new CommandHandler(tasks, ui, storage);
        while (handler.isActive()) {
            if (inputLock != null) {
                try {
                    inputLock.acquire();
                } catch (InterruptedException e) {
                    ui.showError("Interrupted!");
                }
            }
            handler.handleCommand(ui.getInput());
        }

        // Save to disk
        try {
            storage.save(tasks.getAllTasks());
            ui.showReply("Save Success! See you next time!");
        } catch (IOException e) {
            ui.showError("Save Failure :-(. Try again next time!");
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }

        ui.shutDown();
    }

    /**
     * Registers a semaphore for Duke to synchronise with other threads.
     */
    public void addSemaphore(Semaphore sem) {
        inputLock = sem;
    }

    /**
     * Configures Duke to use a particular Ui.
     */
    public void useUi(Ui ui) {
        this.ui = ui;
    }
}