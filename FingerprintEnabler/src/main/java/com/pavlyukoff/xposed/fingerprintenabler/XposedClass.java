package com.pavlyukoff.xposed.fingerprintenabler;

import android.app.Application;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedClass extends Application implements IXposedHookLoadPackage {
	private static final String TAG = "xposed_debug";
	
	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
		
		/*
		if (lpparam.packageName.equals("com.android.internal"))
		{

			findAndHookMethod("com.android.internal.widget.LockPatternUtils$StrongAuthTracker", lpparam.classLoader,
					"isFingerprintAllowedForUser", int.class, new XC_MethodReplacement()
					{
						@Override
						protected Object replaceHookedMethod(MethodHookParam param) throws Throwable
						{
							Log.e("fpEnabler", "isFingerprintAllowedForUser TRUE");
							return true;
						}
					}
			);
			Log.e(TAG, "Hooked : " + lpparam.packageName.toString());

		}
		*/

		//if (lpparam.packageName.equals("com.android.systemui")) {

			findAndHookMethod("com.android.keyguard.KeyguardUpdateMonitor$StrongAuthTracker", lpparam.classLoader,
					"isUnlockingWithFingerprintAllowed", new XC_MethodReplacement()
					{
						@Override
						protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
							//Log.e(TAG, "isUnlockingWithFingerprintAllowed TRUE");
							return true;
						}
					}
			);

			findAndHookMethod("com.android.keyguard.KeyguardUpdateMonitor", lpparam.classLoader,
					"isUnlockingWithFingerprintAllowed", new XC_MethodReplacement()
					{
						@Override
						protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
							//Log.e(TAG, "isUnlockingWithFingerprintAllowed 2 TRUE");
							return true;
						}
					}
			);

			Log.e(TAG, "Hooked : " + lpparam.packageName);

		//}

	}
}
