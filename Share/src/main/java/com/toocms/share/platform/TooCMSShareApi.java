package com.toocms.share.platform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.toocms.share.listener.OnAuthListener;
import com.toocms.share.util.TooCMSShareUtils;
import com.umeng.socialize.UMShareAPI;

/**
 * 分享API
 * <p>
 * Author：Zero
 * Date：2017/7/3 13:46
 *
 * @version v2.0
 */
public class TooCMSShareApi {

    private volatile static TooCMSShareApi tooCMSShareApi;
    private static UMShareAPI umShareAPI;

    private TooCMSShareApi(Context context) {
        umShareAPI = UMShareAPI.get(context);
    }

    public static TooCMSShareApi get(Context context) {
        if (tooCMSShareApi == null)
            synchronized (TooCMSShareApi.class) {
                if (tooCMSShareApi == null)
                    tooCMSShareApi = new TooCMSShareApi(context);
            }
        return tooCMSShareApi;
    }

    /**
     * 接收回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 防止App被系统杀掉
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        umShareAPI.onSaveInstanceState(outState);
    }

    /**
     * 调用#onSaveInstanceState之后需要在onCreate中调用
     *
     * @param activity
     * @param savedInstanceState
     * @param listener
     */
    public void fetchAuthResultWithBundle(Activity activity, Bundle savedInstanceState, OnAuthListener listener) {
        umShareAPI.fetchAuthResultWithBundle(activity, savedInstanceState, listener);
    }

    /**
     * 授权（无用户信息返回）
     *
     * @param platform
     * @param listener
     */
    public void authorize(Activity activity, @NonNull ShareMedia platform, @NonNull OnAuthListener listener) {
        umShareAPI.doOauthVerify(activity, TooCMSShareUtils.convert(platform), listener);
    }

    /**
     * 撤销授权
     *
     * @param platform
     * @param listener
     */
    public void revokeAuthorize(Activity activity, @NonNull ShareMedia platform, @NonNull OnAuthListener listener) {
        umShareAPI.deleteOauth(activity, TooCMSShareUtils.convert(platform), listener);
    }

    /**
     * 获取平台是否已经被授权
     *
     * @param platform
     * @return
     */
    public boolean isAuthorize(Activity activity, @NonNull ShareMedia platform) {
        return umShareAPI.isAuthorize(activity, TooCMSShareUtils.convert(platform));
    }

    /**
     * 释放
     */
    public void release() {
        umShareAPI.release();
    }
}
