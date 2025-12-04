package ee.silves.sectorstask.util;

import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    public boolean validateName(String name) {
        return name.matches("^(?=.*[A-Za-z]).+$");
    }

}
