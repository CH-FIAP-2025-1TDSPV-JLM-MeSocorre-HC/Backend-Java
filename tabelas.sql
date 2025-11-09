-- ============================================
-- SEQUENCES (apenas uma para agendamentos)
-- ============================================

CREATE SEQUENCE agendamentos_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- ============================================
-- TABELA PACIENTE (necessária para a FK)
-- ============================================

CREATE TABLE paciente (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    cpf VARCHAR2(14) NOT NULL,
    idade NUMBER,
    usuario VARCHAR2(50),
    senha VARCHAR2(100)
);

CREATE SEQUENCE paciente_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- ============================================
-- TABELA BASE: AGENDAMENTOS
-- ============================================

CREATE TABLE agendamentos (
    id NUMBER PRIMARY KEY,
    paciente_id NUMBER NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    tipo_agendamento VARCHAR2(20) NOT NULL,
    nome_consulta VARCHAR2(100),
    nome_profissional VARCHAR2(100),
    medico VARCHAR2(100),
    -- Campos específicos para cada tipo
    link VARCHAR2(300),
    endereco VARCHAR2(200),
    tipo_exame VARCHAR2(100),
    resultado_exame VARCHAR2(500),
    CONSTRAINT fk_agendamento_paciente
        FOREIGN KEY (paciente_id)
            REFERENCES paciente(id)
            ON DELETE CASCADE
);