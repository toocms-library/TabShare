package com.toocms.share.share;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.toocms.share.listener.OnShareListener;
import com.toocms.share.platform.Platform;
import com.toocms.share.platform.ShareMedia;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * 一键分享
 *
 * @author Zero
 * @version 2.0
 * @date 2016/8/13 11:41
 */
public class OneKeyShare implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private ShareView shareView;

    private ShareAction shareAction; // 分享平台
    private OnShareListener listener; // 分享回调监听

    private UMWeb web;

    private boolean isCancel = true; // 点击Item之后是否隐藏

    public OneKeyShare(Activity activity) {
        mActivity = activity;
        UMShareAPI.get(mActivity);
        shareView = new ShareView(mActivity);
        shareView.setOnShareListener(this);
        shareAction = new ShareAction(mActivity);
    }

    // 设置需要分享的平台
    public OneKeyShare setPlatformList(ShareMedia... shareMedia) {
        List<Platform> list = new ArrayList<>();
        for (int i = 0; i < shareMedia.length; i++) {
            list.add(shareMedia[i].toPlatform());
        }
        shareView.setPlatformList(list);
        return this;
    }

    public OneKeyShare setUrl(String url, String title, String desc, int resId) {
        web = new UMWeb(url, title, desc, new UMImage(mActivity, resId));
        return this;
    }

    public OneKeyShare setUrl(String url, String title, String desc, String imageUrl) {
        web = new UMWeb(url, title, desc, new UMImage(mActivity, imageUrl));
        return this;
    }

    // 设置分享状态回调监听
    public OneKeyShare setListener(OnShareListener listener) {
        this.listener = listener;
        return this;
    }

    // 设置显示文字
    public OneKeyShare showText(boolean isShowText) {
        shareView.setShowText(isShowText);
        return this;
    }

    // 设置显示取消按钮
    public OneKeyShare showCancel(boolean isShowCancel) {
        shareView.setShowCancel(isShowCancel);
        return this;
    }

    // 点击外部是否可以取消
    public OneKeyShare setCanceledOnTouchOutside(boolean cancel) {
        shareView.setCanceledOnTouchOutside(cancel);
        return this;
    }

    // 点击Item之后是否隐藏
    public OneKeyShare setCanceledOnItemClick(boolean cancel) {
        isCancel = cancel;
        return this;
    }

    // 是否显示
    public boolean isShowing() {
        return shareView.isShowing();
    }

    // 弹出分享界面
    public void show() {
        shareView.show();
    }

    // 隐藏分享界面
    public void hide() {
        shareView.hide();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SHARE_MEDIA platform = shareView.getPlatformsRequisite().get(position).shareMedia;
        shareAction.withMedia(web)
                .setPlatform(platform)
                .setCallback(listener)
                .share();
        // 自动隐藏分享控件
        if (isCancel) shareView.hide();
    }
}
