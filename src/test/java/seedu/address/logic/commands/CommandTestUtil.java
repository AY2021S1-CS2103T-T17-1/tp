package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULAR_CREDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SET_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleNameContainsKeywordsPredicate;
import seedu.address.model.semester.Semester;
import seedu.address.testutil.UpdateModNameDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_MOD_NAME_A = "CS2103T";
    public static final String VALID_MOD_NAME_B = "CS2100";
    public static final String VALID_GRADE_A = "A";
    public static final String VALID_GRADE_B = "B-";
    public static final String VALID_GRADE_C = "A-";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_CODE = "CS2100";
    public static final int VALID_MODULAR_CREDIT = 4;
    public static final int VALID_GOAL_TARGET_A = 4;
    public static final int VALID_GOAL_TARGET_B = 3;
    public static final int VALID_GOAL_TARGET_C = 6;
    public static final String VALID_GOAL_TARGET_INPUT = "4";
    public static final int INVALID_GOAL_TARGET = -1;
    public static final double VALID_CAP_A = 3.50;
    public static final double VALID_CAP_B = 0;

    public static final Semester VALID_SEMESTER = Semester.Y1S1;
    public static final String MOD_NAME_DESC_A = " " + PREFIX_MOD_NAME + VALID_MOD_NAME_A;
    public static final String MOD_NAME_DESC_B = " " + PREFIX_MOD_NAME + VALID_MOD_NAME_B;
    public static final String NO_GRADE = "NA";
    public static final String GRADE_DESC_A = " " + PREFIX_GRADE + VALID_GRADE_A;
    public static final String GRADE_DESC_B = " " + PREFIX_GRADE + VALID_GRADE_B;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String MODULAR_CREDIT_DESC = " " + PREFIX_MODULAR_CREDIT + VALID_MODULAR_CREDIT;
    public static final String SET_GOAL_DESC_A = " " + PREFIX_SET_GOAL + VALID_GOAL_TARGET_A;
    public static final String SET_GOAL_DESC_B = " " + PREFIX_SET_GOAL + VALID_GOAL_TARGET_B;
    public static final String LIST_GOAL_DESC = " " + PREFIX_LIST_GOAL;
    public static final String SEMESTER_DESC = " " + PREFIX_SEMESTER + VALID_SEMESTER.toString();

    public static final String INVALID_MOD_NAME_DESC = " " + PREFIX_MOD_NAME + "James&"; // '&' not allowed in mod names
    public static final String INVALID_GRADE_DESC = " " + PREFIX_GRADE; // empty string not allowed for GRADES
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String WHITESPACE = " ";

    public static final UpdateCommand.UpdateModNameDescriptor DESC_A;
    public static final UpdateCommand.UpdateModNameDescriptor DESC_B;

    static {
        DESC_A = new UpdateModNameDescriptorBuilder().withName(VALID_MOD_NAME_A)
                .withGrade(VALID_GRADE_A)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_B = new UpdateModNameDescriptorBuilder().withName(VALID_MOD_NAME_B)
                .withGrade(VALID_GRADE_B)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered module list and selected module in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());

        Module module = model.getFilteredModuleList().get(targetIndex.getZeroBased());
        final String[] splitName = module.getModuleName().fullModName.split("\\s+");
        model.updateFilteredModuleList(new ModuleNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredModuleList().size());
    }

}
