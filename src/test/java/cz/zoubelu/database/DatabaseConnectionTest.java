package cz.zoubelu.database;


import cz.zoubelu.dao.InformaDao;
import cz.zoubelu.model.InformaLog;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zoubas
 */
public class DatabaseConnectionTest extends AbstractTest {
    @Autowired
    private InformaDao dao;

    @Test
    public void testConnection() {
        Assert.assertNotNull(dao);

        List<InformaLog> informaLogs = dao.getInteractionData();
        Assert.assertEquals(8, informaLogs.size());
    }

}