package com.sinadao;



import com.engine.model.beans.persist.opinion.CaseInfo;

import javax.sql.DataSource;
import java.util.List;

public interface SinaDAO {
   /** 
    * This is the method to be used to initialize
    * database resources ie. connection.
    */
   public void setDataSource(DataSource ds);
   /** 
    * This is the method to be used to create
    * a record in the Student table.
    */
   public Integer create(String case_id, String track_id, Integer state, String ah, String ajlb, String ajbt, String keyword, String ay, String cbr, String cbfy, String dsrlist, String dsrinfo, String requestTime, String endTime);
   /**
    * This is the method to be used to list down
    * a record from the Student table corresponding
    * to a passed student id.
    */
   public CaseInfo getStudent(Integer id);
   /**
    * This is the method to be used to list down
    * all the records from the Student table.
    */
   public List<CaseInfo> listSinas();
   /**
    * This is the method to be used to delete
    * a record from the Student table corresponding
    * to a passed student id.
    */
   public void delete(Integer id);
   /** 
    * This is the method to be used to update
    * a record into the Student table.
    */
   public void update(Integer id);
}