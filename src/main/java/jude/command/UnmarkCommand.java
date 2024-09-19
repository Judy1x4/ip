package jude.command;

import jude.JudeException;
import jude.Storage;
import jude.TaskList;

/**
 * Represents the class which contains the series of actions to unmark a Task as not done to be executed.
 */
public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Unmarks the task as done. Save the changes into the save file.
     * @param list
     * @param storage will save the unmarked version of task data to the save file
     * @throws JudeException if any one of the method call fails
     */
    @Override
    public void execute(TaskList list, Storage storage) throws JudeException {
        list.unmarkTask(index);
        setMessage("Task " + list.getTask(index) + " been unmarked.");
        storage.save(list);
    }

    @Override
    public String getType() {
        return "UnmarkCommand";
    }
}
