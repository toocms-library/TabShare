package com.toocms.tab.share.login;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.toocms.tab.share.listener.OnAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 一键登录
 *
 * @author Zero
 * @version 3.0
 * @date 2020/5/26
 */
public class OneKeyLogin {

    /**
     * 获取用户信息
     *
     * @param isNeedAuth  是否每次授权都需要显示授权页
     * @param share_media 平台名称
     * @param listener    回调监听
     */
    public static void showUser(Activity activity, boolean isNeedAuth, SHARE_MEDIA share_media, OnAuthListener listener) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(isNeedAuth);
        UMShareAPI api = UMShareAPI.get(activity);
        api.setShareConfig(config);
        api.getPlatformInfo(activity, share_media, listener);
    }

    /**
     * 撤销授权
     *
     * @param share_media
     * @param listener
     */
    public static void revokeAuthorize(Activity activity, SHARE_MEDIA share_media, OnAuthListener listener) {
        UMShareAPI.get(activity).deleteOauth(activity, share_media, listener);
    }

    /**
     * 获取平台是否已经被授权
     *
     * @param share_media
     * @return
     */
    public static boolean isAuthorize(Activity activity, @NonNull SHARE_MEDIA share_media) {
        return UMShareAPI.get(activity).isAuthorize(activity, share_media);
    }
}
