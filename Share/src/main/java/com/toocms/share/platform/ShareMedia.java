package com.toocms.share.platform;

import android.text.TextUtils;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Author：Zero
 * Date：2017/6/29 17:25
 *
 * @version v1.0
 */

public enum ShareMedia {
    SinaWeibo,
    QQ,
    QZone,
    Wechat,
    WechatMoments;

    public Platform toPlatform() {
        Platform platform = new Platform();
        if (TextUtils.equals(toString(), "SinaWeibo")) {
            platform.text = "新浪微博";
            platform.icon = "selector_share_weibo";
            platform.index = 0;
            platform.shareMedia = SHARE_MEDIA.SINA;
        } else if (TextUtils.equals(toString(), "QQ")) {
            platform.text = "QQ";
            platform.icon = "selector_share_qq";
            platform.index = 1;
            platform.shareMedia = SHARE_MEDIA.QQ;
        } else if (TextUtils.equals(toString(), "QZone")) {
            platform.text = "QQ空间";
            platform.icon = "selector_share_qzone";
            platform.index = 2;
            platform.shareMedia = SHARE_MEDIA.QZONE;
        } else if (TextUtils.equals(toString(), "Wechat")) {
            platform.text = "微信好友";
            platform.icon = "selector_share_wechat";
            platform.index = 3;
            platform.shareMedia = SHARE_MEDIA.WEIXIN;
        } else if (TextUtils.equals(toString(), "WechatMoments")) {
            platform.text = "朋友圈";
            platform.icon = "selector_share_moments";
            platform.index = 4;
            platform.shareMedia = SHARE_MEDIA.WEIXIN_CIRCLE;
        }
        return platform;
    }
}
