package startingInput;

import javax.swing.*;

public class InputSlider extends JSlider {
    public InputSlider(int min, int max, int value) {
        super(min, max, value);
        this.setPaintTrack(true);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setMajorTickSpacing(1);
    }

    public int getUserInput() {
        return this.getValue();
    }
}
