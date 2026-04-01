package authmod;

public class CodeGenerator {
    private final char[] alphabet;
    private final int[] digits;
    private boolean exhausted;

    public CodeGenerator(CodeCharset charset, int codeLength) {
        this.alphabet = charset.alphabet().toCharArray();
        this.digits = new int[codeLength];
        this.exhausted = codeLength <= 0 || alphabet.length == 0;
    }

    public String nextCode() {
        if (exhausted) {
            return null;
        }

        char[] code = new char[digits.length];

        for (int index = 0; index < digits.length; index++) {
            code[index] = alphabet[digits[index]];
        }

        advance();
        return new String(code);
    }

    private void advance() {
        // This works like an odometer: the last character moves first.
        for (int index = digits.length - 1; index >= 0; index--) {
            digits[index]++;

            if (digits[index] < alphabet.length) {
                return;
            }

            digits[index] = 0;
        }

        exhausted = true;
    }
}
