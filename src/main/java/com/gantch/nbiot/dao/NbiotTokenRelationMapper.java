package com.gantch.nbiot.dao;

import com.gantch.nbiot.model.NbiotTokenRelation;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NbiotTokenRelationMapper {
    @Insert("INSERT INTO nbiot_token_relation(token, mac, type) VALUES (#{token}, #{mac}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addARelation(NbiotTokenRelation nbiotTokenRelation);

    @Select("SELECT * FROM nbiot_token_relation WHERE token = #{token}")
    NbiotTokenRelation getRelationByToken(@Param("token") String token);

    @Select("SELECT * FROM nbiot_token_relation WHERE mac = #{mac}")
    NbiotTokenRelation getRelationByMac(@Param("mac") String mac);

}
