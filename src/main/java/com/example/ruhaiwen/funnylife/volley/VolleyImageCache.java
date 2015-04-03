package com.example.ruhaiwen.funnylife.volley;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * 对图片进行管理的工具类。
 * 
 * @author Created by ruhaiwen on 15-2-5.
 */
public class VolleyImageCache implements com.android.volley.toolbox.ImageLoader.ImageCache {

	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */
	private static LruCache<String, Bitmap> sMemoryCache;

	/**
	 * ImageLoader的实例,。使用单例，是因为初始化成本高
	 */
	private static VolleyImageCache sVolleyImageCache;

	public VolleyImageCache(Context context) {
		// 获取应用程序最大可用内存
		//int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = calculateMemoryCacheSize(context);

		sMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	/**
	 * 获取ImageLoader的实例。
	 * 
	 * @return ImageLoader的实例。
	 */
	public static VolleyImageCache getInstance(Context context) {
		if (sVolleyImageCache == null) {
			sVolleyImageCache = new VolleyImageCache(context.getApplicationContext());
		}
		return sVolleyImageCache;
	}

    @Override
    public Bitmap getBitmap(String url) {
        return sMemoryCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
            if (getBitmap(url) == null) {
                sMemoryCache.put(url, bitmap);
            }
    }

    private static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && SDK_INT >= HONEYCOMB) {
            memoryClass = am.getLargeMemoryClass();
        }
        // Target ~15% of the available heap.
        return 1024 * 1024 * memoryClass / 7;
    }
}
