package red.bus.user.payload;

import java.util.Date;

public class ErrorDetails {
    Date timeStamp;
    String message;
    String description;

    public ErrorDetails(Date timeStamp, String description, String message) {
        this.timeStamp = timeStamp;
        this.description = description;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
