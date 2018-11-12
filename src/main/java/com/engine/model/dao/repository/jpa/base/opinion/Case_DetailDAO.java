package com.engine.model.dao.repository.jpa.base.opinion;

import com.engine.model.beans.persist.opinion.case_detail;
import com.engine.model.dao.repository.jpa.CommonRepository;
import org.springframework.stereotype.Component;

@Component
public  interface Case_DetailDAO extends CommonRepository<case_detail, Integer> {
}