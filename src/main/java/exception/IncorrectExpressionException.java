package exception;

public class IncorrectExpressionException extends Exception{
    public IncorrectExpressionException(String errorMessage) {
        super(errorMessage);
    }
}
