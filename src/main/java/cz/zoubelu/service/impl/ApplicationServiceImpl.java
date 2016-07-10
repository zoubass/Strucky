package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.service.ApplicationService;
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
    public void save(List<Application> apps) {
        appRepo.save(apps);
    }

    @Override
    public List<Application> findAll() {
        return Lists.newArrayList(appRepo.findAll());
    }

    @Override
    public Application findByName(String name) {
        return appRepo.findByName(name);
    }

    @Override
    public Method findMethodOfApplication(Application providingApp, String methodName, Integer methodVersion) {
        return appRepo.findMethodOfApplication(providingApp, methodName, methodVersion);
    }

    @Override
    public ConsumeRelationship findRelationship(Application consumingApp, Method consumedMethod) {
        return appRepo.findRelationship(consumingApp, consumedMethod);
    }

}
