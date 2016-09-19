package com.xiaomi.stability.cases;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.xiaomi.stability.common.ActivityName;
import com.xiaomi.stability.common.XiaoMiTestCase;
import com.xiaomi.stability.common.XiaoMiWatcher;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

/**
 * Created by plasma on 16-9-18.
 */
public class LaunchApps extends XiaoMiTestCase {

    public static Map<String,String> appList = new HashMap<>();


    public Map<String,String> initMap() {
        Map<String,String> map = new HashMap<>();
        map.put("TV桌面", ActivityName.TvHome);
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


    @Test
    public void testAllApps() throws UiObjectNotFoundException, InterruptedException, IOException, RemoteException {
        boolean flag = false;
        int failCount = 0;
        for (int Loop = 0; Loop < Integer.parseInt(InstrumentationRegistry.getArguments().getString("Loop")); Loop++) {
                for (String app : listApps) {
                    flag = openApp(app, false);
                    if (!flag) {
                        failCount++;
                        System.out.println("Case:testAppDesk_2, Could not open App "
                                + app + ".");
                    }
                    sleep(3000);
                    // exit the App
                    device.pressBack();
                }
                if (failCount > 2) {
                    //screenShot();
                    Assert.fail("Fail to open app more than 2 times,for " + failCount
                            + " times.");
                }
        }
    }
}
