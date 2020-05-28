<p align="center">
  <img src="https://avatars3.githubusercontent.com/u/38806334?s=400&u=b20d7b719e126e45e3d45c0ff04d0597ae3ed703&v=4" width="220" height="220" alt="Banner" />
</p>

# TabShare框架集成文档

![Support](https://img.shields.io/badge/API-19+-4BC51D.svg)&#160;&#160;&#160;&#160;&#160;[![TAS Update](https://img.shields.io/badge/更新-记录-4BC51D.svg)](https://github.com/toocms-library/TabShare/releases)&#160;&#160;&#160;&#160;&#160;![Author](https://img.shields.io/badge/Author-Zero-4BC51D.svg)

## 添加Gradle依赖

- 在模块目录下的build.gradle文件的dependencies添加

```
dependencies {
    implementation 'com.github.toocms-library:TabShare:3.0.0.200528-alpha'
}
```

## 集成方法

- 在项目包名下创建wxapi包，并在wxapi包中创建WXEntryActivity类，继承WXCallbackActivity类
- Manifest配置

```
 <!-- 分享和登录都要注册的Activity -->
 <!--QQ/QZONE-->
 <!--这里需要注意的是android:scheme="tencent100371282"这个属性tencent后边的数字需要改成QQ和QZone的AppId-->
 <activity
   android:name="com.tencent.tauth.AuthActivity"
   android:launchMode="singleTask"
   android:noHistory="true">
     <intent-filter>
       <action android:name="android.intent.action.VIEW" />
       <category android:name="android.intent.category.DEFAULT" />
       <category android:name="android.intent.category.BROWSABLE" />
       <data android:scheme="tencent100371282" />
     </intent-filter>
 </activity>
 <activity
   android:name="com.tencent.connect.common.AssistActivity"
   android:configChanges="orientation|keyboardHidden|screenSize"
   android:theme="@android:style/Theme.Translucent.NoTitleBar" />

 <!-- 微信登录页面 -->
 <activity
   android:name=".wxapi.WXEntryActivity"
   android:configChanges="keyboardHidden|orientation|screenSize"
   android:exported="true"
   android:theme="@android:style/Theme.Translucent.NoTitleBar" />
```

- 代码配置，在AppConfig类中的initJarForWeApplication回调方法中设置分享平台的唯一标识

```
@Override
public void initJarForWeApplication(Application application) {
    // 第三方账号所对应的唯一标识
    PlatformConfig.setQQZone("1105240612", "2iDzT9GvL7L9Q9bg");
    PlatformConfig.setWeixin("wx0f7b622f264f30f5", "c5da5d5cc432d38358a850ad20f4f9fc");
}
```

## 使用方法

### 三方分享

- 封装

```
/**
* 分享插件
*/
private OneKeyShare oneKeyShare;

/**
* 调起分享
*
* @param share_media 分享到的平台（根据个数决定是否弹出分享面板，单个不弹出，多个弹出）
*/
private void doShare(SHARE_MEDIA... share_media) {
   if (oneKeyShare == null) oneKeyShare = new OneKeyShare(this);
   oneKeyShare
       // 设置要分享的平台列表，枚举类为SHARE_MEDIA，例如SHARE_MEDIA.QQ
       .setPlatform(share_media)
       // 设置要分享的url、标题、描述、图片（分享内容4选1）
       .setUrl("http://www.toocms.com",
               "晟轩科技",
               "晟轩科技成立于2007年，12年时间让我们从默默无闻做到了行业优质服务商，主要从事网站建设，APP开发，微信开发，品牌设计等服务，能为您提供个性化的互联网全套解决方案",
               R.raw.shengxuan)    // 也可设置网络图片地址："http://xsd-img.toocms.com//Uploads/Config/2016-12-07/5847d181ebeaa.png"
       // 设置要分享的view/layoutId（分享内容4选1）
       .setLayout(R.id.share_view)
       // 设置要分享的imageUrl/resId（分享内容4选1）
       .setImage(R.raw.shengxuan)
       // 设置要分享的小程序，实测描述貌似没什么用（分享内容4选1）
       .setMin("http://www.toocms.com", "易商综合商城", "买啥都便宜", "pages/index/index", "gh_723a21db6d3a", R.raw.shengxuan)
       // 设置分享回调监听
       .setShareCallback(new OnShareListener() {
           @Override
           public void onResult(SHARE_MEDIA share_media) {
               showToast("成功");
           }

           @Override
           public void onError(SHARE_MEDIA share_media, Throwable throwable) {
               showToast("失败");
           }

           @Override
           public void onCancel(SHARE_MEDIA share_media) {
               showToast("取消");
           }
       })
       // 执行分享操作
       .share();
}
```

- 调用

```
// 单个平台分享（不弹出分享面板）
doShare(SHARE_MEDIA.WEIXIN);
// 多个平台分享（弹出分享面板）
doShare(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE);
```

### 授权登录

- 调起授权登录页面/撤销授权

```
// 授权登录
OneKeyLogin.showUser(this, true, share_media, onAuthListener);
// 撤销授权
OneKeyLogin.revokeAuthorize(this, share_media, onAuthListener);
```

- 注册授权登录/撤销授权监听

```
private OnAuthListener onAuthListener = new OnAuthListener() {
    @Override
    public void onComplete(SHARE_MEDIA share_media, int action, PlatformUser platformUser) {
        switch (action) {
            case ACTION_AUTHORIZE:  // 授权
                showDialog(share_media.name() + "授权登录成功",
                        "OpenId：" + platformUser.getOpenId() + "\n" +        // openid
                        "Name：" + platformUser.getName() + "\n" +             // 昵称
                        "Gender：" + platformUser.getGender() + "\n" +         // 性别
                        "Head：" + platformUser.getHead() + "\n" +              // 头像url
                        "Token：" + platformUser.getToken());                     // token
                break;
            case ACTION_DELETE: // 撤销授权
                showToast("已撤销" + share_media.name() + "授权");
                break;
        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int action, Throwable throwable) {
        switch (action) {
            case ACTION_AUTHORIZE:  // 授权
                showToast(share_media.name() + "授权失败");
                break;
            case ACTION_DELETE: // 撤销授权
                showToast("撤销" + share_media.name() + "授权出错");
                break;
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int action) {
        showToast("取消" + share_media.name() + "授权");
    }
};
```

- 判断平台是否已经授权

```
OneKeyLogin.isAuthorize(this, share_media)
```

- 添加回调代码

```
// 为了让回调监听起作用，还需在onActivityResult中调用方法，同时注意：分享/登录只能在Activity中调起
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   super.onActivityResult(requestCode, resultCode, data);
   UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
}
```

## 进阶使用

### 增加其他平台分享/授权登录

- 将所需集成平台对应的友盟Jar包放到libs中
- 将平台logo命名成umeng_socialize_平台名.png并放到drawable-xhdpi文件夹中（适合多平台分享）
- 在initJarForWeApplication回调方法中增加集成平台的配置
- 配置必要项（可根据运行时的错误日志从友盟复制）
  - Manifest.xml
  - layout
  - style
  - drawable
  - string

### 分享面板增加自定义按钮

- 调起自定义面板

```
oneKeyShare = new OneKeyShare(this);
oneKeyShare.setPlatform(share_media);
oneKeyShare.addButton("复制链接", "copyurl", "umeng_socialize_copyurl", "umeng_socialize_copyurl");
oneKeyShare.addButton("屏蔽", "delete", "umeng_socialize_delete", "umeng_socialize_delete");
oneKeyShare.addButton("更多", "more", "umeng_socialize_more", "umeng_socialize_more");
oneKeyShare.setShareboardclickCallback(shareBoardlistener);
oneKeyShare.share();
```

- 注册自定义分享面板选项点击监听

```
private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
    @Override
    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
        // 通过判断share_media是否为空来决定点击的选项是自定义按钮还是三方平台
        if (share_media == null) {  // 自定义按钮
            switch (snsPlatform.mKeyword) {
                case "copyurl":
                    showToast("复制成功");
                    break;
                case "delete":
                    showToast("屏蔽成功");
                    break;
                case "more":
                    showToast("敬请期待");
                    break;
            }
        } else {    // 三方平台分享
            doShare(share_media);   // 该方法为上述单平台分享封装方法
        }
    }
};
```

