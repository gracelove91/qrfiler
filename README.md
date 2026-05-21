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

Download the latest release from [Releases](../../releases), unzip, and run:

```bash
./qrfiler.app/Contents/MacOS/qrfiler
```

Or open `qrfiler.app` directly. (If Gatekeeper blocks it, right-click → Open.)

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
