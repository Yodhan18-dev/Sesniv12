# 🎮 BDK Jupiter Sensi Generator

> **Free Fire Sensitivity Generator for Android**  
> Developer: **BDK JUPITER** | Hyderabad, India

---

## 📱 App Features

- 🔐 **Device-locked password system** — each BDK key works on ONE device only
- ⚡ **Sensitivity formula** by BDK JUPITER — tailored per brand (Redmi/Poco/Xiaomi, Vivo/Oppo, iQOO, OnePlus/Samsung, Infinix/Realme)
- 🎯 Generates: General, Red Dot, 2x, 4x, Sniper sensitivity + DPI + Fire Button size
- 📜 **History** — saves every generation locally
- 📋 **Copy All** — share settings instantly
- 🌑 Dark neon gaming theme with animations & particle effects
- 🔊 Gaming sound effects

---

## 🏗️ Project Structure

```
BDKJupiter/
├── app/src/main/
│   ├── java/com/bdkjupiter/sensi/
│   │   ├── ui/
│   │   │   ├── splash/     SplashActivity, ParticleView
│   │   │   ├── login/      LoginActivity (device-lock logic)
│   │   │   ├── home/       MainActivity, HomeFragment
│   │   │   ├── generate/   GenerateFragment
│   │   │   └── history/    HistoryFragment, HistoryAdapter
│   │   ├── data/           GenerationRecord (data model)
│   │   └── utils/          SensitivityCalculator, PrefsManager, SoundManager
│   └── res/
│       ├── layout/         All XML layouts
│       ├── drawable/       Icons + logo
│       ├── anim/           Zoom, fade, slide, shake, pulse animations
│       ├── values/         Colors (dark neon), Strings, Themes
│       ├── menu/           Bottom nav menu
│       ├── raw/            Sound files (replace with your meme sounds)
│       └── font/           Orbitron font (download separately)
├── .github/workflows/      GitHub Actions CI/CD → builds APK automatically
└── gradle/                 Build config
```

---

## 🚀 Build Instructions

### Option 1 — Android Studio (recommended)

1. **Prerequisites:** Android Studio Hedgehog (2023.1) or newer, JDK 17
2. Open Android Studio → **File → Open** → select the `BDKJupiter/` folder
3. Wait for Gradle sync to finish
4. **Add the Orbitron font:**
   - Download `Orbitron-Bold.ttf` from [Google Fonts](https://fonts.google.com/specimen/Orbitron)
   - Place it at `app/src/main/res/font/orbitron.ttf`
   - Delete the placeholder `orbitron.xml` file
5. **Replace sound files** in `app/src/main/res/raw/`:
   - `sound_yes.wav` → your "yesss!" meme sound
   - `sound_no.wav` → your "noo!" meme sound
   - `sound_generate.wav` → your "fhaa!" / generate sound
   - `sound_click.wav` → click/tap sound
6. **Replace logo** if needed: `app/src/main/res/drawable/logo.png`
7. Click **▶ Run** or **Build → Build Bundle(s)/APK(s) → Build APK(s)**
8. APK will be at: `app/build/outputs/apk/debug/app-debug.apk`

### Option 2 — GitHub Actions (auto-build)

1. Push this project to a GitHub repository
2. Go to **Actions** tab → the `Build APK` workflow runs automatically on every push
3. After the workflow completes, download the APK from the **Artifacts** section
4. Two artifacts are produced:
   - `BDKJupiter-debug-apk` — ready to install and test
   - `BDKJupiter-release-apk` — unsigned release build

> **Note:** The `gradle-wrapper.jar` included is a placeholder. GitHub Actions  
> will automatically download the real Gradle wrapper on first run.  
> For local builds, Android Studio handles this automatically.

---

## 🎨 Customization

### Add Background Music
1. Place your `.mp3` file at `app/src/main/res/raw/bg_music.mp3`
2. In `MainActivity.kt`, add a `MediaPlayer` to loop the music

### Replace Sound Effects
Drop your custom `.wav` or `.mp3` files into `res/raw/` matching the names:
- `sound_yes`, `sound_no`, `sound_generate`, `sound_click`

### Dark/Light Mode Toggle
Currently hardcoded to dark mode. To add a toggle button, use:
```kotlin
PrefsManager(this).setDarkMode(!prefs.isDarkMode())
AppCompatDelegate.setDefaultNightMode(
    if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
)
```

---

## 🔑 Password System

- 100 fixed BDK keys are hardcoded in `LoginActivity.kt`
- On first use, a key is **locked to that device's Android ID**
- Attempting to use the same key on a second device shows an error
- Locked devices are stored in `SharedPreferences` permanently

---

## 📞 Developer Links

| Platform | Link |
|----------|------|
| 📺 YouTube | https://youtube.com/@bdk-jupiter |
| 📸 Instagram | https://www.instagram.com/bdk_jupiter_ff |
| 💬 Discord | https://discord.gg/ztTfSavsf |

---

## ⚠️ Notes

- Min SDK: **Android 7.0 (API 24)**
- Target SDK: **Android 14 (API 34)**
- Language: **Kotlin**
- Architecture: **Single Activity + Fragments**
- Storage: **SharedPreferences** (no internet required for core features)

> "Keep practicing for 2 weeks to set ur hands on the sensi" — BDK JUPITER
