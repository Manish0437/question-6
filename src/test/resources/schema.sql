CREATE TABLE IF NOT EXISTS researcher (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    specialization VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS project (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    budget DOUBLE
);

CREATE TABLE IF NOT EXISTS researcher_project (
    researcherId INT,
    projectId INT,
    PRIMARY KEY(researcherId, projectId),
    FOREIGN KEY(researcherId) REFERENCES researcher(id),
    FOREIGN KEY(projectId) REFERENCES project(id)
);