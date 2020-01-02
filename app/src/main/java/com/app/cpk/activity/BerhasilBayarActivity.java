package com.app.cpk.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.cpk.R;
import com.app.cpk.helper.PrinterCommands;
import com.app.cpk.helper.TinyDB;
import com.app.cpk.helper.Utils;
import com.app.cpk.model.Barang;
import com.app.cpk.model.Toko;
import com.app.cpk.model.Transaksi;
import com.app.cpk.model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class BerhasilBayarActivity extends AppCompatActivity {

    Toolbar toolbar;
    TinyDB tinyDB;
    ArrayList<Transaksi> arrayList = new ArrayList<Transaksi>();
    ArrayList<Barang> barangList = new ArrayList<Barang>();
    String id_transaksi="0";
    String pesanan = "0";
    String jual = "0";
    String bayar = "0";
    int kembali = 0;
    TextView txKembalian;
    Button btnTransLagi, btnPrint, btnShare;
    private User user;
    private Toko toko;
    private ProgressDialog dialog;
    private String diskon, pajak = "0";
    byte FONT_TYPE;
    private static OutputStream outputStream;
    private String timeStamp, printContent = "";
    private final UUID SPP_UUID = UUID
            .fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berhasil_bayar);

        tinyDB = new TinyDB(this);
        user = tinyDB.getObject("user_login",User.class);
        toko = tinyDB.getObject("toko_login",Toko.class);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Harap tunggu...");

        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        arrayList = bundle.getParcelableArrayList("list");
        id_transaksi = getIntent().getStringExtra("id_transaksi");
        jual = getIntent().getStringExtra("jual");
        bayar = getIntent().getStringExtra("bayar");
        diskon = getIntent().getStringExtra("diskon");
        pajak = getIntent().getStringExtra("pajak");
        if (getIntent().hasExtra("pesanan")){
            pesanan = getIntent().getStringExtra("pesanan");
        }
        System.out.println("bayar : "+bayar+" - "+jual);
        kembali = Integer.parseInt(bayar.replace(".",""))-Integer.parseInt(jual.replace(".",""));
        Date date = new Date() ;
        //timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);

        btnTransLagi = (Button)findViewById(R.id.btn_transaksi_ulang);
        btnShare = (Button)findViewById(R.id.btn_share);
        txKembalian = (TextView)findViewById(R.id.tx_kembalian);
        txKembalian.setText("Kembalian : Rp."+String.valueOf(kembali));
        btnTransLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent returnIntent = new Intent();
                ListTransaksiActivity.listTransaksiActivity.setResult(111);
                ListTransaksiActivity.listTransaksiActivity.finish();
                InputBayarActivity.inputBayarActivity.finish();
                finish();
            }
        });

        setPrintContent();
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, printContent);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void setPrintContent(){
        printContent = printContent+timeStamp;
        printContent = printContent+"\n"+toko.getNama();
        printContent = printContent+"\n"+toko.getLokasi();
        printContent = printContent+"\n"+toko.getTelephone();
        printContent = printContent+"\n"+"--------------------------------";
        printContent = printContent+"\n"+"No. Transaksi : "+id_transaksi;
        printContent = printContent+"\n"+user.getNama();
        printContent = printContent+"\n"+"--------------------------------";
        for (Transaksi transaksi : arrayList){
            printContent = printContent+"\n"+transaksi.getNama();
            String jumhar = transaksi.getJumlah() + " X " +
                    doubleToStringNoDecimal(transaksi.getJual()) + " = ";
            int jumlah = Integer.parseInt(transaksi.getJumlah())*Integer.parseInt(transaksi.getJual());
            String jumlahString = doubleToStringNoDecimal(String.valueOf(jumlah));
            printContent = printContent+"\n"+jumhar+jumlahString;
        }
        printContent = printContent+"\n"+"--------------------------------";
        printContent = printContent+"\n"+"Total   : "+doubleToStringNoDecimal(jual);
        printContent = printContent+"\n"+"Bayar   : "+doubleToStringNoDecimal(bayar);
        printContent = printContent+"\n"+"Kembali : "+doubleToStringNoDecimal(String.valueOf(kembali));
        printContent = printContent+"\n"+"--------------------------------";
        try {
            printContent = printContent+"\n"+tinyDB.getString("ucapan");
        }catch (Exception e){
            printContent = printContent+"\n"+"Terima kasih telah berbelanja di Toko "+toko.getNama();
        }
    }

    @Override
    public void onBackPressed() {
        ListTransaksiActivity.listTransaksiActivity.finish();
        InputBayarActivity.inputBayarActivity.finish();
        super.onBackPressed();
    }

    public static String doubleToStringNoDecimal(String number) {
        Double d = Double.parseDouble(number);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter .applyPattern("#,###");
        return formatter.format(d).replace(",",".");
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }
}
