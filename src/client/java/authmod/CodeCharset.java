package authmod;

import net.minecraft.text.Text;

public enum CodeCharset {
    BASE32("Base32", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"),
    BASE64("Base64", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"),
    UPPERCASE("Uppercase", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    LOWERCASE("Lowercase", "abcdefghijklmnopqrstuvwxyz"),
    DIGITS("Digits", "0123456789"),
    HEX("Hex", "0123456789ABCDEF"),
    ALPHANUMERIC("Alphanumeric", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");

    private final String label;
    private final String alphabet;

    CodeCharset(String label, String alphabet) {
        this.label = label;
        this.alphabet = alphabet;
    }

    public String alphabet() {
        return alphabet;
    }

    public Text displayText() {
        return Text.literal(label);
    }
}
