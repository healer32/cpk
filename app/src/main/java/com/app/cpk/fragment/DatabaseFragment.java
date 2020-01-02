package com.app.cpk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.cpk.R;
import com.app.cpk.activity.DBarangActivity;
import com.app.cpk.activity.KBarangActivity;
import com.app.cpk.activity.PelSupActivity;
import com.app.cpk.activity.PembelianActivity;
import com.app.cpk.helper.TinyDB;
import com.app.cpk.model.User;

public class DatabaseFragment extends Fragment {

    Button btnBarJas, btnKatBar, btnMStok, btnPemBar, btnPel, btnHutPit, btnDisPaj;
    TinyDB tinyDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_database, container, false);

        tinyDB = new TinyDB(getContext());

        btnBarJas = (Button)v.findViewById(R.id.btn_brgjasa);
        btnKatBar = (Button)v.findViewById(R.id.btn_kbrg);
        btnMStok = (Button)v.findViewById(R.id.btn_stok);
        btnPemBar = (Button)v.findViewById(R.id.btn_pbrg);
        btnPel = (Button)v.findViewById(R.id.btn_pelsup);

        try {
            if (((User)tinyDB.getObject("user_login", User.class)).getTipe().equals("2")){
                //btnBarJas.setVisibility(View.GONE);
                //btnKatBar.setVisibility(View.GONE);
                btnPemBar.setVisibility(View.GONE);
                //btnHutPit.setVisibility(View.GONE);
                btnDisPaj.setVisibility(View.GONE);
            }else if (((User)tinyDB.getObject("user_login", User.class)).getTipe().equals("3")){
                btnHutPit.setVisibility(View.GONE);
                btnDisPaj.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }

        btnBarJas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DBarangActivity.class);
                intent.putExtra("tipe","0");
                startActivity(intent);
            }
        });
        btnKatBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KBarangActivity.class);
                startActivity(intent);
            }
        });
        btnMStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DBarangActivity.class);
                startActivity(intent);
            }
        });
        btnPemBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PembelianActivity.class);
                startActivity(intent);
            }
        });
        btnPel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PelSupActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
