INSERT INTO researcher (name, specialization)
SELECT 'Marie Curie', 'Radioactivity'
WHERE NOT EXISTS (SELECT 1 FROM researcher WHERE id = 1);

INSERT INTO researcher (name, specialization)
SELECT 'Albert Einstein', 'Relativity'
WHERE NOT EXISTS (SELECT 2 FROM researcher WHERE id = 2);

INSERT INTO researcher (name, specialization)
SELECT 'Isaac Newton', 'Classical Mechanics'
WHERE NOT EXISTS (SELECT 3 FROM researcher WHERE id = 3);

INSERT INTO researcher (name, specialization)
SELECT 'Niels Bohr', 'Quantum Mechanics'
WHERE NOT EXISTS (SELECT 4 FROM researcher WHERE id = 4);

INSERT INTO project (name, budget)
SELECT 'Project Alpha', 50000.00
WHERE NOT EXISTS (SELECT 1 FROM project WHERE id = 1);

INSERT INTO project (name, budget)
SELECT 'Project Beta', 100000.00
WHERE NOT EXISTS (SELECT 2 FROM project WHERE id = 2);

INSERT INTO project (name, budget)
SELECT 'Project Gamma', 150000.00
WHERE NOT EXISTS (SELECT 3 FROM project WHERE id = 3);

INSERT INTO project (name, budget)
SELECT 'Project Delta', 75000.00
WHERE NOT EXISTS (SELECT 4 FROM project WHERE id = 4);

INSERT INTO researcher_project (researcherId, projectId)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM researcher_project WHERE researcherId = 1 AND projectId = 1);

INSERT INTO researcher_project (researcherId, projectId)
SELECT 1, 2
WHERE NOT EXISTS (SELECT 3 FROM researcher_project WHERE researcherId = 1 AND projectId = 2);

INSERT INTO researcher_project (researcherId, projectId)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 2 FROM researcher_project WHERE researcherId = 2 AND projectId = 2);

INSERT INTO researcher_project (researcherId, projectId)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 4 FROM researcher_project WHERE researcherId = 3 AND projectId = 3);

INSERT INTO researcher_project (researcherId, projectId)
SELECT 3, 4
WHERE NOT EXISTS (SELECT 6 FROM researcher_project WHERE researcherId = 3 AND projectId = 4);

INSERT INTO researcher_project (researcherId, projectId)
SELECT 4, 4
WHERE NOT EXISTS (SELECT 5 FROM researcher_project WHERE researcherId = 4 AND projectId = 4);