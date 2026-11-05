# Pastebin

Android app (Kotlin, minSdk 26, OkHttp + coroutines only) that adds a "Pastebin"
action to the text selection toolbar. Uploads selected text, copies the URL, optionally opens it.

Only permission: `INTERNET`.

## Plan
1. Skeleton (done): single module, `SettingsActivity` (launcher), `ShareActivity` (invisible).
2. Selection menu action (`ACTION_PROCESS_TEXT`, translucent theme, clipboard fallback on focus, always `RESULT_CANCELED`).
3. Upload via swappable provider interface (default dpaste.com; verify current API docs first), coroutine + timeout, toast errors.
4. Result: copy URL, "Link copied" toast, optional `ACTION_VIEW`.
5. Settings: open-link toggle (default off), expiry dropdown (1 day / 7 days / never).
6. Manual test in Chrome (⋮ overflow of selection toolbar), toggle on/off, airplane mode, very long text.
