package helpers;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

/**
 * Class to return a TextFormatter object to restrict the max character length of a text field
 */
public class TextFieldLength {

    private int len;

    public TextFieldLength(int length) {
        this.len = length;
    }

    private UnaryOperator<javafx.scene.control.TextFormatter.Change> modifyChange = c -> {
        if (c.isContentChange()) {
            int newLength = c.getControlNewText().length();
            if (newLength > len) {
                // replace the input text with the last len chars
                String tail = c.getControlNewText().substring(0, len);
                c.setText(tail);
                // replace the range to complete text
                // valid coordinates for range is in terms of old text
                int oldLength = c.getControlText().length();
                c.setRange(0, oldLength);
            }
        }
        return c;
    };

    private TextFormatter lengthFormatter = new TextFormatter(modifyChange);

    public TextFormatter getLengthFormatter() {
        return lengthFormatter;
    }
}
