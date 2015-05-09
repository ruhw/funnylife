package com.example.ruhaiwen.photoviewer.base;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ruhaiwen.photoviewer.R;
import com.example.ruhaiwen.photoviewer.model.Photo;
import com.example.ruhaiwen.photoviewer.model.PhotoLab;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public abstract class BaseListFragment extends ListFragment {

    private static final String TAG = "BaseListFragment";
    protected ArrayList<Photo> mPhotos;
    private OnFragmentInteractionListener mListener;

    public BaseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//设置设备旋转时保留fragment实例

        mPhotos = PhotoLab.get(getActivity()).getPhotos();


        PhotoAdapter adapter =new PhotoAdapter(mPhotos);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_list, container, false);
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

    /*
	 * 刷新列表信息(non-Javadoc)
	 * @see android.app.Fragment#onResume()
	 */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        ((PhotoAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
         mListener.onFragmentInteraction(position);

    }

    private class PhotoAdapter extends ArrayAdapter<Photo> {
        public PhotoAdapter(ArrayList<Photo> crimes){
            super(getActivity(),0,crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_photo,
                        null);
            }
            Photo mPhoto = getItem(position);

            ImageView mImageView = (ImageView)convertView.findViewById(R.id.photo_imageView);
            // mImageView.setImageDrawable(R.drawable.ic_launcher);

            TextView mFilenameTextView =
                    (TextView)convertView.findViewById(R.id.filename_textView);
            mFilenameTextView.setText(mPhoto.getName());
            TextView mSizeTextView =
                    (TextView)convertView.findViewById(R.id.size_textView);
            mSizeTextView.setText(mPhoto.getSuffix() + "   "+  mPhoto.getSize());


            return convertView;
        }
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
        public void onFragmentInteraction(int mPosition);
    }
}
