package com.app.venustvsmmhuk;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment implements View.OnClickListener {


    public ContactFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        ((BaseActivity) getActivity()).initHeader();

        view.findViewById(R.id.call).setOnClickListener(this);
        view.findViewById(R.id.mail).setOnClickListener(this);
        view.findViewById(R.id.web).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call:
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getResources().getString(R.string.number)));
                startActivity(callIntent);
                break;
            case R.id.mail:
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.setType("message/rfc822");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.mail)});
                try {
                    startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.no_email_clients), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.web:
                ((MainActivity)getActivity()).openPageInsideBrowser(getResources().getString(R.string.website_url));
                break;
        }
    }
}
