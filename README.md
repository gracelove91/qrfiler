# qrfiler

Kotlin + Compose Desktop 기반 로컬 파일 공유 앱. 파일이나 폴더를 드래그하면 로컬 HTTP 서버를 실행하고, 같은 와이파이 내 다른 기기가 QR 코드로 접속하여 다운로드할 수 있다.

## Stack

| Layer | Choice |
|-------|--------|
| Language | Kotlin 1.9 |
| UI | Compose Desktop (JetBrains) |
| HTTP Server | `com.sun.net.httpserver.HttpServer` |
| QR Code | ZXing |

## macOS Gatekeeper — First Launch

This app is ad-hoc signed only (no Apple Developer ID notarization). Because you downloaded it from the internet, macOS may show:

> "Apple cannot check it for malicious software."

**Workaround**: Right-click the app → **Open** instead of double-click.

Or remove quarantine via Terminal:

```bash
xattr -dr com.apple.quarantine /path/to/qrfiler.app
```

If needed, also allow in **System Settings → Privacy & Security**.

## Development

```bash
# Run
./gradlew run

# Package macOS .app and auto-sign (adhoc)
./gradlew createDistributable
```
