-- Create Project Table
CREATE TABLE project (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    budget DOUBLE
);

-- Create Researcher Table
CREATE TABLE researcher (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    specialization VARCHAR(255)
);

-- Create Junction Table (Researcher_Project)
CREATE TABLE researcher_project (
    researcherId INTEGER,
    projectId INTEGER,
    FOREIGN KEY (researcherId) REFERENCES researcher(id),
    FOREIGN KEY (projectId) REFERENCES project(id),
    PRIMARY KEY (researcherId, projectId)
);
