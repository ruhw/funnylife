package com.example.photowallfallsdemo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ShareActionProvider;
import android.widget.TextView;

/**
 * 查看大图的Activity界面。
 * viewpager
 * 
 */
public class ImageDetailsActivity extends Activity implements
		OnPageChangeListener, MenuItem.OnMenuItemClickListener {

	/**
	 * 用于管理图片的滑动
	 */
	private ViewPager viewPager;

	/**
	 * 显示当前图片的页数
	 */
	private TextView pageText;

    private int mPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_details);
		mPosition = getIntent().getIntExtra("image_position", 0);
		pageText = (TextView) findViewById(R.id.page_text);
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(mPosition);
		viewPager.setOnPageChangeListener(this);
		viewPager.setEnabled(false);
		// 设定当前的页数和总页数
		pageText.setText((mPosition + 1) + "/" + Images.imageUrls.length);
	}


    /**
	 * ViewPager的适配器
	 */
	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
            Bitmap bitmap = getBitmap(position);

			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.empty_photo);
			}
			View view = LayoutInflater.from(ImageDetailsActivity.this).inflate(
					R.layout.zoom_image_layout, null);
			ZoomImageView zoomImageView = (ZoomImageView) view
					.findViewById(R.id.zoom_image_view);
			zoomImageView.setImageBitmap(bitmap);
			container.addView(view);
			return view;
		}

		@Override
		public int getCount() {
			return Images.imageUrls.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}

	}

    private Bitmap getBitmap(int position) {
        String imagePath = getImagePath(Images.imageUrls[position]);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        return bitmap;
    }

    /**
	 * 获取图片的本地存储路径。
	 * 
	 * @param imageUrl
	 *            图片的URL地址。
	 * @return 图片的本地存储路径。
	 */
	private String getImagePath(String imageUrl) {
		int lastSlashIndex = imageUrl.lastIndexOf("/");
		String imageName = imageUrl.substring(lastSlashIndex + 1);
		String imageDir = Environment.getExternalStorageDirectory().getPath()
				+ "/PhotoWallFalls/";
		File file = new File(imageDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String imagePath = imageDir + imageName;
		return imagePath;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int currentPage) {
		// 每当页数发生改变时重新设定一遍当前的页数和总页数
        mPosition = currentPage;
		pageText.setText((currentPage + 1) + "/" + Images.imageUrls.length);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                sentIntent();
                break;
        }
        return true;
    }
    private void sentIntent() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        String imagePath = getImagePath(Images.imageUrls[mPosition]);
        Uri uri = Uri.parse(imagePath);
        intent.putExtra(Intent.EXTRA_STREAM,uri);//调用多张图片分享的方法，一个原理
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "请选择"));
    }

}