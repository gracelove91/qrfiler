# QRfiler

📁 **Share files over LAN with a QR code — no cloud, no signup.**

QRfiler is a minimal desktop app that turns file sharing into a single gesture: drag a file into the window, scan the QR code with your phone, and download. It runs a temporary local HTTP server behind a random token URL so only people on the same Wi-Fi can access it.

---

## Features

- **Drag & drop** or file chooser
- **QR code** generation for instant mobile download
- **Random hex token** URLs (e.g. `http://192.168.0.5:52341/a3f9b2e1`) instead of fixed routes
- **No external server** — everything stays on your local network
- **Lightweight** — Kotlin + Compose Desktop native UI

---

## Tech Stack

- Language: Kotlin 1.9.22
- UI: [Compose Desktop](https://github.com/JetBrains/compose-multiplatform) 1.6.11
- QR Code: [ZXing](https://github.com/zxing/zxing) core 3.5.3
- HTTP Server: `com.sun.net.httpserver.HttpServer` (JDK built-in)

---

## Installation

### macOS (Apple Silicon)

#### Option 1: Homebrew (recommended)

```bash
brew tap gracelove91/tap
brew install --cask qrfiler
# Then remove quarantine so it opens without the security popup:
sudo xattr -cr /Applications/qrfiler.app
```

#### Option 2: Download from [Releases](../../releases)

1. Download `qrfiler-vX.X.X-macos-arm64.zip`
2. Unzip and move `qrfiler.app` to `/Applications`
3. Remove quarantine:
   ```bash
   xattr -cr /Applications/qrfiler.app
   ```
4. Launch from Launchpad or Spotlight

> **Why `xattr -cr`?** The app is ad-hoc signed (no Apple Developer ID). macOS Gatekeeper blocks internet-downloaded unsigned apps by default. Removing the quarantine attribute is the only way to avoid the "malicious software" popup without a $99/year Apple Developer account.

### Build from source

```bash
./gradlew createDistributable
# macOS app bundle: build/compose/binaries/main/app/qrfiler.app
```

---

## Usage

1. Launch the app.
2. Drag & drop a file (or folder) onto the window, or click **파일/폴더 선택**.
3. Click **공유 시작**.
4. Scan the QR code with your phone camera, or enter the URL in a browser.
5. Click **공유 중지** when done — the server shuts down immediately.

> Both devices must be on the same Wi-Fi / LAN.

---

## Security

- URLs contain an unpredictable 16-character hex token generated with `SecureRandom`.
- The HTTP server listens on a random ephemeral port.
- No data leaves your local network; QR codes encode LAN URLs only.
- If a VPN interface is active, QRfiler skips it and advertises the physical LAN IP instead.

---

## Download

| Platform | Format |
|----------|--------|
| macOS (arm64) | `.zip` containing `.app` bundle |

See [Releases](../../releases) for binaries.

---

## License

MIT

---

# 🇰🇷 한국어

QRfiler는 같은 Wi-Fi 내에서 파일을 QR 코드로 빠르게 공유하는 경량 데스크톱 앱입니다. 클라우드나 외부 서버 없이, 로컬 네트워크에서만 파일이 전송됩니다.

## 주요 기능

- 드래그 앤 드롭 또는 파일 선택
- QR 코드 생성으로 휴대폰에서 즉시 다운로드
- 랜덤 hex 토큰 URL로 보안 강화 (예: `http://192.168.0.5:52341/a3f9b2e1`)
- 외부 서버 없이 로컬 네트워크에서만 동작

## 설치

### macOS (Apple Silicon)

#### 방법 1: Homebrew (권장)

```bash
brew tap gracelove91/tap
brew install --cask qrfiler
# 보안 팝업 없이 실행하려면 quarantine 제거:
sudo xattr -cr /Applications/qrfiler.app
```

#### 방법 2: [Releases](../../releases)에서 다운로드

1. `qrfiler-vX.X.X-macos-arm64.zip` 다운로드
2. 압축 해제 후 `qrfiler.app`을 `/Applications`로 이동
3. 터미널에서 quarantine 제거:
   ```bash
   xattr -cr /Applications/qrfiler.app
   ```
4. Launchpad 또는 Spotlight에서 실행

> **왜 `xattr -cr`가 필요한가?** 이 앱은 Apple Developer ID 없이 ad-hoc 서명만 되어 있습니다. macOS는 인터넷에서 받은 서명되지 않은 앱을 기본적으로 차단합니다. 연 $99/year 개발자 계정 없이는 quarantine 속성을 제거하는 것이 팝업을 피는 유일한 방법입니다.

### 소스에서 빌드

```bash
./gradlew createDistributable
# 앱 번들: build/compose/binaries/main/app/qrfiler.app
```

## 사용 방법

1. 앱을 실행합니다.
2. 파일을 창에 드래그하거나 **파일/폴더 선택** 버튼을 클릭합니다.
3. **공유 시작**을 클릭하면 QR 코드가 생성됩니다.
4. 휴대폰 카메라로 QR 코드를 스캔하거나 브라우저에 URL을 입력합니다.
5. 완료 후 **공유 중지**를 클릭하면 서버가 즉시 종료됩니다.

두 기기는 반드시 같은 Wi-Fi에 연결되어 있어야 합니다.

## 보안

- 16자 hex 토큰은 `SecureRandom`으로 생성되어 예측이 불가능합니다.
- HTTP 서버는 랜덤 임시 포트에서만 수신합니다.
- 데이터는 로컬 네트워크를 벗어나지 않습니다.
- VPN이 켜져 있어도 물리 LAN IP를 우선 사용합니다.

## 라이선스

MIT
