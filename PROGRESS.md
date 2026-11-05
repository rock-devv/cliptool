# Progress

## Status

All README plan phases 1-5 are implemented. Phase 6 (manual device testing in Chrome)
still has to be run on a device; the debug build is green.

## Done

- Single-module Android app (Kotlin, minSdk 26, OkHttp + coroutines only).
- Only permission: `INTERNET`.
- `SettingsActivity` (launcher) with the open-link toggle (default off) and the
  expiry spinner (1 day / 7 days / 365 days), persisted in `SharedPreferences`.
- `ShareActivity` (invisible `ACTION_PROCESS_TEXT` handler with a translucent theme):
  reads the selected text (clipboard fallback on window focus), uploads it, copies
  the link, shows a toast and optionally opens the link. Always `RESULT_CANCELED`.
- `PasteProvider` interface with the dpaste.com implementation and a 20 s timeout.
- Gradle 8.7 wrapper committed; `./gradlew assembleDebug` passes.
- dpaste contract smoke-tested: form POST with `content` + `expiry_days` returns 201
  and the `https://dpaste.com/...` link as both body and `Location`.

## Building

Requires JDK 17 and Android SDK platform 34 with build-tools 34.0.0.
Point `sdk.dir` in an untracked `local.properties` at the SDK, then:

```sh
./gradlew assembleDebug
```

## Environment notes

The debug build was verified with Temurin JDK 17 and Android SDK platform 34
installed under the workspace home directory. `local.properties` holds the local
`sdk.dir` and is gitignored.

## Known gaps

- There is no `AGENTS.md` in the repository, so the names and package above follow
  the explicit requirements of issue #2.
- dpaste.com keeps pastes for 1 to 365 days; it has no "never" option, so the expiry
  setting offers 1 day, 7 days and 365 days.
- The upload flow has not been exercised on a device or emulator; only compilation,
  packaging and the HTTP contract were verified here.
