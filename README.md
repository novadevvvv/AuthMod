# Auth Mod

Client-side Fabric mod for Minecraft 1.21.1.

What it does:
- Lets you set a command like `register`
- Lets you choose a code length and charset like base32 or base64
- Generates codes in order like `AAAA`, `AAAB`, `AAAC`
- Sends `/<command> <code>` while the runner is active
- Gives you keybinds for open menu, start/stop, and pause/resume

How to use:
1. Open the mod config screen from Mod Menu or bind the `Open Auth Mod Menu` key.
2. Set the command, code length, charset, and delay.
3. Use the `Start Or Stop Auth Mod` key to begin or end the runner.
4. Use the `Pause Or Resume Auth Mod` key to pause without losing your current run.

Notes:
- The command field should not include the leading slash. Use `register`, not `/register`.
- Delay is measured in client ticks. `0` means it tries every tick.
- This project does not include the Gradle wrapper files. If you want them, run `gradle wrapper` on a machine with Gradle installed.
