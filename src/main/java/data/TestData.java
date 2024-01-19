package data;

import static utils.PropertiesUtils.getEnv;

public class TestData {
    public static final Environment ENVIRONMENT = getEnvironment();
    public static final String LOGGER_INDENT = "\n===============================================";

    private static Environment getEnvironment() {
        String envParameter = getEnv();
        try {
            return Environment.valueOf(envParameter.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ExceptionInInitializerError("Not supported environment format: [" + envParameter + "].\n" +
                    "Please use one of these: prod / rc / dev / eval / eu / stage.");
        }
    }
}
