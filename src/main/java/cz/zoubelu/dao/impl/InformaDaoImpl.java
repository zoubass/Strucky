package cz.zoubelu.dao.impl;

import cz.zoubelu.dao.InformaDao;
import cz.zoubelu.dao.mapper.InteractionMapper;
import cz.zoubelu.model.InformaLog;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by zoubas on 25.6.16.
 */
public class InformaDaoImpl implements InformaDao {

    JdbcTemplate jdbcTemplate;

    public InformaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<InformaLog> getInteractionData() {
        return this.jdbcTemplate.query("select * from INFORMA_LOG", new InteractionMapper());
    }
}
