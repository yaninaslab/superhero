package com.sg.superhero.dto;

import java.util.List;

public class SuperHeroDTO {

    private List<Integer> orglist;
    private boolean villan;

    public boolean isVillan() {
        return villan;
    }

    public void setVillan(boolean villan) {
        this.villan = villan;
    }

    public List<Integer> getOrglist() {
        return orglist;
    }

    public void setOrglist(List<Integer> orglist) {
        this.orglist = orglist;
    }

}
