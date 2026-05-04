# ZuiHotspotFix

LSPosed / Xposed module to re‑enable the hotspot Quick Settings tile and tethering settings page on Lenovo ZUXOS‑based tablets where hotspot is disabled by the OEM on global ROMs.

> ⚠️ **Experimental / device‑specific**  
> Tested only on **Lenovo Idea Tab Pro** running **ZUXOS 1.5.10.060 (Android 16)**, China ROM converted to global manually.  
> Use at your own risk. Other Lenovo devices / firmware versions may behave differently.

---

## Features

- Forces the **Hotspot** Quick Settings tile to always be available in System UI.
- Forces Lenovo’s internal tethering support check to always return `true`, which re‑enables the **Hotspot / Tethering** page in Settings (if present in your ROM).
- No smali or system file editing – all changes are done at runtime via LSPosed hooks.

---

## How it works (technical)

> For developers and power users.

- Hooks `com.android.systemui.qs.tiles.HotspotTile` (or OEM‑specific equivalent) to override the method that decides tile availability, returning `true` unconditionally.
- Hooks `com.lenovo.common.utils.LenovoUtils.isSupportTether(android.content.Context)` in the **Settings** process and always returns `true`, bypassing OEM checks that hide tethering on some global builds.
- Uses the classic Xposed API (`de.robv.android.xposed:api:82`) and runs under LSPosed.

No system partitions are modified; everything is runtime‑only via LSPosed.

---

## Requirements

- Rooted Android device with **LSPosed** (or compatible) installed.
- Device/ROM based on **Lenovo ZUXOS** where:
  - Hotspot hardware is present and works on other regions, but
  - The hotspot tile and/or tethering page are disabled on global firmware.
- Android 16 on Lenovo Idea Tab Pro confirmed; other devices/versions are **untested**.

---

## Installation

1. Download the latest APK from the [Releases](https://github.com/Xeno761/ZuiHotspotFix/releases) page.
2. Install the APK like a normal app.
3. Open **LSPosed**:
   - Go to **Modules** and enable **ZuiHotspotFix**.
   - In **Scopes**, enable at least:
     - `com.android.systemui`
     - `com.android.settings`
4. Reboot your device.

After reboot:

- The Hotspot Quick Settings tile should appear or become selectable.
- The Hotspot / Tethering page should show up in Settings (if present in your ROM).

---

## Known limitations

- Developed and tested only on:
  - **Lenovo Idea Tab Pro**
  - **ZUXOS 1.5.10.060 (Android 16)**, China ROM manually converted to global.
- Other Lenovo models / ZUXOS versions may:
  - Use different class or method names, or
  - Have additional region checks that this module does not handle.
- If Lenovo changes SystemUI or Settings significantly in an update, the hooks may stop working or need to be updated.

---

## Troubleshooting

- **Module doesn’t show in LSPosed**
  - Make sure the app is installed and `xposedminversion` metadata is present in `AndroidManifest.xml`.
- **Tile still missing**
  - Confirm LSPosed scope includes `com.android.systemui`.
  - Check LSPosed logs for messages from `ZuiHotspotFix` and verify the hook is applied.
- **Hotspot page still hidden in Settings**
  - Confirm LSPosed scope includes `com.android.settings`.
  - Ensure Lenovo has not removed the hotspot UI entirely from your build; this module only bypasses the support check, it cannot recreate fully removed UIs.

---

## Safety

- No system files or partitions are modified.
- All changes happen at runtime in the app processes via LSPosed hooks.
- Uninstalling the module and rebooting restores stock behavior.

That said, this is still a root‑level modification – use at your own risk.

---

## Credits

- LSPosed and Xposed developers for the hooking framework and APIs.
- Lenovo / ZUXOS for the base firmware.

Module by **@Xeno761**.
