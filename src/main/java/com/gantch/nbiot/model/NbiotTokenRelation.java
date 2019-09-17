package com.gantch.nbiot.model;

import lombok.Data;

@Data
public class NbiotTokenRelation {
    private Integer id;
    private String token;
    private String mac;
    private String type;

    public NbiotTokenRelation(Integer id, String token, String mac, String type) {
        this.id = id;
        this.token = token;
        this.mac = mac;
        this.type = type;
    }

    public NbiotTokenRelation(String token, String mac, String type) {
        this.token = token;
        this.mac = mac;
        this.type = type;
    }

}
