package com.docker.pojo;

public class SrcTbl {
    int id;
    String creation_date;
    String sale_value;

    public SrcTbl() {
    }

    public SrcTbl(int id, String creation_date, String sale_value) {
        this.id = id;
        this.creation_date = creation_date;
        this.sale_value = sale_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getSale_value() {
        return sale_value;
    }

    public void setSale_value(String sale_value) {
        this.sale_value = sale_value;
    }
}
