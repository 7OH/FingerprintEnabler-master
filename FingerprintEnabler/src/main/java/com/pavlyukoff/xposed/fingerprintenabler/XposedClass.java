package com.pavlyukoff.xposed.fingerprintenabler;

import android.app.Application;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedClass extends Application implements IXposedHookLoadPackage {
	private static final String TAG = "FPE_debug";
	private boolean isDebug = BuildConfig.DEBUG;

	private void LogE(Throwable e) {
		if(isDebug)
			Log.e(TAG, "Exception", e);
	}

	private void LogE(boolean hooked, String className, String methodName) {
		if(!isDebug)
			return;

		Log.e(TAG,
				"Hook : " + (hooked?"YES":"no") + " : " + methodName + " : " + className);
	}

	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

		boolean hooked;
		String className;
		String methodName;

		if (lpparam.packageName.equals("com.android.systemui")) {

			className	= "com.android.keyguard.KeyguardUpdateMonitor";

			// 00.00 -------------------------------------------------------------------------------
			//this MUST disable request PIN at all - DANGEROUS
			/*
			methodName	= "getUserCanSkipBouncer";
			try {
				hooked = true;
				findAndHookMethod(className, lpparam.classLoader, methodName,
						int.class,
						new XC_MethodReplacement()
						{
							@Override
							protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
								return true;
							}
						}
				);
			} catch (Throwable e){
				hooked = false;
				LogE(e);
			}
			LogE(hooked, className, methodName);
			*/

			// 00.01 -------------------------------------------------------------------------------
			methodName	= "isUnlockingWithFingerprintAllowed";
			try {
				hooked = true;
				findAndHookMethod(className, lpparam.classLoader, methodName,
						XC_MethodReplacement.returnConstant(true));

			} catch (Throwable e){
				hooked = false;
				LogE(e);
			}
			LogE(hooked, className, methodName);

			// 00.02 -------------------------------------------------------------------------------
			methodName	= "isUnlockWithFingerPrintPossible";
			try {
				hooked = true;
				findAndHookMethod(className, lpparam.classLoader, methodName,
						int.class,
						XC_MethodReplacement.returnConstant(true));
			} catch (Throwable e) {
				hooked = false;
				LogE(e);
			}
			LogE(hooked, className, methodName);

			// 00.03 -------------------------------------------------------------------------------
			methodName	= "isFingerprintDisabled";
			try {
				findAndHookMethod(className, lpparam.classLoader, methodName,
						int.class,
						XC_MethodReplacement.returnConstant(false));
			} catch (Throwable e) {
				hooked = false;
				LogE(e);
			}
			LogE(hooked, className, methodName);

			// 00.04 -------------------------------------------------------------------------------
			methodName	= "isUnlockCompleted";
			try {
				findAndHookMethod(className, lpparam.classLoader, methodName,
						XC_MethodReplacement.returnConstant(true));
			} catch (Throwable e) {
				hooked = false;
				LogE(e);
			}
			LogE(hooked, className, methodName);

			// 01.00 -------------------------------------------------------------------------------
			className	= "com.android.keyguard.KeyguardUpdateMonitor$StrongAuthTracker";
			methodName	= "isUnlockingWithFingerprintAllowed";
			try {
				hooked = true;
				findAndHookMethod(className, lpparam.classLoader, methodName,
						XC_MethodReplacement.returnConstant(true));
			} catch (Throwable e){
				hooked = false;
				LogE(e);
			}
			LogE(hooked, className, methodName);

			// 02.00 -------------------------------------------------------------------------------
			className	= "com.android.internal.widget.LockPatternUtils$StrongAuthTracker";
			methodName	= "isFingerprintAllowedForUser";
			try
			{
				hooked = true;
				findAndHookMethod(className, lpparam.classLoader, methodName,
						int.class,
						XC_MethodReplacement.returnConstant(true));
			} catch (Throwable e){
				hooked = false;
				LogE(e);
			}
			LogE(hooked, className, methodName);

		}

	}
}
