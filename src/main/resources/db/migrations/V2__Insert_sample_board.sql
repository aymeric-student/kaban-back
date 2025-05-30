-- Script pour créer une board simple
-- Insertion d'une nouvelle board dans la table board

INSERT INTO board (title)
VALUES ('Mon Premier Tableau Kanban');

-- Pour vérifier que la board a été créée
SELECT * FROM board WHERE title = 'Mon Premier Tableau Kanban';

-- Ajout de trois colonnes à la board
-- On récupère d'abord l'ID de la board créée pour l'utiliser dans les colonnes
INSERT INTO columns (board_id, title)
VALUES
    ((SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban'), 'À faire'),
    ((SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban'), 'En cours'),
    ((SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban'), 'Terminé');

-- Vérification des colonnes créées
SELECT c.column_id, c.title as column_title, b.title as board_title
FROM columns c
         JOIN board b ON c.board_id = b.board_id
WHERE b.title = 'Mon Premier Tableau Kanban'
ORDER BY c.title;

-- Ajout de trois tâches par colonne
-- Tâches pour la colonne "À faire"
INSERT INTO tasks (column_id, title, status)
VALUES
    ((SELECT column_id FROM columns WHERE title = 'À faire' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Analyser les besoins', FALSE),
    ((SELECT column_id FROM columns WHERE title = 'À faire' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Créer les maquettes', FALSE),
    ((SELECT column_id FROM columns WHERE title = 'À faire' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Préparer la documentation', FALSE);

-- Tâches pour la colonne "En cours"
INSERT INTO tasks (column_id, title, status)
VALUES
    ((SELECT column_id FROM columns WHERE title = 'En cours' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Développer API REST', FALSE),
    ((SELECT column_id FROM columns WHERE title = 'En cours' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Configurer la base de données', FALSE),
    ((SELECT column_id FROM columns WHERE title = 'En cours' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Implémenter lauthentification', FALSE);

-- Tâches pour la colonne "Terminé"
INSERT INTO tasks (column_id, title, status)
VALUES
    ((SELECT column_id FROM columns WHERE title = 'Terminé' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Installer PostgreSQL', TRUE),
    ((SELECT column_id FROM columns WHERE title = 'Terminé' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Configurer Spring Boot', TRUE),
    ((SELECT column_id FROM columns WHERE title = 'Terminé' AND board_id = (SELECT board_id FROM board WHERE title = 'Mon Premier Tableau Kanban')), 'Créer les tables', TRUE);

-- Vérification de toutes les tâches créées
SELECT
    b.title as board_title,
    c.title as column_title,
    t.title as task_title,
    t.status
FROM tasks t
         JOIN columns c ON t.column_id = c.column_id
         JOIN board b ON c.board_id = b.board_id
WHERE b.title = 'Mon Premier Tableau Kanban'
ORDER BY c.title, t.title;