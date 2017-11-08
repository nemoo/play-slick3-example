package utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;


public class GelfAppender extends AppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
