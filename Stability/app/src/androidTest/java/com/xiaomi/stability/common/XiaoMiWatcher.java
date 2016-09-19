package com.xiaomi.stability.common;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiWatcher;
import android.util.Log;

import java.util.regex.Pattern;

/**
 * Created by plasma on 16-9-18.
 */
public class XiaoMiWatcher {
    public static final String TAG = XiaoMiWatcher.class.getSimpleName();
    public static UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    public static final UiWatcher anrWatcher = new UiWatcher() {
        @Override
        public boolean checkForCondition() {
            UiObject2 anrWindows = device.findObject(By.textContains("无响应"));
            if (anrWindows != null) {
                //to do something
                return true;
            } else {
                //to do something
                return false;
            }
        }
    };

    public static final UiWatcher fcWatcher = new UiWatcher() {
        @Override
        public boolean checkForCondition() {
            UiObject2 fcWindows = device.findObject(By.textContains("已停止运行。"));
            if (fcWindows != null) {
                UiObject2 okBtn = device.findObject(By.text("确定"));
                okBtn.click();
//                okBtn.click();
                Log.i(TAG, "111111111111111111111111111111111");
                return true;
            } else {
                //to do something
                return false;
            }
        }
    };
}
