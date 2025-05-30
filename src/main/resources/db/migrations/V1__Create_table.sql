-- V1__Create_tables.sql
-- Tables Kaban Platform
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table BOARD
CREATE TABLE board (
                       board_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       title VARCHAR(255) NOT NULL UNIQUE
);

-- Table COLUMNS
CREATE TABLE columns (
                         column_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         board_id UUID NOT NULL,
                         title VARCHAR(255) NOT NULL,
                         FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE
);

-- Table TASKS
CREATE TABLE tasks (
                       task_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       column_id UUID NOT NULL,
                       title VARCHAR(500) NOT NULL,
                       status BOOLEAN DEFAULT FALSE,
                       FOREIGN KEY (column_id) REFERENCES columns(column_id) ON DELETE CASCADE
);

-- Table SUBTASK
CREATE TABLE subtask (
                         sub_task_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         task_id UUID NOT NULL,
                         title VARCHAR(300) NOT NULL,
                         completed BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE
);