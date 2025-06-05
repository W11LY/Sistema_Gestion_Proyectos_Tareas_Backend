-- Tabla USER
CREATE TABLE IF NOT EXISTS client (
    client_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    names VARCHAR(100) NOT NULL,
    lastnames VARCHAR(100),
    phone VARCHAR(10),
    email VARCHAR(100),
    password VARCHAR(100)
);

-- Tabla PROJECT
CREATE TABLE IF NOT EXISTS project (
    project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(300),
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_project_client FOREIGN KEY (client_id) REFERENCES client(client_id) ON DELETE CASCADE
);

-- Tabla TASK
CREATE TABLE IF NOT EXISTS task (
    task_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(300),
    state BOOLEAN NOT NULL,
    project_id BIGINT NOT NULL,
    CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);
