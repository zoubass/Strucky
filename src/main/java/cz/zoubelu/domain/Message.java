package cz.zoubelu.domain;

import java.sql.Timestamp;

/**
 * Model of one interaction between applications
 * This object represents one row in Message table, INFORMA_LOG database
 */
public class Message {
    private Long id;
    private Timestamp request_time;
    private Timestamp response_time;
    private String application;
    private String environment;
    private String node;
    private String msg_type;
    private Integer msg_version;
    private String msg_uid;
    private String msg_id;
    private Integer msg_src_sys;
    private Integer msg_src_env;
    private Integer msg_tar_sys;
    private Integer msg_tar_env;
    private Integer msg_priority;
    private Integer msg_ttl;
    private String exception;
    private String exception_message;
    private String ignored_exception;

    //default constructor
    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getRequest_time() {
        return request_time;
    }

    public void setRequest_time(Timestamp request_time) {
        this.request_time = request_time;
    }

    public Timestamp getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Timestamp response_time) {
        this.response_time = response_time;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public Integer getMsg_version() {
        return msg_version;
    }

    public void setMsg_version(Integer msg_version) {
        this.msg_version = msg_version;
    }

    public String getMsg_uid() {
        return msg_uid;
    }

    public void setMsg_uid(String msg_uid) {
        this.msg_uid = msg_uid;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public Integer getMsg_src_sys() {
        return msg_src_sys;
    }

    public void setMsg_src_sys(Integer msg_src_sys) {
        this.msg_src_sys = msg_src_sys;
    }

    public Integer getMsg_src_env() {
        return msg_src_env;
    }

    public void setMsg_src_env(Integer msg_src_env) {
        this.msg_src_env = msg_src_env;
    }

    public Integer getMsg_tar_sys() {
        return msg_tar_sys;
    }

    public void setMsg_tar_sys(Integer msg_tar_sys) {
        this.msg_tar_sys = msg_tar_sys;
    }

    public Integer getMsg_tar_env() {
        return msg_tar_env;
    }

    public void setMsg_tar_env(Integer msg_tar_env) {
        this.msg_tar_env = msg_tar_env;
    }

    public Integer getMsg_priority() {
        return msg_priority;
    }

    public void setMsg_priority(Integer msg_priority) {
        this.msg_priority = msg_priority;
    }

    public Integer getMsg_ttl() {
        return msg_ttl;
    }

    public void setMsg_ttl(Integer msg_ttl) {
        this.msg_ttl = msg_ttl;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getException_message() {
        return exception_message;
    }

    public void setException_message(String exception_message) {
        this.exception_message = exception_message;
    }

    public String getIgnored_exception() {
        return ignored_exception;
    }

    public void setIgnored_exception(String ignored_exception) {
        this.ignored_exception = ignored_exception;
    }
}
