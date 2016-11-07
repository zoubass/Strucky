package cz.zoubelu.utils;

import cz.zoubelu.domain.Application;

/**
 * Created by zoubas
 */
public class NullUtils {

    public static void nullCheck(Object obj,String message){
        if (obj == null) throw new NullPointerException(message);
    }
}
