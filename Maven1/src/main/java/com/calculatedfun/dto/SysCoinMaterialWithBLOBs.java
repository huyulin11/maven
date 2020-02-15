package com.calculatedfun.dto;

import com.calculatedfun.dto.SysCoinMaterial;

public class SysCoinMaterialWithBLOBs extends SysCoinMaterial {
    private String shortname;

    private String fullname;

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}