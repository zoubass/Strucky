package cz.zoubelu.domain;

/**
 * Created by zoubas on 10.7.16.
 * This enum represents the application schema of graph database
 */
public enum SystemID {

    //TODO: přidat parametr name a pod tím aplikaci zobrazovat
    IBM_MB(0),
    CZGUSRMNG(1),
    CZGUCINFO(2),
    CZGPOLINFO(3),
    CZGPOLICING(4),
    CZGMESSAGING(5),
    CZGCALCBM(6),
    CZGDELIVERY(7),
    CZGPRINTS(8),
    CZGDA(9),
    CZGAGENTINFO(11),
    CZGCLAIMS(12),
    CZGCODELIST(13),
    CZGCARPRICING(14),
    CZGPRODCATALOG(15),
    CZGAGENTINFO_16(16),
    CZGEARNIX(17),
    CZGSEARCH(18),
    CZGCOMM(19),
    CZGLEADMNG(20),
    CZGBLACKLISTING(21),
    CZGCONTRACTIMPORT(22),
    CZGPROCESSSUPPORT(23),
    CZCTIA(24),
    MONITORING(99),
    CZGLISA(141),
    PEGAS_KUKÁTKO(100),
    SCA(101),
    PORADCE(102),
    HUGO(103),
    FAC_APP(104),
    POVINNÉ_RUČENÍ_IPAD(105),
    POVINNÉ_RUČENÍ_ANDRIOD(106),
    PŠP_KLIENT_DESKTOP(107),
    BERTA(108),
    EREGRESY(109),
    EAGENTI(110),
    FIP_PARTNERS(111),
    SFTPBRIDGE(112),
    WEB_GENERALI(113),
    NEDORUČENÁ_POŠTA_LN(114),
    MESSAGE_HOUSE(115),
    EPOHLEDAVKY(116),
    MESSAGE_HOUSE2(134),
    LISA(141),
    MICROSITE_POV(201),
    AWD(1000),
    NHUGO(1001),
    UNICREDIT_LEASING(1003),
    INSIA(1004),
    MODUL_SERVIS_ČSOB_LEASING(1005),
    TOTAL_BROKERS(1006),
    PFP(1007),
    FIXUM(1008),
    DATALIFE(1009),
    OK_GROUP(1010),
    SALVE_FINANCE(1011),
    RENOMIA(1012),
    INWEB(1013),
    SMS_FINANČNÍ_PORADENSTVÍ(1014),
    FSS_PARTNERS(1015),
    VOLKSWAGEN_FINANCIAL_SERVICES(1016),
    AXIMA(1017),
    CESKY_OBCHODNI_SERVIS(1018),
    FEDS(1019),
    OVB(1020);

    private Integer id;

    SystemID(Integer id) {
        this.id = id;
    }

    public Integer getID() {
        return this.id;
    }

    public static String getSystemByID(Integer id) {
        for (SystemID system : values()) {
            if ((id).equals(system.getID())) {
                // v budoucnu vracet parametr name, viz @SystemID line 8
                return system.name();
            }
        }
        return id.toString();
    }

}
