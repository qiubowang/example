package com.example.kingsoft.ViewPagerLibs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kingsoft.ActivityLibs.ViewFragmentActivity;
import com.example.kingsoft.CustomAdapter.R;

/**
 * Created by kingsoft on 2017/6/28.
 */

public class Fragment1 extends Fragment{
    private Button mButton = null;
    private ViewFragmentActivity callback = null;

    public Fragment1() {
        super();
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        callback = (ViewFragmentActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_layout_one, container, false);
//        mButton = (Button) callback.findViewById(R.id.frament1_button);
//        mButton.setOnClickListener((v) -> {
//            callback.interactiveFragment(IFragmentCallback.RESET_FRAGMENT_3_TEXT_VIEW);
//        });

        return view;
    }
}
