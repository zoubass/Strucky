package cz.zoubelu.dao.mapper;

import cz.zoubelu.model.InformaLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zoubas on 21.5.16.
 */
public class InteractionMapper implements RowMapper<InformaLog> {

    public InformaLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        InformaLog informalog = new InformaLog();
        informalog.setId(rs.getLong("ID"));

        informalog.setRequest_time(rs.getTimestamp("REQUEST_TIME"));
        informalog.setResponse_time(rs.getTimestamp("RESPONSE_TIME"));
        informalog.setApplication(rs.getString("application"));
        informalog.setEnvironment(rs.getString("Environment"));
        informalog.setNode(rs.getString("node"));
        informalog.setMsg_type(rs.getString("Msg_type"));
        informalog.setMsg_version(rs.getInt("Msg_version"));
        informalog.setMsg_uid(rs.getString("Msg_uid"));
        informalog.setMsg_id(rs.getString("Msg_id"));
        informalog.setMsg_src_sys(rs.getInt("Msg_src_sys"));
        informalog.setMsg_src_env(rs.getInt("Msg_src_env"));
        informalog.setMsg_tar_sys(rs.getInt("Msg_tar_sys"));
        informalog.setMsg_tar_env(rs.getInt("Msg_tar_env"));
        informalog.setMsg_priority(rs.getInt("Msg_priority"));
        informalog.setMsg_ttl(rs.getInt("Msg_ttl"));
        informalog.setException(rs.getString("Exception"));
        informalog.setException_message(rs.getString("Exception_message"));
        informalog.setIgnored_exception(rs.getString("Ignored_exception"));
        return informalog;
    }

}
