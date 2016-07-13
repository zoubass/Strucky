package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.ConsumesRelationshipRepository;
import cz.zoubelu.repository.MethodRepository;
import cz.zoubelu.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zoubas on 9.7.16.
 */
@Service
@Transactional
public class GraphServiceImpl implements GraphService {
    @Autowired
    private ApplicationRepository appRepo;

    @Autowired
    private MethodRepository methodRepo;

    @Autowired
    private ConsumesRelationshipRepository consumesRelationshipRepo;

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
    public Method findProvidedMethodOfApplication(Application providingApp, String methodName, Integer methodVersion) {
        return methodRepo.findProvidedMethodOfApplication(providingApp, methodName, methodVersion);
    }

    @Override
    public ConsumeRelationship findRelationship(Application consumingApp, Method consumedMethod) {
        return consumesRelationshipRepo.findRelationship(consumingApp, consumedMethod);
    }


    private Map<String, Object> toD3Format(Iterator<Map<String, Object>> result) {
        List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> rels= new ArrayList<Map<String,Object>>();
        int i=0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(map("title",row.get("movie"),"label","movie"));
            int target=i;
            i++;
            for (Object name : (Collection) row.get("cast")) {
                Map<String, Object> actor = map("title", name,"label","actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source",source,"target",target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String,Object>(2);
        result.put(key1,value1);
        result.put(key2,value2);
        return result;
    }

    public Map<String, Object> graph(int limit) {
        Iterator<Map<String, Object>> result = appRepo.graph().iterator();
        return toD3Format(result);
    }

}
