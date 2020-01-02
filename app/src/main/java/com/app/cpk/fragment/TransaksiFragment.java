package com.app.cpk.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cpk.R;
import com.app.cpk.activity.ListTransaksiActivity;
import com.app.cpk.adapter.TransaksiAdapter;
import com.app.cpk.helper.TinyDB;
import com.app.cpk.model.Barang;
import com.app.cpk.model.GetBarangResponse;
import com.app.cpk.model.InsertResponse;
import com.app.cpk.model.ResultPembelianItem;
import com.app.cpk.model.Toko;
import com.app.cpk.model.Transaksi;
import com.app.cpk.model.User;
import com.app.cpk.model.Pesanan;
import com.app.cpk.service.APIService;
import com.app.cpk.service.RetrofitHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiFragment extends Fragment {

    List<Transaksi> transaksiList = new ArrayList<Transaksi>();
    List<Transaksi> allTransaksiList = new ArrayList<Transaksi>();
    ArrayList<Transaksi> temp = new ArrayList<Transaksi>();
    ArrayList<Barang> barangList = new ArrayList<Barang>();
    ArrayList<Pesanan> pesananList = new ArrayList<Pesanan>();
    List<ResultPembelianItem> pembelianList = new ArrayList<ResultPembelianItem>();
    RecyclerView recyclerView;
    TransaksiAdapter transaksiAdapter;
    Button btnLanjut, btnSearch, btnAdd, btnBarcode, btnPajak, btnDiskon;
    EditText edtSearch;
    TinyDB tinyDB;
    ProgressDialog dialog;
    int jum;
    String pajak,diskon = "0";
    private Menu menu;
    private int posPesanan = 0;
    AlertDialog alertDialog;
    private int modeView = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaksi, container, false);

        tinyDB = new TinyDB(getContext());

        setHasOptionsMenu(true);

        btnLanjut = (Button)v.findViewById(R.id.btn_lanjuttrans);
        btnSearch = (Button)v.findViewById(R.id.btn_searchtrans);
        btnBarcode = (Button)v.findViewById(R.id.btn_barcodetrans);
        btnAdd = (Button)v.findViewById(R.id.btn_addtrans);
        btnPajak = (Button)v.findViewById(R.id.btn_pajaktrans);
        btnDiskon = (Button)v.findViewById(R.id.btn_diskontrans);
        edtSearch = (EditText)v.findViewById(R.id.edt_searchtrans);
        edtSearch.setVisibility(View.GONE);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerTransaksi);
        transaksiAdapter = new TransaksiAdapter(transaksiList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transaksiAdapter);


        jum = 0;
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getJum()>0){
                    pembelianList.clear();
                    Intent intent = new Intent(getContext(), ListTransaksiActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("list", temp);
                    intent.putExtras(bundle);
                    intent.putExtra("pajak",pajak);
                    intent.putExtra("diskon",diskon);
                    startActivityForResult(intent,123);
                }else{
                    Toast.makeText(getContext(),"Tidak ada barang yang dipilih",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateScan();
            }
        });
        btnDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDiskon();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSearch.getVisibility()==View.VISIBLE){
                    Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.anim_close);
                    edtSearch.startAnimation(slideUp);
                    edtSearch.setVisibility(v.GONE);
                }else{
                    edtSearch.setVisibility(v.VISIBLE);
                    Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.anim_expand);
                    edtSearch.startAnimation(slideDown);
                }
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    public void initiateScan(){
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    public void addTransaksi(int pos){
        jum = jum + 1;
        if (!temp.contains(transaksiList.get(pos))){
            int stok = Integer.parseInt(transaksiList.get(pos).getStok());
            int jumlah = Integer.parseInt(transaksiList.get(pos).getJumlah());
            transaksiList.get(pos).setJumlah(String.valueOf(jumlah+1));
            transaksiList.get(pos).setStok(String.valueOf(stok-1));
            temp.add(transaksiList.get(pos));
        }else{
            int index = temp.indexOf(transaksiList.get(pos));
            int jumlah = Integer.parseInt(temp.get(index).getJumlah());
            int stok = Integer.parseInt(transaksiList.get(pos).getStok());
            temp.get(index).setJumlah(String.valueOf(jumlah+1));
            temp.get(index).setStok(String.valueOf(stok-1));
        }
        btnLanjut.setText("Lanjut > "+String.valueOf(jum));
    }

    public int getJum() {
        return jum;
    }

    public void setJum(int jum) {
        this.jum = jum;
    }

    public void showDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_beli_tambahan, null);
        dialogBuilder.setView(dialogView);

        TextView txTitle = (TextView) dialogView.findViewById(R.id.tx_dialog_title);
        final EditText edtNama = (EditText) dialogView.findViewById(R.id.edt_nama_produk);
        final EditText edtCode = (EditText) dialogView.findViewById(R.id.edt_code_produk);
        final EditText edtHargaD = (EditText) dialogView.findViewById(R.id.edt_hargad_produk);
        final EditText edtHargaJ = (EditText) dialogView.findViewById(R.id.edt_hargaj_produk);
        final TextView txJumlah = (TextView) dialogView.findViewById(R.id.tx_jumlah);
        final TextView btnPlus = (TextView) dialogView.findViewById(R.id.btn_plus);
        final TextView btnMin = (TextView) dialogView.findViewById(R.id.btn_min);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jum = Integer.parseInt(txJumlah.getText().toString());
                jum = jum + 1;
                txJumlah.setText(String.valueOf(jum));
            }
        });
        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txJumlah.getText().toString().equals("0")){
                    int jum = Integer.parseInt(txJumlah.getText().toString());
                    jum = jum - 1;
                    txJumlah.setText(String.valueOf(jum));
                }
            }
        });

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!(edtHargaD.getText().toString().equals("")&&
                        edtHargaJ.getText().toString().equals("")&&edtNama.getText().toString().equals(""))){
                    showPD();
                    addBarang(edtNama.getText().toString(),txJumlah.getText().toString(),
                            edtHargaD.getText().toString(), edtHargaJ.getText().toString());
                }else{
                    Toast.makeText(getContext(), "Nama dan Harga harap diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void showPD(){
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == 123){
            if (resultCode==111){
                temp.clear();
                transaksiList.clear();
                allTransaksiList.clear();
                for (Barang b : barangList){
                    if (Integer.parseInt(b.getStok())>0){
                        transaksiList.add(new Transaksi(b.getId(),b.getNama(),"0",b.getHarga(),b.getStok(),b.getHarga_jual(),"1",b.getImage()));
                        allTransaksiList.add(new Transaksi(b.getId(),b.getNama(),"0",b.getHarga(),b.getStok(),b.getHarga_jual(),"1",b.getImage()));
                    }
                }
                jum = 0;
                btnLanjut.setText("Lanjut > "+String.valueOf(jum));
                transaksiAdapter.notifyDataSetChanged();
                //((MenuActivity)getActivity()).resetTransaksi();
            }
        }else if(result != null) {
            if(result.getContents() != null) {
                Toast.makeText(getContext(),result.getContents(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getListBarang(){
        APIService api = RetrofitHelper.getClient().create(APIService.class);
        Call<GetBarangResponse> call = api.showBarang(((User)tinyDB.getObject("user_login", User.class)).getId_toko());
        System.out.println("masuk");
        call.enqueue(new Callback<GetBarangResponse>() {
            @Override
            public void onResponse(Call<GetBarangResponse> call, Response<GetBarangResponse> response) {
                System.out.println("re : " + response.body().getStatus_code());
                if (response.body().getStatus_code()==1) {
                    transaksiList.clear();
                    barangList.clear();
                    allTransaksiList.clear();
                    barangList.addAll(response.body().getResult_barang());
                    for (Barang b : response.body().getResult_barang()){
                        if (Integer.parseInt(b.getStok())>0){
                            transaksiList.add(
                                    new Transaksi(b.getId(),b.getNama(),"0",b.getHarga(),b.getStok(),b.getHarga_asli(),b.getHarga_jual(),"1",b.getImage()));
                        }
                        allTransaksiList.add(
                                new Transaksi(b.getId(),b.getNama(),"0",b.getHarga(),b.getStok(),b.getHarga_asli(),b.getHarga_jual(),"1",b.getImage()));
                    }
                    transaksiAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"Tidak ada barang",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetBarangResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Harap periksa koneksi internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void showDialogDiskon(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_diskon, null);
        dialogBuilder.setView(dialogView);

        TextView txTitle = (TextView) dialogView.findViewById(R.id.tx_dialog_title);
        final EditText edtDiskon = (EditText) dialogView.findViewById(R.id.edt_diskon_dialog);
        edtDiskon.setText(((Toko)tinyDB.getObject("toko_login", Toko.class)).getDiskon());
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                diskon = edtDiskon.getText().toString();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        temp.clear();
        btnLanjut.setText("Lanjut > 0");
        getListBarang();
    }

    public void addBarang(final String nama, final String jumlah, final String hargad, final String hargaj) {
        APIService api = RetrofitHelper.getClient().create(APIService.class);
        Call<InsertResponse> call = api.addBarang(((User) tinyDB.getObject("user_login", User.class)).getId_toko(), "0",
                nama, "000", hargad, jumlah, hargaj, "0", "","");
        System.out.println("masuk");
        call.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                System.out.println("re : " + response.body().getStatus_code());
                if (response.body().getStatus_code().equals("1")) {
                    Toast.makeText(getContext(), "Tambah barang berhasil", Toast.LENGTH_SHORT).show();
                    temp.add(new Transaksi("0",nama,jumlah,hargad,jumlah,
                            hargaj,"1"));
                    setJum(getJum()+Integer.parseInt(jumlah));
                    btnLanjut.setText("Lanjut > "+String.valueOf(getJum()));
                    getListBarang();
                }else{
                    Toast.makeText(getContext(), "Tambah barang gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Harap periksa koneksi internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.transaksi_menu, menu);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.clear_transaksi:
                temp.clear();
                transaksiList.clear();
                allTransaksiList.clear();
                for (Barang b : barangList){
                    if (Integer.parseInt(b.getStok())>0){
                        transaksiList.add(new Transaksi(b.getId(),b.getNama(),"0",b.getHarga(),b.getStok(),b.getHarga_asli(),b.getHarga_jual(),"1",b.getImage()));
                    }
                    allTransaksiList.add(new Transaksi(b.getId(),b.getNama(),"0",b.getHarga(),b.getStok(),b.getHarga_asli(),b.getHarga_jual(),"1",b.getImage()));
                }
                jum = 0;
                btnLanjut.setText("Lanjut > "+String.valueOf(jum));
                transaksiAdapter.notifyDataSetChanged();
                return true;
            case R.id.view_mode:
                transaksiAdapter = new TransaksiAdapter(transaksiList, this);
                recyclerView.setAdapter(transaksiAdapter);
                if (modeView==0) {
                    modeView = 1;
                    transaksiAdapter.setMode(modeView);
                    MenuItem viewItem = menu.findItem(R.id.view_mode);
                    viewItem.setTitle("Mode List");
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                }else{
                    modeView = 0;
                    transaksiAdapter.setMode(modeView);
                    MenuItem viewItem = menu.findItem(R.id.view_mode);
                    viewItem.setTitle("Mode Grid");
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void filter(String text){
        List<Transaksi> temp = new ArrayList();
        for(Transaksi d: transaksiList){
            if(d.getNama().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        transaksiAdapter.updateList(temp);
    }
}
