package utils;
 
public class TestConfig {
 
    public static final String BASE_URL = "https://www.pcx.hu";
 
    public static final String VALID_EMAIL    = System.getenv().getOrDefault("PCX_EMAIL",    "v71lre@inf.elte.hu");
    public static final String VALID_PASSWORD = System.getenv().getOrDefault("PCX_PASSWORD", "Probajelszo123");
 
    public static final String INVALID_EMAIL    = "nemletezik@fake.com";
    public static final String INVALID_PASSWORD = "rosszjelszo123";
 
    public static final String SEARCH_TERM_LAPTOP  = "laptop";
    public static final String SEARCH_TERM_PHONE   = "telefon";
    public static final String SEARCH_TERM_SPECIAL = "!!!###";
 
    public static final int EXPLICIT_WAIT_SECONDS = 10;
 
    private TestConfig() {}
}