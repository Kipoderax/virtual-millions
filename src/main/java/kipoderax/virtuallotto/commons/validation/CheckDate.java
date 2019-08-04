package kipoderax.virtuallotto.commons.validation;

import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Data
public class CheckDate {

    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
    String after = "21:30:00";
    String before = "22:30:00";

    private LocalTime start = LocalTime.parse(after, format);
    private LocalTime end = LocalTime.parse(before, format);
    private LocalTime current = LocalTime.parse(
            date.toString()
                    .substring(date.toString().length() - 18, date.toString().length() - 10),
            format);

    public boolean isLottery() {

        return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                && (start.isBefore(current) && end.isAfter(current));
    }
}
