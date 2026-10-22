# Build the Magic Mirror APK using only your phone

This project includes a GitHub Actions workflow at `.github/workflows/android-apk.yml`.
It builds a debug APK in GitHub's cloud; no Android Studio is needed on your phone.

## Steps
1. Sign in to GitHub in your phone browser and create a **private** repository.
2. Upload the *contents* of this project folder (`README.md`, `API_CONTRACT.md`, `backend/`, `android-app/`, and `.github/`) to the repository root. Do not upload `.env` files or real API keys.
3. Open the repository's **Actions** tab and select **Build Android APK**. If prompted, enable Actions. Tap **Run workflow**. Alternatively, pushing the files should start the workflow automatically.
4. Open the completed workflow run, scroll to **Artifacts**, and download `magic-mirror-debug-apk`. Extract the downloaded artifact ZIP to get `app-debug.apk`.
5. Tap `app-debug.apk` on your phone to install it. Android may ask you to allow your browser/files app to install unknown apps; only allow this for the source you trust, then install.

## Important
- The first build may fail because the Android source has not yet been compiler-verified and the USB camera library integration is unverified. If it fails, open the workflow run and share the first meaningful error from the build log so it can be fixed.
- A successful APK build does **not** mean the try-on service is running. The app also needs the Python backend deployed and reachable over HTTPS, plus a valid device API key and FASHN configuration.
- For a real backend, add a repository **Variable** named `MAGICMIRROR_BASE_URL` containing the backend's public HTTPS base URL, and a repository **Secret** named `MAGICMIRROR_DEVICE_API_KEY` containing the same device key configured on the backend. Then rerun the workflow. Do not put a FASHN API key in the Android app or GitHub variables.
- The default URL `http://10.0.2.2:8000` is only for an Android emulator and will not reach a backend from a physical phone. Configure the public backend URL before expecting API features to work.
