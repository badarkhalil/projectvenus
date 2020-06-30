package com.app.venustvsmmhuk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.app.venustvsmmhuk.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseActivity extends AppCompatActivity {

    ImageButton back, facebook, twitter, youtube, search;
    ProgressDialog loadingDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initHeader() {
        back = (ImageButton) findViewById(R.id.back);
        facebook = (ImageButton) findViewById(R.id.facebook);
        twitter = (ImageButton) findViewById(R.id.twitter);
        youtube = (ImageButton) findViewById(R.id.youtube);
        search = (ImageButton) findViewById(R.id.search);

        if (this instanceof MainActivity) {
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof HomeFragment) {
                        back.setVisibility(View.GONE);
                        facebook.setVisibility(View.VISIBLE);
                        twitter.setVisibility(View.VISIBLE);
                        youtube.setVisibility(View.VISIBLE);
                        search.setVisibility(View.GONE);
                        break;
                    } else if (fragment instanceof CategoryFragment) {
                        back.setVisibility(View.GONE);
                        facebook.setVisibility(View.GONE);
                        twitter.setVisibility(View.GONE);
                        youtube.setVisibility(View.GONE);
                        search.setVisibility(View.VISIBLE);
                        break;
                    } else if (fragment instanceof LiveFragment
                            || fragment instanceof  ContactFragment){
                        back.setVisibility(View.GONE);
                        facebook.setVisibility(View.GONE);
                        twitter.setVisibility(View.GONE);
                        youtube.setVisibility(View.GONE);
                        search.setVisibility(View.GONE);
                        break;
                    }
                }
            } else {
                back.setVisibility(View.GONE);
                facebook.setVisibility(View.VISIBLE);
                twitter.setVisibility(View.VISIBLE);
                youtube.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            facebook.setOnClickListener((MainActivity) this);
            twitter.setOnClickListener((MainActivity) this);
            youtube.setOnClickListener((MainActivity) this);
            search.setOnClickListener((MainActivity) this);
        } else {
            back.setVisibility(View.VISIBLE);
            facebook.setVisibility(View.GONE);
            twitter.setVisibility(View.GONE);
            youtube.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
        }
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this, R.style.DialogTheme);
            loadingDialog.setTitle(getString(R.string.title_please_wait));
            if (Build.VERSION.SDK_INT < 21) {
                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            loadingDialog.setMessage(getString(R.string.title_processing));
            loadingDialog.setCancelable(false);
            loadingDialog.setIndeterminate(false);
        }
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog.cancel();
        }
    }

    public void showAlertDialog(String title, String message, Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", onClickListener);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void dismissDialog() {
        alertDialog.dismiss();
    }
}
