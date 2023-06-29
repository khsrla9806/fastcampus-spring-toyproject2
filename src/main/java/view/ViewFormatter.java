package view;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class ViewFormatter {
    public static String getFormatData(String data) {
        if (data == null) {
            return "";
        }
        return String.format("%-10s", data);
    }

    public static String getFormatDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
        return timestamp.toLocalDateTime().format(formatter);
    }
}
