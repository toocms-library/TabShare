package com.toocms.share.listener;

import com.toocms.share.util.TooCMSShareUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 分享监听
 * <p>
 * Author：Zero
 * Date：2017/6/30 18:04
 *
 * @version v2.0
 */
public abstract class OnShareListener implements UMShareListener {

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
     */
    public abstract void onSuccess(String platform);

    /**
     * 分享失败
     *
     * @param platform
     * @param throwable 错误日志
     */
    public abstract void onError(String platform, Throwable throwable);

    /**
     * 分享取消
     *
     * @param platform
     */
    public abstract void onCancel(String platform);

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        onStart(TooCMSShareUtils.getPlatform(share_media));
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        onSuccess(TooCMSShareUtils.getPlatform(share_media));
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        onError(TooCMSShareUtils.getPlatform(share_media), throwable);
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        onCancel(TooCMSShareUtils.getPlatform(share_media));
    }
}
