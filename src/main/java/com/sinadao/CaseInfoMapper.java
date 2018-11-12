package com.sinadao;


import com.engine.model.beans.persist.opinion.CaseInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CaseInfoMapper implements RowMapper<CaseInfo> {
   public CaseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
      CaseInfo CaseInfo= new CaseInfo();

//      Sina.setName(rs.getString("name"));
//      CaseInfo.setId(rs.getString("name"));
      CaseInfo.setAy(rs.getString("ay"));
      CaseInfo.setKeyword(rs.getString("keyword"));


      return CaseInfo;
   }
}