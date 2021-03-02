package parser;

import commands.*;
import exceptions.MissingInfoException;
import exceptions.UnknownCommandException;
import io.DukePrint;
import models.TaskList;

import java.text.ParseException;

public class CommandParser {

    private final CommandVerifier verifier;
    private final TaskList taskList;
    private final DukePrint dukePrint;

    public CommandParser(TaskList taskList, DukePrint dukePrint) {
        verifier = new CommandVerifier();
        this.taskList = taskList;
        this.dukePrint = dukePrint;
    }

    public Command parse(String command) throws UnknownCommandException, MissingInfoException, ParseException {
        verifier.verify(command);
        return parseValidCommandString(command);
    }

    private Command parseValidCommandString(String command) throws ParseException {
        Command result = null;
        String[] subStrings = command.split(" ");

        switch (subStrings[0]) {
        case "bye":
            result = new ExitCommand(dukePrint);
            break;
        case "list":
            result = new ListCommand(taskList, dukePrint);
            break;
        case "done":
            result = new DoneCommand(taskList, Integer.parseInt(subStrings[1]), dukePrint);
            break;
        case "todo":
            result = new AddToDoCommand(taskList, dukePrint, command.substring(4).trim());
            break;
        case "deadline":
            String[] splitByKeyword = command.split("/by");
            result = new AddDeadlineCommand(taskList, dukePrint,
                    splitByKeyword[0].substring(9).trim(), splitByKeyword[1].trim());
            break;
        case "event":
            splitByKeyword = command.split("/at");
            result = new AddEventCommand(taskList, dukePrint,
                    splitByKeyword[0].substring(6).trim(), splitByKeyword[1].trim());
            break;
        case "delete":
            result = new DeleteCommand(taskList, Integer.parseInt(subStrings[1]), dukePrint);
            break;
        case "find":
            result = new FindCommand(command.substring(5), taskList, dukePrint);
            break;
        default:
            break;
        }
        return result;
    }
}
