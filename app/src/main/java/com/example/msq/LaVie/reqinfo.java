package com.example.msq.LaVie;

public class reqinfo {
    public String location;
    public int quantity;
    public String bType;
    public long contact;

    reqinfo(String location1, int quantity1, String bloodGrp, String phNo1)
    {

    }
    reqinfo( String l,int a, String b, long c)
    {
        location = l;
        quantity = a;
        bType = b;
        contact = c;
    }
}
