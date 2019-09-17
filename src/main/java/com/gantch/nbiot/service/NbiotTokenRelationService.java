package com.gantch.nbiot.service;

import com.gantch.nbiot.dao.NbiotTokenRelationMapper;
import com.gantch.nbiot.model.NbiotTokenRelation;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NbiotTokenRelationService {
    @Autowired
    private NbiotTokenRelationMapper nbiotTokenRelationMapper;

    public NbiotTokenRelation getRelationByToken(String token){//根据token查询关系
        return nbiotTokenRelationMapper.getRelationByToken(token);
    }

    public Boolean addARelation(NbiotTokenRelation nbiotTokenRelation){//添加新的关系
        Integer i = nbiotTokenRelationMapper.addARelation(nbiotTokenRelation);
        return i==1;
    }

    public NbiotTokenRelation getRelationByMac(String mac){//根据mac查询关系
        return nbiotTokenRelationMapper.getRelationByMac(mac);
    }
}
