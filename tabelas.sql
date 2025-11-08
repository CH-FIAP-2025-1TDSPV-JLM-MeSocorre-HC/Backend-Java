CREATE TABLE consulta (
    id NUMBER PRIMARY KEY,
    paciente_id NUMBER NOT NULL,
    paciente_nome VARCHAR2(100) NOT NULL,
    data_consulta VARCHAR2(100) NOT NULL,
    medico_id NUMBER NOT NULL,
    especialidade VARCHAR2(100) NOT NULL
);

CREATE SEQUENCE consulta_seq START WITH 1 INCREMENT BY 1;