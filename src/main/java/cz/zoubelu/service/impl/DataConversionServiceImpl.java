package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.*;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.service.DataConversionService;
import cz.zoubelu.service.GraphService;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 10.7.16.
 */
@Service
@Transactional
public class DataConversionServiceImpl implements DataConversionService {

    @Autowired
    private InformaDao informaDao;

    @Autowired
    private GraphService graphService;

    private boolean isAppModified = false;
    private boolean isRelationModified = false;

    @Override
    public void convertData() {
        List<Message> messages = informaDao.getInteractionData();
        for (Message msg : messages) {
            Application providingApp = graphService.findByName(msg.getApplication());
            Method consumedMethod = getConsumedMethod(providingApp, msg);
            Application consumingApp = getConsumedApplication(msg);
            createConsumeRelation(consumingApp, consumedMethod);
            //TODO: what about response? :D
        }
    }

    //TODO: opakuje se mi stejny vzor if(true) init object if (object null) action >> else action
    private Method getConsumedMethod(Application app, Message msg) {
        Method method;
        if (app != null) {
            method = graphService.findProvidedMethodOfApplication(app, msg.getMsg_type(), msg.getMsg_version());
            if (method == null) {
                method = createMethodSet(msg);
                app.getProvidedMethods().add(method);
                graphService.save(app);
            }
        } else {
            // pokud neexistuje aplikace, vytvoří se a vratí se její, také vytvořená, metoda
            method = createMethodSet(msg);
            Set<Method> methods = new HashSet(Arrays.asList(new Method[]{method}));
            Application providingApp = new Application(msg.getApplication(), methods);
            graphService.save(providingApp);
        }
        return method;
    }

    //TODO: opakuje se mi stejny vzor if(true) init object if (object null) action >> else action
    private Application getConsumedApplication(Message msg) {
        Application consumer;
        String system = SystemID.getSystemByID(msg.getMsg_src_sys());
        if (system != null) {
            consumer = graphService.findByName(system);
            if (consumer == null) {
                consumer = createConsumerApp(system);
            }
        } else {
            // neni v ciselniku konzumentů, vytvori se novy node
            consumer = createConsumerApp(msg.getMsg_src_sys().toString());
        }
        return consumer;
    }

    private void createConsumeRelation(Application consumer, Method method) {
        ConsumeRelationship consumeRelationship = graphService.findRelationship(consumer, method);
        if (consumeRelationship != null) {
            consumeRelationship.setTotalUsage(consumeRelationship.getTotalUsage() + 1);
        } else {
            consumer.setConsumeRelationship(Lists.newArrayList(new ConsumeRelationship(consumer, method, 1L)));
        }
    }

    private final Method createMethodSet(Message msg) {
        //TODO: zobecnit a nebo se zamyslet zda to teda potrebuju?
        return new Method(msg.getMsg_type(), msg.getMsg_version());
    }

    private final Application createConsumerApp(String name) {
        Application consumer = new Application(name, null);
        graphService.save(consumer);
        return consumer;
    }

}
