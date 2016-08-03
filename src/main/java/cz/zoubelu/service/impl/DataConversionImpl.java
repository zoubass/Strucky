package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.*;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.repository.MethodRepository;
import cz.zoubelu.repository.RelationshipRepository;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.ConversionResult;
import cz.zoubelu.utils.SystemID;
import cz.zoubelu.utils.TimeRange;
import cz.zoubelu.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubas on 10.7.16.
 */
@Service
@Transactional
public class DataConversionImpl implements DataConversion {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private InformaDao informaDao;

    @Autowired
    private ApplicationRepository applicationRepo;

    @Autowired
    private MethodRepository methodRepository;

    @Autowired
    private RelationshipRepository relationshipRepo;

    @Autowired
    private Validator validator;


    public ConversionResult convertData(TimeRange timeRange) {
        List<Message> messages = informaDao.getInteractionData(timeRange);
        return convertData(messages);
    }

    public ConversionResult convertData(List<Message> messages) {
        log.info("Starting conversion.");
        ConversionResult result = new ConversionResult();
        try {
            long startTime = System.currentTimeMillis();

            innerConversion(messages);

            long stopTime = System.currentTimeMillis();
            String message = String.format("Conversion successfully completed. Took %s ms.",stopTime-startTime);
            log.info(message);
            result.setMessage(message);
        } catch (Exception e) {
            result.setMessage("Conversion failed, reason: " + e.getMessage());
        }
        return result;
    }

    private void innerConversion(List<Message> messages) {
        for (Message msg : messages) {
            validator.validateMessage(msg);
            Application providingApp = getProvidingApp(msg);
            Method consumedMethod = getConsumedMethod(providingApp, msg);
            Application consumingApp = getConsumerApplication(msg);

            log.info(String.format("Saving relationship - Provider: %s, Method: %s, Consumer: %s.",
                    providingApp.getName(), consumedMethod.getName(), consumingApp.getName()));

            createConsumeRelation(consumingApp, consumedMethod);
        }
    }


    private Application getProvidingApp(Message msg) {
        // vytvořit vyhledání aplikace tak, že si nejdříve zjistí, zda nemá shodu s jménem nebo sysID v číselníku?
        //SystemID system = SystemID.isInSystem(appNAme,tarID);
//        if system != null vyhledej a pak kdyz ne tak se chovej
        Application app = applicationRepo.findByName(StringUtils.upperCase(msg.getApplication()));
        return createApplicationIfNull(app, msg.getApplication(), msg.getMsg_tar_sys());
    }

    private Method getConsumedMethod(Application app, Message msg) {
        Method method = methodRepository.findProvidedMethod(app, msg.getMsg_type(), msg.getMsg_version());
        return createMethodIfNull(app, method, msg);
    }


    private Application getConsumerApplication(Message msg) {
        String systemName = SystemID.getSystemByID(msg.getMsg_src_sys());
        System.out.println("SYSTEM ID: " + msg.getMsg_src_sys());
        return createApplicationIfNull(applicationRepo.findByName(StringUtils.upperCase(systemName)), systemName, msg.getMsg_src_sys());
    }

    private void createConsumeRelation(Application consumer, Method method) {
        ConsumeRelationship consumeRelationship = relationshipRepo.findRelationship(consumer, method);
        if (consumeRelationship != null) {
            log.debug(String.format("Relationship: %s CONSUMES -> %s, already exists, incrementing total usage by 1.",
                    consumer.getName(), method.getName()));
            consumeRelationship.setTotalUsage(consumeRelationship.getTotalUsage() + 1);
        } else {
            consumeRelationship = createRelationship(consumer, method);
        }
        relationshipRepo.save(consumeRelationship);
    }


    /**
     * HELPER METHODS
     **/

    private Application createApplicationIfNull(Application app, String appName, Integer systemId) {
        if (app == null) {
            if (systemId == null) {
                log.info("There is no systemID for the new application: " + appName);
            }
            String name = StringUtils.upperCase(appName);
            app = new Application(name, systemId, new ArrayList<Method>());
            return applicationRepo.save(app);
        } else {
            return app;
        }
    }

    private Method createMethodIfNull(Application app, Method method, Message msg) {
        if (method == null) {
            return createMethod(app, msg);
        }
        return method;
    }

    // NEW METHOD CREATION
    private Method createMethod(Application app, Message msg) {
        Method method = new Method(msg.getMsg_type(), msg.getMsg_version());
        log.debug(String.format("Creating method: %s, version: %s for app %s.", msg.getMsg_type(), msg.getMsg_version(),
                app.getName()));

        if (app.getProvidedMethods() != null) {
            app.getProvidedMethods().add(method);
        } else {
            app.setProvidedMethods(Lists.newArrayList(method));
        }
        applicationRepo.save(app);
        log.debug(String.format("Application: %s successfully saved.", app.getName()));
        return method;
    }

    // NEW RELATIONSHIP IN CASE IT DOESNT EXIST YET
    private ConsumeRelationship createRelationship(Application consumer, Method method) {
        ConsumeRelationship consumeRelationship = new ConsumeRelationship(consumer, method, 1L);
        log.debug(String.format("Creating new relationship: %s CONSUMES -> %s.", consumer.getName(), method.getName()));
        if (consumer.getConsumeRelationship() != null) {
            consumer.getConsumeRelationship().add(consumeRelationship);
        } else {
            consumer.setConsumeRelationship(Lists.newArrayList(consumeRelationship));
        }
        applicationRepo.save(consumer);
        return consumeRelationship;
    }

}
