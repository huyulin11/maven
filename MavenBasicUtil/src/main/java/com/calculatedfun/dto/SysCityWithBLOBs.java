package com.calculatedfun.dto;

import com.calculatedfun.dto.SysCity;

public class SysCityWithBLOBs extends SysCity {
    private String fullname;

    private String shortname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}