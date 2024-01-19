package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Environment {
    DEV(""),
    RC(""),
    PROD("https://stackoverflow.com");

    private final String host;
}
