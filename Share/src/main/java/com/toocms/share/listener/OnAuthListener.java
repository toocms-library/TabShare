package com.toocms.share.listener;

import com.toocms.share.login.PlatformUser;
import com.toocms.share.util.TooCMSShareUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 授权/获取用户信息监听
 * <p>
 * Author：Zero
 * Date：2017/6/30 18:04
 *
 * @version v2.0
 */
public abstract class OnAuthListener implements UMAuthListener {

    /**
     * 分享开始之前
     *
     * @param platform 分享的平台名称
     */
    public abstract void onStart(String platform);

    /**
     * 分享成功
     *
     * @param platform
     * @param action
     * @param user     平台用户信息
     */
    public abstract void onSuccess(String platform, int action, PlatformUser user);

    /**
     * 分享失败
     *
     * @param platform
     * @param action
     * @param throwable 错误日志
     */
    public abstract void onError(String platform, int action, Throwable throwable);

    /**
     * 分享取消
     *
     * @param platform
     */
    public abstract void onCancel(String platform, int action);

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        onStart(TooCMSShareUtils.getPlatform(share_media));
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        PlatformUser user = null;
        if (map != null) {
            user = new PlatformUser(map.get("uid"), map.get("name"), map.get("gender"), map.get("iconurl"), map.get("accessToken"));
        }
        onSuccess(TooCMSShareUtils.getPlatform(share_media), i, user);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        onError(TooCMSShareUtils.getPlatform(share_media), i, throwable);
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        onCancel(TooCMSShareUtils.getPlatform(share_media), i);
    }
}
