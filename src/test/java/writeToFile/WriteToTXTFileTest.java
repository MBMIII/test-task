package writeToFile;

import abstarction.BaseTest;
import abstarction.WriteToFile;
import exception.IncorrectExpressionException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteToTXTFileTest extends BaseTest {
    String path = "src\\main\\resources\\files\\test.txt";
    WriteToFile write = new WriteToTXTFile(path);

    @BeforeTest
    public void deleteFileIfExist() {
        try {
            Path temp = Paths.get(path);
            Files.deleteIfExists(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String[] symbols = {"==", ">", ">=", "<", "<="};
        for (String symbol : symbols) {
            List<String> currentObject = new ArrayList<>(write.getExpressionByValue(symbol, 4.0));
            List<String> expectedObject = expressionMap().get(symbol);
            Assert.assertEquals(currentObject, expectedObject);
        }
    }
}
