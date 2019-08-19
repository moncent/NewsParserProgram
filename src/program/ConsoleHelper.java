// Используется для отладки

package program;

import program.view.ErrorMessageFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    public static void printText(String text) {
        System.out.println(text);
    }

    public static String readText() {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            line = reader.readLine();
        } catch (IOException e) {
            new ErrorMessageFrame(e);
        }
        return line;
    }
}