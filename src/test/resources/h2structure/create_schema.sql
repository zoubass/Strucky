CREATE TABLE IF NOT EXISTS MESSAGE_201606 (
  ID                NUMBER(19, 0) NOT NULL,
  REQUEST_TIME      TIMESTAMP(6)  NULL,
  RESPONSE_TIME     TIMESTAMP(6)  NULL,
  APPLICATION       VARCHAR2(20)  NULL,
  ENVIRONMENT       VARCHAR2(10)  NULL,
  NODE              VARCHAR2(64)  NULL,
  MSG_TYPE          VARCHAR2(30)  NULL,
  MSG_VERSION       NUMBER(10, 0) NULL,
  MSG_UID           VARCHAR2(40)  NULL,
  MSG_ID            VARCHAR2(120) NULL,
  MSG_SRC_SYS       NUMBER(10, 0) NULL,
  MSG_SRC_ENV       NUMBER(10, 0) NULL,
  MSG_TAR_SYS       NUMBER(10, 0) NULL,
  MSG_TAR_ENV       NUMBER(10, 0) NULL,
  MSG_PRIORITY      NUMBER(10, 0) NULL,
  MSG_TTL           NUMBER(10, 0) NULL,
  EXCEPTION         VARCHAR2(240) NULL,
  EXCEPTION_MESSAGE VARCHAR2(240) NULL,
  IGNORED_EXCEPTION VARCHAR2(240) NULL,
  CONSTRAINT pk_id PRIMARY KEY (ID)
);

CREATE SEQUENCE seq_id
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10