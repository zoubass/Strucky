package cz.zoubelu.service;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zoubas on 9.7.16.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository appRepo;

    @Override
    public void save(Application app) {
        appRepo.save(app);
    }

    @Override
    public List<Application> findAll() {
        return Lists.newArrayList(appRepo.findAll());
    }

}
