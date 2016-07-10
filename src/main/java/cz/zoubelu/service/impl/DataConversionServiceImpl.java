package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.*;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.service.ApplicationService;
import cz.zoubelu.service.DataConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zoubas on 10.7.16.
 */
@Service
@Transactional
public class DataConversionServiceImpl implements DataConversionService {

    @Autowired
    private InformaDao informaDao;

    @Autowired
    private ApplicationService appService;

    @Override
    public void convertToGraphData() {
        List<Message> messages = informaDao.getInteractionData();
        for (Message msg : messages) {
            Application providingApp = appService.findByName(msg.getApplication());
            Method consumedMethod = null;
            if (providingApp != null) {
                consumedMethod = appService.findMethodOfApplication(providingApp, msg.getMsg_type(), msg.getMsg_version());
            } else {
                //TODO: vyhodit exception, nebyla nalezena applikace, nebo pro dynamicke chovani, ji vytvorit spolecne s touhle metodou
            }
            String system = SystemID.getSystemByID(msg.getMsg_src_sys());
            Application consumingApp = null;
            if (system != null) {
                consumingApp = appService.findByName(system);
            } else {
                //TODO: what to do when system id is not found
            }
            ConsumeRelationship consumeRelationship = appService.findRelationship(consumingApp, consumedMethod);
            if (consumeRelationship != null) {
                consumeRelationship.setTotalUsage(consumeRelationship.getTotalUsage() + 1);
            } else {
                consumingApp.setConsumeRelationship(Lists.newArrayList(new ConsumeRelationship(consumingApp, consumedMethod, 1L)));
            }

        }
    }

    //FIXME remove
/*
    private Method getMethodByName(Application ownerApplication, String name) {
        List<Method> methods = new ArrayList<>();
        methods.addAll(ownerApplication.getProvidedMethods());
        for (Method method : methods) {
            if ((name).equals(method.getName())) {
                return method;
            }
        }
        // pokud by vratil novou metodu, bylo by to dynamické v případě, že časem přibyde metoda k aplikaci
        return null;
    }
    */
}
