package jude.command;

import jude.JudeException;
import jude.Storage;
import jude.TaskList;
import jude.task.Task;

/**
 * Represents the command which contains the instruction as series of actions to add a Task
 * into the TaskList to be executed.
 */
public class AddCommand extends Command {
    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Adds task to the TaskList. Notifies the user that the task has been added.
     * @param list TaskList to store the task to be added.
     * @param storage Save File controller which writes the task data onto the save file.
     * @throws JudeException If any one of the method fails.
     */
    @Override
    public void execute(TaskList list, Storage storage) throws JudeException {
        list.addTask(task);
        setMessage("Task " + task + " has been added.");
        storage.save(list);
    }

    @Override
    public String getType() {
        return "AddCommand";
    }
}
