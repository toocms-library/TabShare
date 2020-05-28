package com.toocms.tab.share.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * <p>
 * Author：Zero
 * Date：2020/5/26
 *
 * @version v3.0
 */
public class TooCMSShareUtils {

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

    /**
     * 检查是否有SD卡
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 将view转换成bitmap,并将bitmap转换成图片文件存储到sd卡中
     *
     * @param activity
     * @param view
     * @return
     */
    public static File loadImageFromView(Activity activity, View view) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        view.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        int w = view.getWidth();
        int h = view.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        view.layout(0, 0, w, h);
        view.draw(c);
        return savePhotoToSDCard(bmp);
    }

    /**
     * 将bitmap转换成png格式存储到sd卡中
     *
     * @param bitmap
     * @return
     */
    public static File savePhotoToSDCard(Bitmap bitmap) {
        if (TooCMSShareUtils.checkSDCardAvailable()) {
            String imageFilePath = null;
            try {
                Class data = Class.forName("com.toocms.tab.toolkit.FileManager");
                Method method = data.getMethod("getImageFilePath", new Class[]{});
                imageFilePath = (String) method.invoke(null, new Object[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());
            File photoFile = new File(imageFilePath, dateFormat.format(new Date()) + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (bitmap != null) {
                    if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return photoFile;
        }
        return null;
    }
}
