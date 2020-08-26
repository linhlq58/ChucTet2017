package com.tetstudio.linhlee.chuctet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lequy on 1/17/2017.
 */

public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutResource();

    protected abstract void initVariables(Bundle savedInstanceState, View rootView);

    protected abstract void initData(Bundle savedInstanceState);

    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), null, false);
        initVariables(savedInstanceState, rootView);
        initData(savedInstanceState);
        return rootView;
    }

    /**
     * Start Activity with Bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Start Activity without Bundle
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }

    /**
     * Change fragment
     * @param fragment - New Fragment
     * @param layoutId - Layout Resource Id
     */
    protected  void changeFramgnet(Fragment fragment, int layoutId){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(layoutId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
