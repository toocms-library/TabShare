package com.toocms.share.platform;

/**
 * 平台账号配置
 * <p>
 * Author：Zero
 * Date：2017/7/3 11:38
 *
 * @version v2.0
 */
public class PlatformConfig {

    /**
     * 设置新浪微博账号
     *
     * @param appKey
     * @param appSecret
     * @param redirectUrl
     */
    public static void setSina(String appKey, String appSecret, String redirectUrl) {
        com.umeng.socialize.PlatformConfig.setSinaWeibo(appKey, appSecret, redirectUrl);
    }

    /**
     * 设置QQ空间账号
     *
     * @param appId
     * @param appKey
     */
    public static void setQZone(String appId, String appKey) {
        com.umeng.socialize.PlatformConfig.setQQZone(appId, appKey);
    }

    /**
     * 设置微信账号
     *
     * @param appId
     * @param appSecret
     */
    public static void setWechat(String appId, String appSecret) {
        com.umeng.socialize.PlatformConfig.setWeixin(appId, appSecret);
    }
}
