package com.xeno.zuihotspotfix;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HotspotHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        // 1) SystemUI hook
        if ("com.android.systemui".equals(lpparam.packageName)) {
            XposedBridge.log("HotspotFix: Loaded in SystemUI");

            try {
                XposedHelpers.findAndHookMethod(
                        "com.android.systemui.qs.tiles.HotspotTile",
                        lpparam.classLoader,
                        "isAvailable",
                        XC_MethodReplacement.returnConstant(true)
                );
                XposedBridge.log("HotspotFix: Hooked HotspotTile.isAvailable()");
            } catch (Throwable t) {
                XposedBridge.log("HotspotFix: SystemUI hook failed: " + t);
            }

            return;
        }

        // 2) Settings hook
        if ("com.android.settings".equals(lpparam.packageName)) {
            XposedBridge.log("HotspotFix: Loaded in Settings");

            try {
                XposedHelpers.findAndHookMethod(
                        "com.lenovo.common.utils.LenovoUtils",
                        lpparam.classLoader,
                        "isSupportTether",
                        Context.class,
                        XC_MethodReplacement.returnConstant(true)
                );
                XposedBridge.log("HotspotFix: Hooked LenovoUtils.isSupportTether(Context) -> true");
            } catch (Throwable t) {
                XposedBridge.log("HotspotFix: Settings hook failed: " + t);
            }
        }
    }
}