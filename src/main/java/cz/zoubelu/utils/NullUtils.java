package cz.zoubelu.utils;

import cz.zoubelu.domain.Application;

/**
 * Created by zoubas
 */
public class NullUtils {

    public static void nullCheck(Object obj){
        if (obj == null) throw new NullPointerException("Objekt nesmí být null.");
    }

    public static void nullCheck(Application app){
        if (app == null) throw new IllegalArgumentException("Aplikace nebyla nalezena.");
    }
}
