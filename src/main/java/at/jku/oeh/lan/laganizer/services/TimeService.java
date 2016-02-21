package at.jku.oeh.lan.laganizer.services;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Service
public class TimeService {
    public String InstantToString(Instant instant) {
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }

    public Instant StringToInstant(String instant) {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(instant));
    }

}
