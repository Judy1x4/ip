package jude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import jude.command.AddCommand;
import jude.command.ListCommand;
import jude.command.MarkCommand;
import jude.command.UnmarkCommand;
import jude.command.DeleteCommand;

import jude.task.Deadline;
import jude.task.Event;
import jude.task.Todo;

public class ParserTest {

    // list
    @Test
    public void parse_list_success() {
        try {
            assertEquals(new ListCommand().toString(), new Parser().parse("list").toString());
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void parse_listWithLeadingSpace_fail() {

        try {
            assertEquals(new ListCommand().toString(), new Parser().parse(" list").toString());
            fail();
        } catch (JudeException je) {
            assertEquals("No valid jude.command was provided.", je.getMessage());
        }
    }

    @Test
    public void parse_listWithTrailingSpace_fail() {
        try {
            assertEquals(new ListCommand().toString(), new Parser().parse("list ").toString());
            fail();
        } catch (JudeException je) {
            assertEquals("Poyo,  The description of a list must be empty.", je.getMessage());
        }
    }

    @Test
    public void parse_listWithTwoWords_fail() {
        try {
            assertEquals(new ListCommand(), new Parser().parse("list a"));
            fail();
        } catch (JudeException je) {
            assertEquals("Poyo,  The description of a list must be empty.", je.getMessage());
        }
    }

    // mark
    @Test
    public void parse_mark_success() {
        try {
            assertEquals(new MarkCommand(1).toString(), new Parser().parse("mark 1").toString());
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void parse_markWithoutNumber_fail() {
        try {
            assertEquals(new MarkCommand(1).toString(), new Parser().parse("mark").toString());
            fail();
        } catch (JudeException je) {
            assertEquals(
                    "the index is not provided or not applicable to the current list size.", je.getMessage());
        }
    }

    @Test
    public void parse_ununmarkWithLetter_fail() {
        try {
            assertEquals(new UnmarkCommand(1).toString(), new Parser().parse("unmark AAA".toString()));
            fail();
        } catch (JudeException je) {
            assertEquals("Number format exception has occurred.", je.getMessage());
        }
    }

    // ununmark
    @Test
    public void parse_unmark_success() {
        try {
            assertEquals(new UnmarkCommand(1).toString(), new Parser().parse("unmark 1").toString());
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void parse_unmarkWithoutNumber_fail() {
        try {
            assertEquals(new UnmarkCommand(1).toString(), new Parser().parse("unmark").toString());
            fail();
        } catch (JudeException je) {
            assertEquals(
                    "the index is not provided or not applicable to the current list size.", je.getMessage());
        }
    }

    @Test
    public void parse_unmarkWithLetter_fail() {
        try {
            assertEquals(new UnmarkCommand(1).toString(), new Parser().parse("unmark AAA".toString()));
            fail();
        } catch (JudeException je) {
            assertEquals("Number format exception has occurred.", je.getMessage());
        }
    }

    // delete
    @Test
    public void parse_delete_success() {
        try {
            assertEquals(new DeleteCommand(1).toString(), new Parser().parse("delete 1").toString());
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void parse_deleteWithoutNumber_fail() {
        try {
            assertEquals(new DeleteCommand(1).toString(), new Parser().parse("delete").toString());
            fail();
        } catch (JudeException je) {
            assertEquals(
                    "the index is not provided or not applicable to the current list size.", je.getMessage());
        }
    }

    @Test
    public void parse_deleteWithLetter_fail() {
        try {
            assertEquals(new DeleteCommand(1).toString(), new Parser().parse("delete AAA".toString()));
            fail();
        } catch (JudeException je) {
            assertEquals("Number format exception has occurred.", je.getMessage());
        }
    }

    // to-do

    @Test
    public void parse_todo_success() {
        try {
            assertEquals(
                    new AddCommand(new Todo("AAA")).toString(),
                    new Parser().parse("to-do AAA").toString());
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void parse_todoWithoutDescription_fail() {
        try {
            assertEquals(
                    new AddCommand(new Todo("AAA")).toString(), new Parser().parse("to-do".toString()));
            fail();
        } catch (JudeException je) {
            assertEquals("No description was provided.", je.getMessage());
        }
    }

    @Test
    public void parse_todoWithoutDash_success() {
        try {
            assertEquals(
                    new AddCommand(new Todo("AAA")).toString(), new Parser().parse("todo AAA".toString()));
            fail();
        } catch (JudeException je) {
            assertEquals("No valid jude.command was provided.", je.getMessage());
        }
    }

    // deadline
    @Test
    public void parse_deadline_success() {
        try {
            assertEquals(
                    new AddCommand(new Deadline("AAA", "1/1/2000 0400")).toString(),
                    new Parser().parse("deadline AAA /by 1/1/2000 0400").toString()
            );
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void parse_deadlineNoTime_fail() {
        try {
            assertEquals(
                    new AddCommand(new Deadline("AAA", "1/1/2000 0000")).toString(),
                    new Parser().parse("deadline AAA /by 1/1/2000").toString()
            );
            fail();
        } catch (JudeException je) {
            assertEquals("wrong date or time format was provided."
                    + " Provide: day/month/year time (e.g. 1/1/2000 1800).", je.getMessage());
        }
    }

    @Test
    public void parse_deadlineWrongBy_fail() {
        try {
            assertEquals(
                    new AddCommand(new Deadline("AAA", "1/1/2000 0000")).toString(),
                    new Parser().parse("deadline AAA by 1/1/2000 0000").toString()
            );
            fail();
        } catch (JudeException je) {
            assertEquals("Poyo, the jude.command deadline"
                    + " must be provided with a description with the use of /by jude.command.", je.getMessage());
        }
    }

    // event
    @Test
    public void parse_event_success() {
        try {
            assertEquals(
                    new AddCommand(new Event("AAA", "1/1/2000 0000", "1/1/2000 1200")).toString(),
                    new Parser().parse("event AAA /from 1/1/2000 0000 /to 1/1/2000 1200").toString()
            );
        } catch (JudeException je) {
            System.out.println(je.getMessage());
            fail();
        }
    }

    @Test
    public void parse_eventNoTime_fail() {
        try {
            assertEquals(
                    new AddCommand(new Event("AAA", "1/1/2000 0000", "1/1/2000 1200")).toString(),
                    new Parser().parse("event AAA /from 1/1/2000 /to 1/1/2000").toString()
            );
            fail();
        } catch (JudeException je) {
            assertEquals("wrong date or time format was provided."
                    + " Provide: day/month/year time (e.g. 1/1/2000 1800).", je.getMessage());
        }
    }

    @Test
    public void parse_eventWrongTo_fail() {
        try {
            assertEquals(
                    new AddCommand(new Event("AAA", "1/1/2000 0000", "1/1/2000 1200")).toString(),
                    new Parser().parse("event AAA /from 1/1/2000 0000 to 1/1/2000 12000").toString()
            );
            fail();
        } catch (JudeException je) {
            assertEquals("Poyo, the jude.command event must be provided "
                    + "with a description with the use of /from followed by /to jude.command.", je.getMessage());
        }
    }

    @Test
    public void parse_eventWrongFrom_fail() {
        try {
            assertEquals(
                    new AddCommand(new Event("AAA", "1/1/2000 0000", "1/1/2000 1200")).toString(),
                    new Parser().parse("event AAA from 1/1/2000 0000 /to 1/1/2000 12000").toString()
            );
            fail();
        } catch (JudeException je) {
            assertEquals("Poyo, the jude.command event must be provided "
                    + "with a description with the use of /from followed by /to jude.command.", je.getMessage());
        }
    }
}
