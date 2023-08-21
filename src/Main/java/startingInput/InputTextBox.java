package main.java.startingInput;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputTextBox extends JTextField {
    String userInput;
    InputTextBox selfReference = this;

    public InputTextBox() {
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                userInput = selfReference.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                userInput = selfReference.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    /**
     * Returns the string that the user input into this text box
     * @return the string that the user input into this text box
     */
    public String getUserInput() {
        return userInput;
    }
}
