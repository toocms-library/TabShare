package com.toocms.tab.share;

import android.app.Activity;

import com.toocms.tab.share.login.OneKeyLogin;
import com.toocms.tab.share.share.OneKeyShare;

/**
 * Author：Zero
 * Date：2020/7/6 15:16
 *
 * @version v1.0
 */

public class TabShare {

    /**
     * 获取登录类实例
     *
     * @param activity
     * @return
     */
    public static OneKeyLogin getOneKeyLogin(Activity activity) {
        return OneKeyLogin.getInstance(activity);
    }

    /**
     * 获取分享类实例
     *
     * @param activity
     * @return
     */
    public static OneKeyShare getOneKeyShare(Activity activity) {
        return OneKeyShare.getInstance(activity);
    }

    /**
     * 释放
     *
     * @param activity
     */
    public static void release(Activity activity) {
        getOneKeyLogin(activity).release();
        getOneKeyShare(activity).release();
    }
}
