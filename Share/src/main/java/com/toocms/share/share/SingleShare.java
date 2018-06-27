package com.toocms.share.share;

import android.app.Activity;

import com.toocms.share.listener.OnShareListener;
import com.toocms.share.platform.ShareMedia;
import com.toocms.share.util.TooCMSShareUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 单一平台分享
 * <p>
 * Author：Zero
 * Date：2018/5/18 10:25
 *
 * @version v2.2.1
 */
public class SingleShare {

    private Activity mActivity;

    private ShareAction shareAction;
    private OnShareListener listener; // 分享回调监听

    private SHARE_MEDIA platform; // 分享平台
    private UMWeb web; // 分享内容

    public SingleShare(Activity activity) {
        mActivity = activity;
        UMShareAPI.get(mActivity);
        shareAction = new ShareAction(mActivity);
    }

    // 设置需要分享的平台
    public SingleShare setPlatform(ShareMedia shareMedia) {
        platform = TooCMSShareUtils.convert(shareMedia);
        return this;
    }

    public SingleShare setUrl(String url, String title, String desc, int resId) {
        if (!TooCMSShareUtils.isUrl(url)) throw new ClassCastException("请设置正确的url");
        web = new UMWeb(url, title, desc, new UMImage(mActivity, resId));
        return this;
    }

    public SingleShare setUrl(String url, String title, String desc, String imageUrl) {
        if (!TooCMSShareUtils.isUrl(url)) throw new ClassCastException("请设置正确的url");
        if (!TooCMSShareUtils.isUrl(imageUrl)) throw new ClassCastException("请设置正确的imageUrl");
        web = new UMWeb(url, title, desc, new UMImage(mActivity, imageUrl));
        return this;
    }

    // 设置分享状态回调监听
    public SingleShare setListener(OnShareListener listener) {
        this.listener = listener;
        return this;
    }

    public void toShare() {
        if (platform == null) throw new NullPointerException("请设置分享平台");
        if (web == null) throw new NullPointerException("请设置分享内容");
        shareAction.withMedia(web)
                .setPlatform(platform)
                .setCallback(listener)
                .share();
    }
}
