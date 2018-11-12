package com.sinadao;

import com.engine.model.beans.persist.opinion.CaseInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;


public  class SinaJDBCTemplate implements SinaDAO {
    private DataSource dataSource;
    private static JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public Integer create(String caseId,String track_id, Integer state, String ah, String ajlb, String ajbt, String keyword, String ay, String cbr, String cbfy, String dsrlist, String dsrinfo, String requestTime, String endTime) {
        String SQL = "insert IGNORE  into case_detail (caseId,track_id,state,ah,ajlb,ajbt,keyword ,ay,cbr,cbfy,dsrlist,dsrinfo,requestTime,endTime) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplateObject.update(SQL, caseId,track_id,state,ah,ajlb,ajbt,keyword ,ay,cbr,cbfy,dsrlist,dsrinfo,requestTime,endTime);

        return 6;
    }

    public CaseInfo getStudent(Integer id) {


        String SQL = "select * from case_detail where id = ?";
        CaseInfo Sina = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id}, new CaseInfoMapper());
        return Sina;
    }


    public List<CaseInfo> listSinas() {
        String SQL = "select * from case_detail";
        List<CaseInfo> sinas = jdbcTemplateObject.query(SQL,
                new CaseInfoMapper());
        return sinas;
    }

    public void delete(Integer id) {
        String SQL = "delete from case_detail where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id);
        return;
    }


    public void update(Integer id) {
        String SQL = "update case_detail set name = ? ,password=?  sourceId=?  userId =?  where id = ?";
        jdbcTemplateObject.update(SQL,  id);
        System.out.println("Updated Record with ID = " + id);
        return;
    }
}