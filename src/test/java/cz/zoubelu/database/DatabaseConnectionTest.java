package cz.zoubelu.database;


import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.domain.Message;
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

        List<Message> messages = dao.getInteractionData();
        Assert.assertEquals(25968, messages.size());
    }

}