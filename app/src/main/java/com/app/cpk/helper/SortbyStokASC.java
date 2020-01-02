package com.app.cpk.helper;

import com.app.cpk.model.Barang;

import java.util.Comparator;

public class SortbyStokASC implements Comparator<Barang> {
    public int compare(Barang a, Barang b)
    {
        return Integer.parseInt(a.getStok()) - Integer.parseInt(b.getStok());
    }
}
