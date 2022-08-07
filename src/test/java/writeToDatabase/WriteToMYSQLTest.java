package writeToDatabase;

import abstarction.BaseTest;
import abstarction.WriteToDatabase;
import exception.IncorrectExpressionException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class WriteToMYSQLTest extends BaseTest {
    String schema = "";
    String url = "" + schema;
    String username = "";
    String password = "";
    WriteToDatabase write = new WriteToMYSQL(url, username, password);

    @BeforeTest
    public void clearTable() {
        write.clearTable("expressions");
    }

    @Test(priority = 1, dataProvider = "expressions")
    public void writeHappyPath(int id, String expression, double result) throws IncorrectExpressionException {
        write.write(expression);
        Assert.assertEquals(result, Double.valueOf(write.getExpressionByID(id).replaceAll(regex, "")));
    }

    @Test(priority = 2, dataProvider = "incorrectExpressions", expectedExceptions = IncorrectExpressionException.class)
    public void writeIncorrectExpressions(String expression) throws IncorrectExpressionException {
        write.write(expression);
    }

    @Test(priority = 3, dataProvider = "updatedExpressions")
    public void updateHappyPath(int id, String expression, double result) throws IncorrectExpressionException {
        write.updateExpressionByID(id, expression);
        Assert.assertEquals(result, Double.valueOf(write.getExpressionByID(id).replaceAll(regex, "")));
    }

    @Test(priority = 4, dataProvider = "incorrectExpressionsWithId", expectedExceptions = IncorrectExpressionException.class)
    public void updateWithIncorrectExpressions(int id, String expression) throws IncorrectExpressionException {
        write.updateExpressionByID(id, expression);
    }

    @Test(priority = 5)
    public void searchByExpressionValue() {
        System.out.println(write.getExpressionByValue("==", 4.0));
        String[] symbols = {"=", ">", ">=", "<", "<="};
        for (String symbol : symbols) {
            List<String> currentObject = new ArrayList<>(write.getExpressionByValue(symbol, 4.0));
            List<String> expectedObject = expressionMap().get(symbol);
            Assert.assertEquals(currentObject, expectedObject);
        }
    }

}
