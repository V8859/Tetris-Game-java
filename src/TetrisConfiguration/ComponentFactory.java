package TetrisConfiguration;

import javax.swing.*;
import java.util.Optional;

public class ComponentFactory {

    public static JPanel generateComponent(String componentType, String text) {
        switch (componentType.toLowerCase()) {
            case "options":
                return new OptionsComponent(text).createComponent();
            case "player":
                return new PlayerComponent(text).createComponent();
            default:
                throw new IllegalArgumentException("Unknown component type: " + componentType);
        }
    }
    public static JPanel generateSliderComponent(String text, int min, int max, int initial){
        return new SliderComponent(text, min, max, initial).createComponent();
    }
    public static JButton generateButton(String text){
        return new ButtonComponent(text).createComponent();
    };
}
