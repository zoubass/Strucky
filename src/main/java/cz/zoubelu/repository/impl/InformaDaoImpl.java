package cz.zoubelu.repository.impl;

import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.repository.mapper.InteractionMapper;
import cz.zoubelu.domain.InformaLog;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by zoubas on 25.6.16.
 */
public class InformaDaoImpl implements InformaDao {

    private JdbcTemplate jdbcTemplate;

    public InformaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<InformaLog> getInteractionData() {
        return this.jdbcTemplate.query("select * from INFORMA_LOG", new InteractionMapper());
    }
}
