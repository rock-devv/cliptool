# Progress

## Status

Skeleton renamed to the final component names. The upload flow, result handling and
settings UI are the remaining README plan phases.

## Done

- Single-module Android app (Kotlin, minSdk 26, OkHttp + coroutines only).
- Only permission: `INTERNET`.
- `SettingsActivity` (launcher) and `ShareActivity` (invisible `ACTION_PROCESS_TEXT`
  handler with a translucent theme).
- Gradle 8.7 wrapper committed; `./gradlew assembleDebug` passes.

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
