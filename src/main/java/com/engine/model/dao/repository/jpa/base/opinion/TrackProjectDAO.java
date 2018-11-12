package com.engine.model.dao.repository.jpa.base.opinion;

import com.engine.model.dao.repository.jpa.CommonRepository;

import com.engine.model.beans.persist.opinion.TrackProject;
import org.springframework.stereotype.Component;

@Component
public interface TrackProjectDAO extends CommonRepository<TrackProject, Integer> {
}
