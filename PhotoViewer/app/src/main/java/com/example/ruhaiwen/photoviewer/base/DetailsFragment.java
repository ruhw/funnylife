package com.example.ruhaiwen.photoviewer.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ruhaiwen.photoviewer.R;
import com.example.ruhaiwen.photoviewer.model.Photo;
import com.example.ruhaiwen.photoviewer.model.PhotoLab;
import com.example.ruhaiwen.photoviewer.view.ZoomImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private static final String CURRENT_PATH = "mCurrentPath";

    private int mPosition;

    private OnFragmentInteractionListener mListener;
    private boolean isShow = false;
    /**
     * 用于管理图片的滑动
     */
    private ViewPager viewPager;

    /**
     * 显示当前图片的页数
     */
    private TextView pageText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(int position) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_PATH, position);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(CURRENT_PATH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView =  inflater.inflate(R.layout.fragment_details, container, false);
        pageText = (TextView) contentView.findViewById(R.id.page_text);
        viewPager = (ViewPager) contentView.findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mPosition);
        viewPager.setOnPageChangeListener(this);
        viewPager.setEnabled(false);
        // 设定当前的页数和总页数
        pageText.setText((mPosition + 1) + "/" + PhotoLab.get(getActivity()).getPhotos().size());
        return contentView;
    }

    /**
     * ViewPager的适配器
     *
     * @author guolin
     */
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Photo mPhoto = PhotoLab.get(getActivity()).getPhotos().get(position);
            Bitmap bitmap = BitmapFactory.decodeFile(mPhoto.getPath());
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.empty_photo);
            }
            View view = LayoutInflater.from(getActivity()).inflate(
                    R.layout.zoom_image_view, null);
            ZoomImageView zoomImageView = (ZoomImageView) view
                    .findViewById(R.id.zoom_image_view);
            zoomImageView.setImageBitmap(bitmap);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return PhotoLab.get(getActivity()).getPhotos().size();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        isShow = !isShow;
        if (mListener != null) {
            mListener.onFragmentInteraction(isShow);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Boolean isShow);
    }

}
