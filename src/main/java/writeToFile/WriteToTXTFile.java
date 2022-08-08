package writeToFile;

import abstarction.WriteToFile;
import exception.IncorrectExpressionException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class WriteToTXTFile extends WriteToFile {
    private String pathToFile;

    public WriteToTXTFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public void write(String expression) throws IncorrectExpressionException {
        String result;
        expression = expression.replaceAll("\\s+", "");
        Path path = Paths.get(pathToFile);
        int id = 0;
        result = String.valueOf(evaluate(expression));
        if (!Files.exists(path) | new File(pathToFile).length() == 0) {
            try (FileWriter fr = new FileWriter(String.valueOf(path))) {
                fr.write("ID");
                fr.write(" -> ");
                fr.write("Expression");
                fr.write(" = ");
                fr.write("Value");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                reader.readLine();
                id++;
            }
            Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
            Files.write(path, (id + " -> ").getBytes(), StandardOpenOption.APPEND);
            Files.write(path, expression.getBytes(), StandardOpenOption.APPEND);
            Files.write(path, " = ".getBytes(), StandardOpenOption.APPEND);
            Files.write(path, result.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void updateExpressionByID(int id, String newExpression) throws IncorrectExpressionException {
        newExpression = newExpression.replaceAll("\\s+", "");
        Path path = Paths.get(pathToFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                if (reader.readLine().startsWith(String.valueOf(id))) {
                    List<String> lines = Files.readAllLines(path);
                    lines.set(id, id + " -> " + newExpression + " = " + evaluate(newExpression) + "");
                    Files.write(path, lines);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("No such id!");
        }
    }

    @Override
    public List<String> getAll() {
        List<String> expressions = new ArrayList<>();
        Path path = Paths.get(pathToFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                expressions.add(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("File does not exist!");
        }
        return expressions;
    }

    @Override
    public String getExpressionByID(int id) {
        String temp;
        Path path = Paths.get(pathToFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                temp = reader.readLine();
                if (temp.startsWith(String.valueOf(id))) {
                    return temp.replaceAll("\\d -> ", "");
                }
            }
        } catch (IOException e) {
            System.out.println("Can't find id!");
        }
        return null;
    }

    public List<String> getExpressionByValue(String mathSymbol, double value) {
        List<String> expressions = new ArrayList<>();
        String removeFirst = ".+->.";
        String removeLast = "\\s=.+";
        String removeAllButValue = ".+= ";
        String temp;
        Path path = Paths.get(pathToFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.readLine();
            while (reader.ready()) {
                temp = reader.readLine();
                switch (mathSymbol) {
                    case "==":
                        if (Double.parseDouble(temp.replaceAll(removeAllButValue, "")) == value) {
                            temp = temp.replaceAll(removeFirst, "").replaceAll(removeLast, "");
                            expressions.add(temp);
                        }
                        break;
                    case ">":
                        if (Double.parseDouble(temp.replaceAll(removeAllButValue, "")) > value) {
                            temp = temp.replaceAll(removeFirst, "").replaceAll(removeLast, "");
                            expressions.add(temp);
                        }
                        break;
                    case ">=":
                        if (Double.parseDouble(temp.replaceAll(removeAllButValue, "")) >= value) {
                            temp = temp.replaceAll(removeFirst, "").replaceAll(removeLast, "");
                            expressions.add(temp);
                        }
                        break;
                    case "<":
                        if (Double.parseDouble(temp.replaceAll(removeAllButValue, "")) < value) {
                            temp = temp.replaceAll(removeFirst, "").replaceAll(removeLast, "");
                            expressions.add(temp);
                        }
                        break;
                    case "<=":
                        if (Double.parseDouble(temp.replaceAll(removeAllButValue, "")) <= value) {
                            temp = temp.replaceAll(removeFirst, "").replaceAll(removeLast, "");
                            expressions.add(temp);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("No such file!");
        }
        return expressions;
    }

    @Override
    public void clear() {
        try (PrintWriter pw = new PrintWriter(pathToFile)) {
            pw.print("");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
