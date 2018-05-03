package com.toocms.share.login;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.toocms.share.listener.OnAuthListener;
import com.toocms.share.platform.ShareMedia;
import com.toocms.share.util.TooCMSShareUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

/**
 * 一键登录
 *
 * @author Zero
 * @version 2.0
 * @date 2016/8/13 17:46
 */
public class OneKeyLogin {

    private Activity mActivity;

    public OneKeyLogin(Activity activity) {
        mActivity = activity;
    }

    /**
     * 获取用户信息
     *
     * @param isNeedAuth 是否每次授权都需要显示授权页
     * @param platform   平台名
     * @param listener   回调监听
     */
    public void showUser(boolean isNeedAuth, @NonNull ShareMedia platform, @NonNull OnAuthListener listener) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(isNeedAuth);
        UMShareAPI.get(mActivity).setShareConfig(config);
        UMShareAPI.get(mActivity).getPlatformInfo(mActivity, TooCMSShareUtils.convert(platform), listener);
    }
}
