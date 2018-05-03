package com.toocms.share.share;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toocms.share.R;
import com.toocms.share.platform.Platform;
import com.toocms.share.util.TooCMSShareUtils;

import java.util.List;

/**
 * 自定义分享控件
 *
 * @author Zero
 * @version 2.0
 * @date 2016/8/11 17:58
 */
class ShareView {

    private GridView gridView;
    private PlatformAdapter adapter;
    private LinearLayout linlayCancel;

    private Activity mActivity;
    private View fullMaskView; // 蒙层视图
    private View contentLayout; // 分享视图

    private List<Platform> list; // 需要的平台成名列表
    private int panelHeight = 0; // 分享视图高度
    private int navigationBarHeight = 0; // 导航栏高度
    private boolean isCancel = true; // 点击外部是否隐藏
    private boolean isShowText; // 是否显示文字
    private boolean isShowing; // 是否显示状态

    public ShareView(Activity activity) {
        this.mActivity = activity;
        initShareView(activity);
    }

    private void initShareView(Activity activity) {
        fullMaskView = View.inflate(activity, R.layout.ui_view_full_mask_layout, null);
        contentLayout = View.inflate(activity, R.layout.ui_share, null);
        // 初始化控件
        gridView = (GridView) contentLayout.findViewById(R.id.share_platform);
        linlayCancel = (LinearLayout) contentLayout.findViewById(R.id.share_cancel_line);
        //
        attachView();
        initListener();
    }

    // 将该View添加到根布局
    private void attachView() {
        ((ViewGroup) mActivity.getWindow().getDecorView()).addView(fullMaskView);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        ((ViewGroup) mActivity.getWindow().getDecorView()).addView(contentLayout, params);
        fullMaskView.setVisibility(View.GONE);
        contentLayout.setVisibility(View.GONE);

        if (TooCMSShareUtils.hasNavigationBar(mActivity)) {
            navigationBarHeight = TooCMSShareUtils.getNavigationBarHeight(mActivity);
        }
    }

    void setPlatformList(List<Platform> list) {
        this.list = list;
        gridView.setNumColumns(list.size());
    }

    void setCanceledOnTouchOutside(boolean cancel) {
        isCancel = cancel;
    }

    // 设置显示文字
    void setShowText(boolean isShowText) {
        this.isShowText = isShowText;
    }

    // 设置显示取消按钮
    void setShowCancel(boolean isShowCancel) {
        linlayCancel.setVisibility(isShowCancel ? View.VISIBLE : View.GONE);
    }

    void setOnShareListener(OnItemClickListener listener) {
        gridView.setOnItemClickListener(listener);
    }

    public List<Platform> getPlatformsRequisite() {
        return list;
    }

    // 动画显示布局
    void show() {
        adapter = new PlatformAdapter();
        gridView.setAdapter(adapter);
        fullMaskView.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.VISIBLE);
        contentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contentLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup parent = (ViewGroup) contentLayout.getParent();
                panelHeight = parent.getHeight() - contentLayout.getTop();
                ObjectAnimator.ofFloat(contentLayout, "translationY", panelHeight, -navigationBarHeight).setDuration(200).start();
            }
        });
        isShowing = true;
    }

    // 动画隐藏布局
    void hide() {
        fullMaskView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(contentLayout, "translationY", -navigationBarHeight, panelHeight).setDuration(200).start();
        isShowing = false;
    }

    // 获取是否显示
    public boolean isShowing() {
        return isShowing;
    }

    private void initListener() {
        fullMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCancel) hide();
            }
        });
        linlayCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

    private class PlatformAdapter extends BaseAdapter {

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Platform getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.listitem_share, null);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, TooCMSShareUtils.dpToPxInt(85));
                convertView.setLayoutParams(params);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.listitem_share_icon);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.listitem_share_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.imageView.setImageResource(TooCMSShareUtils.getBitmapRes(mActivity, getItem(position).icon));
            viewHolder.textView.setText(getItem(position).text);
            if (isShowText) {
                viewHolder.textView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.textView.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            public ImageView imageView;
            public TextView textView;
        }
    }
}
