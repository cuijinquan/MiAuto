package com.xiaomi.stability.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;

import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.xiaomi.stability.common.XiaoMiWatcher.*;
import static java.lang.Thread.sleep;

/**
 * Created by plasma on 16-9-18.
 */

@RunWith(JUnit4.class)
public class XiaoMiTestCase {
    public static Instrumentation instrumentation;
    public static UiDevice device;
    public static String TAG = XiaoMiTestCase.class.getSimpleName();
    private Bundle params;

    //key code
    public static final int KEY_VOLUME_UP = 24;
    public static final int KEY_VOLUME_DOWN = 25;
    public static final int KEY_VOLUME_MUTE = 164;
    public static final int KEY_CHANNEL_UP = 166;
    public static final int KEY_CHANNEL_DOWN = 167;
    public static final int KEY_SETTING = 176;
    public static final int KEY_SHOW_DISPLAY = 165;
    public static final int KEY_SIGNAL_SOURCE = 4404;
    public static final int KEY_UP = 19;
    public static final int KEY_DOWN = 20;
    public static final int KEY_LEFT = 21;
    public static final int KEY_RIGHT = 22;
    public static final int KEY_CENTER = 23;
    public static final int KEY_BACK = 4;
    public static final int KEY_HOME = 3;
    public static final int KEY_MENU = 82;
    public static final int KEYCODE_DEL = 67;

    //multimedia code
    public static final int KEY_MEDIA_PRE = 88;
    public static final int KEY_MEDIA_NEXT = 87;
    public static final int KEY_MEDIA_FAST_FORWARD = 90;
    public static final int KEY_MEDIA_REWIND = 89;
    public static final int KEY_MEDIA_PLAY_PAUSE = 85;
    public static final int KEY_MEDIA_STOP = 86;

    //signal source
    public static final int KEY_TV = 170;
    public static final int KEY_VGA = 4401;
    public static final int KEY_VOICE = 4409;
    // public static final int KEY_HDMI = 269;
    public static final int KEY_HDMI = 4435;
    public static final int KEY_CVBS = 4402;

    //number code
    public static final int KEY_NUM0 = 7;
    public static final int KEY_NUM1 = 8;
    public static final int KEY_NUM2 = 9;
    public static final int KEY_NUM3 = 10;
    public static final int KEY_NUM4 = 11;
    public static final int KEY_NUM5 = 12;
    public static final int KEY_NUM6 = 13;
    public static final int KEY_NUM7 = 14;
    public static final int KEY_NUM8 = 15;
    public static final int KEY_NUM9 = 16;

    public static Map<String,String> appList = new HashMap<>();
    public ArrayList<String> listApps = new ArrayList<String>();


    public Map<String,String> initMap() {
        Map<String,String> map = new HashMap<>();
        map.put("TV桌面",ActivityName.TvHome);
        map.put("高清播放器",ActivityName.VideoPlayer);
        map.put("电视频道",ActivityName.TvPlayer);
        map.put("应用商店",ActivityName.AppStore);
        map.put("游戏中心",ActivityName.GameCenter);
        map.put("多媒体浏览器",ActivityName.MediaExplorer);
        map.put("设置",ActivityName.Settings);
        map.put("智能家庭TV",ActivityName.SmartHomeTV);
        map.put("小米商城",ActivityName.XiaoMiShop);
        map.put("米果音乐",ActivityName.MiGuoMusic);
        map.put("小米电视手册",ActivityName.TVHandBook);
        map.put("电视管家",ActivityName.TVManager);
        map.put("日历",ActivityName.Calendar);
        map.put("无线投屏",ActivityName.SmartShare);
        map.put("通知中心",ActivityName.NotificationCenter);
        map.put("时尚画报",ActivityName.Gallery);
        return map;
    }

    public ArrayList<String> appNameList() {
        ArrayList<String> list = new ArrayList<String>() {
            {
                add("高清播放器");
                add("电视频道");
                add("应用商店");
                add("游戏中心");
                add("多媒体浏览器");
                add("设置");
                add("智能家庭TV");
                add("小米商城");
                add("米果音乐");
                add("小米电视手册");
                add("电视管家");
                add("日历");
                add("无线投屏");
                add("通知中心");
                add("时尚画报");
            }
        };
        return list;
    }

    @Before
    public void setUp() throws IOException {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        params = InstrumentationRegistry.getArguments();
        Log.i(TAG, "===============start the test case!");
        device.pressBack();
        appList = initMap();
        listApps = appNameList();
        registerCommonWatcher();
    }

    @After
    public void tearDown() throws IOException {
        Log.i(TAG, "===============end the test case!");
        device.pressBack();
        device.pressBack();
        device.pressBack();
        device.pressHome();
        unregisterCommonWatcher();
    }

    public boolean openApp(String app) throws InterruptedException, RemoteException, IOException {
        return openApp(app,true);
    }

    public boolean openApp(String app, boolean flag) throws RemoteException, InterruptedException, IOException {
        if (app == "" || app == null) {
            Log.i(getClass().getSimpleName(),"the App name can't be null");
            return false;
        }
        // verify if the app is a valid App
        if (!(appList.containsKey(app))) {
            //screenShot();
            if (flag) {
                junit.framework.Assert.fail("The app " + app + "is not a valid App, please check the App name.");
            }
            System.out.println("The app " + app + "is not a valid App, please check the App name.");
            return false;
        }
        // back to app desktop
        device.pressBack();
        device.pressBack();
        device.pressBack();
        device.pressHome();
        /*for (int i = 0; i < 4; i++) {
            UiObject2  appItem = device.findObject(By.pkg("com.mitv.tvhome").text(Pattern.compile(app)));
            if (appItem != null) { // find the app on current window
                appItem.click();
                sleep(1000);
                //appItem.click();
                UiObject2 appItem1 = device.findObject(By.pkg("com.stv.opener").text(Pattern.compile(app)));
                if (appItem1 != null) {
                    appItem1.click();
                    sleep(1000);
                    UiObject2 appItem2 = device.findObject(By.pkg("com.stv.opener").text(Pattern.compile(app)));
                    if(appItem2!=null){
                        appItem2.click();
                        sleep(1);
                    }
                }
                Assert.assertEquals("Can not found " + app, true, device.findObject(By.pkg(Pattern.compile(appList.get(app)))));
                return true;
            } else {
                device.executeShellCommand("input keyevent 20");
                device.executeShellCommand("input keyevent 20");
                sleep(1000);
            }
        }*/

        if (device.getCurrentPackageName() == "com.mitv.tvhome") {
            Log.i(TAG, "当前不是Home桌面，已打开的应用是：" + device.getCurrentPackageName());
        }
        //打开app
        device.executeShellCommand("am start -S "+appList.get(app));
        Log.i(TAG, "appList.get(app)=============="+appList.get(app));
        sleep(5000);
        registerCommonWatcher();
        device.runWatchers();
        if (device.getCurrentPackageName() != "com.mitv.tvhome") {
            Log.i(TAG, "当前不是Home桌面，已打开的应用是：" + device.getCurrentPackageName());
        }
        if (flag) {
            junit.framework.Assert.fail("could not find the App " + app + ".");
        }
        return false;
    }

    protected void registerCommonWatcher() {
        device.registerWatcher("fcWatcher", XiaoMiWatcher.fcWatcher);
        device.registerWatcher("anrWatcher", XiaoMiWatcher.anrWatcher);
    }

    protected void unregisterCommonWatcher() {
        device.removeWatcher("anrWatcher");
        device.removeWatcher("fcWatcher");
    }
}
