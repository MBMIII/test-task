package abstarction;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import exception.IncorrectExpressionException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public interface Write {
    default Double evaluate(String expression) throws IncorrectExpressionException {
        double result;
        try {
            BigDecimal bd = new BigDecimal(Double.toString(new DoubleEvaluator().evaluate(expression)));
            result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
        } catch (IllegalArgumentException exception) {
            throw new IncorrectExpressionException("Incorrect expression");
        }
        return result;
    }

    void write(String expression) throws IncorrectExpressionException;

    void updateExpressionByID(int id, String newExpression) throws IncorrectExpressionException;

    String getExpressionByID(int id);

    List<String> getExpressionByValue(String mathSymbol, double value);
}
