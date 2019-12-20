package com.ashu.postermaker.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.ashu.postermaker.R;
import com.ashu.postermaker.fragments.PagerFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends BaseActivity {
    private ImageView iv_settings;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        setMyTitle(""+getString(R.string.app_name));
        showSettingButton();
        showPager();

    }

    private void showLogoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.logout_alert);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_logout = dialog.findViewById(R.id.btn_logout);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
    }

    private void showPager() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new PagerFragment();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @NotNull
    @Override
    public String getClassName() {
        return ""+MainActivity.class.getSimpleName();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.are_you_sure_you_want_exit_app)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }

                    })
                    .setNegativeButton(R.string.no, null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
