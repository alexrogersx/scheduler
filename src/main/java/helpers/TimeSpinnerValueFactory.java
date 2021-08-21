package helpers;

import javafx.scene.control.SpinnerValueFactory;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * SpinnerValueFactory object used to format a LocalDateTime object in a Spinner object.
 */
public class TimeSpinnerValueFactory extends SpinnerValueFactory<LocalTime> {
        {
            setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), null));
        }
        @Override
        public void decrement(int steps) {
            if (getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.minusMinutes(steps));
            }
        }

        @Override
        public void increment(int steps) {
            if (this.getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.plusMinutes(steps));
            }
        }
}
