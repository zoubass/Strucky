package cz.zoubelu.service.impl;

import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.service.Visualization;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by t922274 on 14.7.2016.
 */
public class VisualizationImpl implements Visualization{
	@Autowired
	private ApplicationRepository appRepo;

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

	@Override
	public Map<String, Object> visualizeGraph() {
		Iterator<Map<String, Object>> result = appRepo.getGraph().iterator();
		return toD3Format(result);
	}
}