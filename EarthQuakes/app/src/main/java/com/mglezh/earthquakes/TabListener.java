package com.mglezh.earthquakes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by cursomovil on 10/04/15.
 */
public	class	TabListener<T	extends	Fragment>	implements

        ActionBar.TabListener {

    private Fragment fragment;

    private Activity activity;

    private Class<T> fragmentClass;

    private int fragmentContainer;


    public TabListener(Activity activity, int fragmentContainer,

                       Class<T> fragmentClass) {

        this.activity = activity;

        this.fragmentContainer = fragmentContainer;

        this.fragmentClass = fragmentClass;

    }

    //	Called	when	a	new	tab	has	been	selected

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (fragment == null) {

            String fragmentName = fragmentClass.getName();

            fragment = Fragment.instantiate(activity, fragmentName);

            ft.replace(fragmentContainer, fragment, fragmentName);

        } else

            ft.attach(fragment);

    }


    //	Called	on	the	currently	selected	tab	when	a	different	tag	is

    //	selected.

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (fragment != null)

            ft.detach(fragment);

    }


    //	Called	when	the	selected	tab	is	selected.

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (fragment != null)

            ft.attach(fragment);

    }
}