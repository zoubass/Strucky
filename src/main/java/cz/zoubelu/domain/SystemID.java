package cz.zoubelu.domain;

/**
 * Created by zoubas on 10.7.16.
 */
public enum SystemID {
    //TODO: CREATE CODELIST
    CZGPRINTS(1),

    CZGCALCBM(2),

    CZGEARNIX(3);
    private Integer id;

    SystemID(Integer id) {
        this.id = id;
    }

    Integer getID() {
        return this.id;
    }

    public static String getSystemByID(Integer id) {
        for (SystemID system : values()) {
            if ((id).equals(system.getID())) {
                return system.name();
            }
        }
        return null;
    }

}
