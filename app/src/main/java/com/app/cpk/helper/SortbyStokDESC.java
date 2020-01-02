package com.app.cpk.helper;

import com.app.cpk.model.Barang;

import java.util.Comparator;

public class SortbyStokDESC implements Comparator<Barang> {
    public int compare(Barang a, Barang b)
    {
        return Integer.parseInt(b.getStok()) - Integer.parseInt(a.getStok());
    }
}
