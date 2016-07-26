package cz.zoubelu.service.impl;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.service.Visualization;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by t922274 on 14.7.2016.
 */
public class VisualizationImpl implements Visualization {
    @Autowired
    private ApplicationRepository appRepo;

    private Map<String, Object> toD3Format(Iterator<Map<String, Object>> result) {
        //TODO: opravit
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> rels = new ArrayList<Map<String, Object>>();
        int i = 0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(map("label", "application", "title", ((Application)row.get("application")).getName()));
            Map<String, Object> method = map("label", "method", "title", ((Method)row.get("method")).getName());
            int target = nodes.indexOf(method);
            if (target == -1) {
                nodes.add(method);
                target = i++;
            }
//            if(row.get("rel");
            rels.add(mapRelation("source", i, "target", target,"type","PROVIDES","count", "0"));
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }
    private Map<String, Object> mapRelation(String key1, Object value1, String key2, Object value2,String key3, Object value3,String key4, Object value4) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        result.put(key3, value3);
        result.put(key4, value4);
        return result;
    }

    @Override
    public Map<String, Object> visualizeGraph() {
        Iterator<Map<String, Object>> result = appRepo.getGraph().iterator();
        return toD3Format(result);
    }
}
