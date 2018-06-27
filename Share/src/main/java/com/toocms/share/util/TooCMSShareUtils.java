package com.toocms.share.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import com.toocms.share.platform.ShareMedia;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.umeng.socialize.bean.SHARE_MEDIA.MORE;
import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;
import static com.umeng.socialize.bean.SHARE_MEDIA.QZONE;
import static com.umeng.socialize.bean.SHARE_MEDIA.SINA;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN_CIRCLE;

/**
 * 工具类
 * <p>
 * Author：Zero
 * Date：2017/7/3 17:03
 *
 * @version v2.0
 */
public class TooCMSShareUtils {

    // 判断设备是否有返回键、菜单键来确定是否有 NavigationBar
    public static boolean hasNavigationBar(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            return true;
        }
        return false;
    }

    // 获取 NavigationBar 的高度
    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    // 获取图片资源的id
    public static int getBitmapRes(Context context, String resName) {
        try {
            String str = context.getPackageName();
            Class localClass = Class.forName(str + ".R$drawable");
            return getResId(localClass, resName);
        } catch (Throwable localThrowable) {
        }
        return 0;
    }

    public static int getResId(Class<?> paramClass, String paramString) {
        int i = 0;
        if (paramString != null) {
            String str = paramString.toLowerCase();
            try {
                Field localField = paramClass.getField(str);
                localField.setAccessible(true);
                i = ((Integer) localField.get(null)).intValue();
            } catch (Throwable localThrowable) {
                i = 0;
            }
        }
        return i;
    }

    /**
     * 判断网址是否有效
     */
    public static boolean isUrl(String url) {
        Pattern pattern = Pattern.compile("^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\\.)+([A-Za-z]+)[/\\?\\:]?.*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static int dpToPxInt(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    public static String getPlatform(SHARE_MEDIA share_media) {
        switch (share_media) {
            case SINA:
                return "新浪微博";
            case QQ:
                return "QQ";
            case QZONE:
                return "QQ空间";
            case WEIXIN:
                return "微信";
            case WEIXIN_CIRCLE:
                return "朋友圈";
            default:
                return "暂不支持的平台";
        }
    }

    public static SHARE_MEDIA convert(ShareMedia platform) {
        switch (platform) {
            case SinaWeibo:
                return SINA;
            case QQ:
                return QQ;
            case QZone:
                return QZONE;
            case Wechat:
                return WEIXIN;
            case WechatMoments:
                return WEIXIN_CIRCLE;
            default:
                return MORE;
        }
    }
}
