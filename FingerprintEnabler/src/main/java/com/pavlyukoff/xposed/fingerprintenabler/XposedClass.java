package com.pavlyukoff.xposed.fingerprintenabler;

import android.app.Application;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedClass extends Application implements IXposedHookLoadPackage {
	private static final String TAG = "xposed_debug";
	private boolean isDebug = BuildConfig.DEBUG;
	
	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

		boolean hooked;

		//if (lpparam.packageName.equals("com.android.systemui")) {

			try
			{
				hooked = true;
				findAndHookMethod("com.android.internal.widget.LockPatternUtils$StrongAuthTracker", lpparam.classLoader,
						"isFingerprintAllowedForUser", int.class, new XC_MethodReplacement()
						{
							@Override
							protected Object replaceHookedMethod(MethodHookParam param) throws Throwable
							{
								if(isDebug)
									Log.e(TAG, "isFingerprintAllowedForUser for <" + lpparam.packageName + ">");
								return true;
							}
						}
				);
			} catch (Exception ex){
				hooked = false;
			}
			if (hooked)
				if(isDebug)
					Log.e(TAG, "Hooked 0 " + lpparam.packageName);

			//this MUST disable request PIN at all - DANGEROUS
			/*
			try {
				hooked = true;
				findAndHookMethod("com.android.keyguard.KeyguardUpdateMonitor", lpparam.classLoader,
						"getUserCanSkipBouncer", int.class, new XC_MethodReplacement()
						{
							@Override
							protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
								if(isDebug)
									Log.e(TAG, "getUserCanSkipBouncer for <" + lpparam.packageName + ">");
								return true;
							}
						}
				);
			} catch (Exception ex){
				hooked = false;
			}

			if (hooked)
				if(isDebug)
					Log.e(TAG, "Hooked 1: " + lpparam.packageName);
			*/

			try {
				hooked = true;
				findAndHookMethod("com.android.keyguard.KeyguardUpdateMonitor", lpparam.classLoader,
						"isUnlockingWithFingerprintAllowed", new XC_MethodReplacement() {
							@Override
							protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
								if(isDebug)
									Log.e(TAG, "isUnlockingWithFingerprintAllowed for <" + lpparam.packageName + ">");
								return true;
							}
						}
				);

			} catch (Exception ex){
				hooked = false;
			}
			if (hooked)
				if(isDebug)
					Log.e(TAG, "Hooked 2: " + lpparam.packageName);

			try {
				hooked = true;
				findAndHookMethod("com.android.keyguard.KeyguardUpdateMonitor$StrongAuthTracker", lpparam.classLoader,
						"isUnlockingWithFingerprintAllowed", new XC_MethodReplacement() {
							@Override
							protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
								if(isDebug)
									Log.e(TAG, "isUnlockingWithFingerprintAllowed.Strong for <" + lpparam.packageName + ">");
								return true;
							}
						}
				);
			}
			catch (Exception ex)
			{
				hooked = false;
			}
			if (hooked)
				if(isDebug)
					Log.e(TAG, "Hooked 3: " + lpparam.packageName);

			/*
			try {
				hooked = true;
				findAndHookMethod("com.android.internal.widget.isFingerPrintEnabled", lpparam.classLoader,
						"isLockFingerprintEnabled", int.class, new XC_MethodReplacement() {
							@Override
							protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
								if(isDebug)
									Log.e(TAG, "isLockFingerprintEnabled for <" + lpparam.packageName + ">");
								return true;
							}
						}
				);
			}
			catch (Exception ex)
			{
				hooked = false;
			}
			if (hooked)
				if(isDebug)
					Log.e(TAG, "Hooked 4: " + lpparam.packageName);
			*/

		//}

	}
}
