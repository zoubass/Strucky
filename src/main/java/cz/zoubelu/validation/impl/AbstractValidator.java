package cz.zoubelu.validation.impl;

import cz.zoubelu.domain.Message;
import cz.zoubelu.validation.Validator;

/**
 * Created by zoubas on 2.8.16.
 */
public abstract class AbstractValidator implements Validator{

    @Override
    public boolean validateMessage(Message msg) {
        return valid(msg);
    }

    /**
     *
     * @param msg
     * @return true if valid
     */
    protected abstract boolean valid(Message msg);
}
