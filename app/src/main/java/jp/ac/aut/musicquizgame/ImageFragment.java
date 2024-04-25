package jp.ac.aut.musicquizgame;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import jp.ac.aut.musicquizgame.databinding.FragmentImageBinding;
import kotlin.jvm.JvmStatic;


public class ImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IMG_RES_ID = "IMG_RES_ID";

    // TODO: Rename and change types of parameters
    private int imageResId;

    public ImageFragment() {
        // Required empty public constructor
    }



    @NonNull
    @JvmStatic
    public static ImageFragment newInstance(int imageResId) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        if(Objects.nonNull(args)){
            args.putInt(IMG_RES_ID, imageResId);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.imageResId = getArguments().getInt(IMG_RES_ID);
        }
    }

    private FragmentImageBinding binding;

    public FragmentImageBinding getBinding() {
        return this.binding;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentImageBinding.inflate(inflater,container,false);
        return this.binding.getRoot();
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        this.getBinding().imageView.setImageResource(imageResId);
    }
}