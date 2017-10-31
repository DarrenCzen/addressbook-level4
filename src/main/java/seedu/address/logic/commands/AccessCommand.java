package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.AccessWebsiteRequestEvent;
import seedu.address.commons.events.ui.PanelSwitchRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Accesses a person's website in the address book.
 */
public class AccessCommand extends Command {
    public static final String COMMAND_WORD = "access";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accesses the website of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ACCESS_PERSON_SUCCESS = "Accessed website of Person: %1$s";

    private final Index targetIndex;

    public AccessCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new PanelSwitchRequestEvent(COMMAND_WORD));

        EventsCenter.getInstance().post(new AccessWebsiteRequestEvent(person.getWebsite().toString()));
        return new CommandResult(String.format(MESSAGE_ACCESS_PERSON_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccessCommand // instanceof handles nulls
                && this.targetIndex.equals(((AccessCommand) other).targetIndex)); // state check
    }
}
