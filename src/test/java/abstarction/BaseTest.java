package abstarction;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseTest {
    protected String regex = "^\\S.+[ =]";

    @DataProvider(name = "expressions")
    public Object[][] getExpressions() {
        return new Object[][]{
                {1, "2+2", 4.0},
                {2, "4-3", 1.0},
                {3, "5*5", 25.0},
                {4, "10/2", 5.0},
                {5, "0.75 + 12.321", 13.071},
                {6, " (7 + 8) + -1 ", 14.0},
                {7, "((5+2)*-3+-5/(2*40)) * 4", -84.25}
        };
    }

    @DataProvider(name = "incorrectExpressions")
    public Object[][] getIncorrectExpressions() {
        return new Object[][]{
                {"2+2-*1"},
                {"test"},
                {"(23-1"},
                {"[7-2]+(1-2)"}
        };
    }

    @DataProvider(name = "updatedExpressions")
    public Object[][] getUpdatedExpressions() {
        return new Object[][]{
                {1, "4+4", 8.0},
                {2, "14/14", 1.0},
                {3, " (1 * 8) / (12-1) ", 0.727},
                {4, "1 * 4", 4.0}
        };
    }

    @DataProvider(name = "incorrectExpressionsWithId")
    public Object[][] getIncorrectExpressionsWithId() {
        return new Object[][]{
                {1, "2+2-*1"},
                {2, "test"},
                {3, "(23-1"},
                {4, "[7-2]+(1-2)"}
        };
    }

    public Map<String, List<String>> expressionMap() {
        Map<String, List<String>> expectedObject = new HashMap<>();
        List<String> list = new ArrayList<>(List.of("1*4"));
        expectedObject.put("=", list);
        expectedObject.put("==", list);
        list = new ArrayList<>(List.of("4+4", "0.75+12.321", "(7+8)+-1"));
        expectedObject.put(">", list);
        list = new ArrayList<>(List.of("4+4", "1*4", "0.75+12.321", "(7+8)+-1"));
        expectedObject.put(">=", list);
        list = new ArrayList<>(List.of("14/14", "(1*8)/(12-1)", "((5+2)*-3+-5/(2*40))*4"));
        expectedObject.put("<", list);
        list = new ArrayList<>(List.of("14/14", "(1*8)/(12-1)", "1*4", "((5+2)*-3+-5/(2*40))*4"));
        expectedObject.put("<=", list);
        return expectedObject;
    }
}
