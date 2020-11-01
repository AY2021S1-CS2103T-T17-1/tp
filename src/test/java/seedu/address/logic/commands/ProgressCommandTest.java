package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CAP_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAP_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_TARGET_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_TARGET_C;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ProgressCommand.MESSAGE_REQUIRED_CAP;
import static seedu.address.logic.commands.ProgressCommand.MESSAGE_TARGET_CAP;
import static seedu.address.logic.commands.ProgressCommand.MESSAGE_UNACHIEVABLE_CAP;
import static seedu.address.testutil.TypicalModules.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.GoalTarget;

public class ProgressCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new GoalTarget());

    private final boolean isDdp = true;

    @Test
    public void execute_progressWithoutDdp_success() {
        model.setGoalTarget(new GoalTarget(VALID_GOAL_TARGET_B));
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_TARGET_CAP, VALID_CAP_A)
                        + String.format(MESSAGE_REQUIRED_CAP, VALID_CAP_A));

        ModelManager expectedModel = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new GoalTarget(VALID_GOAL_TARGET_B));

        ProgressCommand progressCommand = new ProgressCommand(!isDdp);
        assertCommandSuccess(progressCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_progressWithDdp_success() {
        model.setGoalTarget(new GoalTarget(VALID_GOAL_TARGET_B));
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_TARGET_CAP, VALID_CAP_A)
                        + String.format(MESSAGE_REQUIRED_CAP, VALID_CAP_A));

        ModelManager expectedModel = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new GoalTarget(VALID_GOAL_TARGET_B));

        ProgressCommand progressCommand = new ProgressCommand(isDdp);
        assertCommandSuccess(progressCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_progressWithUnachievableGoal_success() {
        model.setGoalTarget(new GoalTarget(VALID_GOAL_TARGET_C));
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_TARGET_CAP, VALID_CAP_B)
                        + MESSAGE_UNACHIEVABLE_CAP);

        ModelManager expectedModel = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new GoalTarget(VALID_GOAL_TARGET_C));

        ProgressCommand progressCommand = new ProgressCommand(isDdp);
        assertCommandSuccess(progressCommand, model, expectedCommandResult, expectedModel);
    }
}
