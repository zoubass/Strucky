package cz.zoubelu.validation.impl;

import cz.zoubelu.domain.Message;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by zoubas on 2.8.16.
 */
public class MessageValidator extends AbstractValidator {
    private final Logger log = Logger.getLogger(getClass());

    protected boolean valid(Message msg) {
        if (msg == null) {
            log.error("Message is null.");
            return false;
        } else if (StringUtils.isEmpty(msg.getApplication())) {
            log.error("Message doesn't specify the consumed application");
            return false;
        } else if (msg.getMsg_version() == null) {
            log.error("Consumed method version is null.");
            //TODO: řešit jinak, validace by neměla měnit fieldy Message, to by nikdo nečekal.
            msg.setMsg_version(0);
        }
        return true;
    }
}
