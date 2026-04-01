package authmod;

public class AuthModConfig {
    public String command = "register";
    public int codeLength = 4;
    public CodeCharset charset = CodeCharset.BASE32;
    public int delayTicks = 2;

    public AuthModConfig copy() {
        AuthModConfig copy = new AuthModConfig();
        copy.command = command;
        copy.codeLength = codeLength;
        copy.charset = charset;
        copy.delayTicks = delayTicks;
        return copy;
    }

    public void normalize() {
        command = command == null ? "register" : command.trim();

        while (command.startsWith("/")) {
            command = command.substring(1).trim();
        }

        if (command.isEmpty()) {
            command = "register";
        }

        if (codeLength < 1) {
            codeLength = 1;
        }

        if (codeLength > 16) {
            codeLength = 16;
        }

        if (delayTicks < 0) {
            delayTicks = 0;
        }

        if (delayTicks > 200) {
            delayTicks = 200;
        }

        if (charset == null) {
            charset = CodeCharset.BASE32;
        }
    }
}
