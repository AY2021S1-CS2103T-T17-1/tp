package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DOUBLE_DEGREE;

import seedu.address.model.Model;
import seedu.address.model.module.GoalTarget;

/**
 * Calculates the CAP required to achieve the user's target CAP for the remaining modules that
 * they will take.
 */
public class ProgressCommand extends Command {

    public static final String COMMAND_WORD = "progress";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows your target CAP and"
            + "calculates the average CAP required for your remaining modules to reach your target.\n"
            + "Parameters: "
            + "[" + PREFIX_DOUBLE_DEGREE + "]\n"
            + "Example: " + COMMAND_WORD + " --ddp";

    public static final String MESSAGE_REQUIRED_CAP = "The average CAP required for your remaining modules "
            + "to meet your target is: %.2f";
    public static final String MESSAGE_TARGET_CAP = "Your target CAP is: %.2f\n";
    public static final String MESSAGE_UNACHIEVABLE_CAP = "Sorry! Your target CAP cannot be achieved :(";

    private static final int TOTAL_MODULAR_CREDIT = 160;
    private static final int TOTAL_MODULAR_CREDIT_DDP = 200;
    private static final double MAX_CAP = 5.0;
    private static final double MIN_CAP = 0;

    private final boolean isDdp;

    public ProgressCommand(boolean isDdp) {
        this.isDdp = isDdp;
    }

    @Override
    public CommandResult execute(Model model) {

        double currentCap = model.generateCap();
        GoalTarget userGoalTarget = model.getGoalTarget();
        double targetCap = GoalTarget.getUserGoalGrade(userGoalTarget);

        double currentMc = model.getCurrentMc();
        double totalMc = isDdp ? TOTAL_MODULAR_CREDIT_DDP : TOTAL_MODULAR_CREDIT;

        double remainingMc = totalMc - currentMc;
        double currentWeightedCap = currentCap * currentMc;
        double totalWeightedCap = targetCap * totalMc;

        double requiredCap = (totalWeightedCap - currentWeightedCap) / remainingMc;

        if (requiredCap > MAX_CAP || requiredCap < MIN_CAP) {
            return new CommandResult(String.format(MESSAGE_TARGET_CAP, targetCap)
                    + MESSAGE_UNACHIEVABLE_CAP);
        } else {
            return new CommandResult(String.format(MESSAGE_TARGET_CAP, targetCap)
                    + String.format(MESSAGE_REQUIRED_CAP, requiredCap));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ProgressCommand)) {
            return false;
        }

        return isDdp == ((ProgressCommand) other).isDdp;
    }
}
