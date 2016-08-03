package cz.zoubelu.validation;

import cz.zoubelu.domain.Message;

/**
 * Created by zoubas on 2.8.16.
 */
public interface Validator {

    boolean validateMessage(Message msg);
}
