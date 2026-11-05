# Pastebin

Android app (Kotlin, minSdk 26, OkHttp + coroutines only) that adds a "Pastebin"
action to the text selection toolbar. Uploads selected text, copies the URL, optionally opens it.

Only permission: `INTERNET`.

## Usage

1. Select text in an app that supports `ACTION_PROCESS_TEXT` (for example Chrome).
2. Open the selection toolbar's overflow menu and choose **Pastebin**.
3. The text is uploaded to [dpaste.com](https://dpaste.com) and the link is copied
   to the clipboard ("Link copied").
4. If **Open link after upload** is enabled, the link is opened in a browser.

If the action is started without selected text, the app falls back to the clipboard
once its window has focus. The activity is invisible and always finishes with
`RESULT_CANCELED`, so the selected text is never replaced.

## Settings

Launch the app from the launcher:

- **Open link after upload** — off by default; when off the link is only copied.
- **Keep paste for** — 1 day, 7 days, or 365 days.

dpaste.com keeps pastes for 1 to 365 days and has no "never" option, so the longest
choice is 365 days (the site's maximum). The default is 7 days.

Uploads use dpaste's [HTTP API v2](https://dpaste.com/api/v2/): a form-encoded
`POST` to `https://dpaste.com/api/v2/` with `content` and `expiry_days`, whose 201
response carries the new item's URL. Requests send a `User-Agent` header and time
out after 20 seconds.

## Build

Requires JDK 17 and Android SDK platform 34 with build-tools 34.0.0. Set `sdk.dir` in
an untracked `local.properties`, then:

```sh
./gradlew assembleDebug
```

The Gradle 8.7 wrapper matches the project's AGP 8.5.2.

## Structure

- `SettingsActivity` — launcher settings screen (open-link toggle, expiry spinner).
- `ShareActivity` — invisible `ACTION_PROCESS_TEXT` handler; upload, copy, optional open.
- `PasteProvider` — swappable upload interface; `DpasteProvider` is the default.
- `Settings` — `SharedPreferences` storage.

## Manual test checklist

- Chrome: select text, use the toolbar overflow, choose Pastebin; check the toast and clipboard.
- Toggle "Open link after upload" off and on.
- Turn on airplane mode: the app shows a failure toast and does not crash.
- Paste a very long selection.
- Start the action without selected text (clipboard fallback).
