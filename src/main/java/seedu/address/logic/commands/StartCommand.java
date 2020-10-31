package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterManager;

/**
 * Starts the updating of the module list in a semester.
 * Enables the user to add,  or delete modules in
 * a particular semester.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts adding or updating modules in the semester stated.\n"
            + "Parameters: SEMESTER\n"
            + "Example: " + COMMAND_WORD + " Y2S1";

    public static final String MESSAGE_START_SEMESTER_SUCCESS =
            "You are now updating: %1$s \n The modules you are taking in %1$s:";

    private final Semester toStart;

    /**
     * Creates a StartCommand to add the specified {@code Semester}
     */
    public StartCommand(Semester semester) throws ParseException {
        requireNonNull(semester);
        if (!SemesterManager.isValidSemester(semester.toString())) {
            throw new ParseException(Messages.MESSAGE_INVALID_SEMESTER);
        }
        toStart = semester;
    }

    public Semester getStartSemester() {
        return toStart;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        SemesterManager semesterManager = SemesterManager.getInstance();
        semesterManager.setCurrentSemester(toStart);
        return new CommandResult(String.format(MESSAGE_START_SEMESTER_SUCCESS, toStart));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && toStart.equals(((StartCommand) other).toStart));
    }
}
