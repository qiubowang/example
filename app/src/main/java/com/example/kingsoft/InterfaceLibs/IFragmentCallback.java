package com.example.kingsoft.InterfaceLibs;

/**
 * Created by kingsoft on 2017/6/30.
 */
public interface IFragmentCallback {
    //默认为静态final
    int RESET_FRAGMENT_2_TEXT_VIEW = 0;
    int RESET_FRAGMENT_3_TEXT_VIEW = 1;
    void interactiveFragment(int type);

}
