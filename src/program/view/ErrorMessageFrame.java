package program.view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ErrorMessageFrame extends JFrame {
    private String message;
    private LinkedList<String> stackTraceElementLinkedList;


    public ErrorMessageFrame(Exception exception) {
        initStackError(exception);
        message = exception.getMessage();
        init(stackTraceElementLinkedList);
    }

    public void initStackError(Exception e) {
        StackTraceElement[] traceElements = e.getStackTrace();
        createStackListAndAddElements(traceElements);
    }

    public void createStackListAndAddElements(StackTraceElement[] traceElements) {
        stackTraceElementLinkedList = new LinkedList<>();
        for (StackTraceElement element : traceElements) {
            stackTraceElementLinkedList.add(element.toString());
        }
    }

    public void init(LinkedList<String> list) {
        setErrorFrame();
        packAndEnableErrorFrame();
    }

    public void setErrorFrame() {
        setBaseComponentsOfFrame();
        addDescription();
    }

    public void setBaseComponentsOfFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ошибка!");
        setPreferredSize(new Dimension(700, 600));
        setLayoutManagerOfFrame();
    }

    public void setLayoutManagerOfFrame() {
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(boxLayout);
    }

    public void addDescription() {
        addPreDescriptionTitle();
        addDescriptionText();
        addPostDescriptionTitle();
    }

    public void addPreDescriptionTitle() {
        add(new JLabel("Описание ошибки:"));
        add(new JLabel("----------------"));
    }

    public void addDescriptionText() {
        StringBuilder builder = new StringBuilder();
        JTextArea description = new JTextArea(createStackText(builder));
        add(new JScrollPane(description));
    }

    public String createStackText(StringBuilder builder) {
        builder.append(message);
        for (String mess : stackTraceElementLinkedList) {
            builder.append("\n").append(mess);
        }
        return builder.toString();
    }

    public void addPostDescriptionTitle() {
        add(new JLabel("----------------"));
        add(new JLabel("Скопируйте эту ошибку (Ctrl + C)"));
        add(new JLabel("и обратитесь к разработчику данного приложения."));
    }

    public void packAndEnableErrorFrame() {
        pack();
        setVisible(true);
    }
}