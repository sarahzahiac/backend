const express = require('express');
const cors = require('cors');
const db = require('./database');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = 8000;


app.get('/persons', (req, res) => {
    db.query('SELECT * FROM persons', (err, rows) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Erreur dans la requête de la database' });
        }
        res.json(rows);
    });
});

app.post('/persons', (req, res) => {
    const { first_name, last_name, email, gender } = req.body;

    db.query(
        'INSERT INTO persons (first_name, last_name, email, gender) VALUES (?, ?, ?, ?)',
        [first_name, last_name, email, gender],
        (err, result) => {
            if (err) {
                console.error(err); 
                return res.status(500).json({ error: "Erreur lors de la création de la nouvelle personne" });
            }

            res.json({
                message: 'Nouvelle personne ajoutée avec succès',
                id: result.insertId 
            });
        }
    );
});


app.put('/persons/:id', (req, res) => {
    const { id } = req.params;
    const { first_name, last_name, email, gender } = req.body;

    db.query(
        'UPDATE persons SET first_name = ?, last_name = ?, email = ?, gender = ? WHERE id = ?',
        [first_name, last_name, email, gender, id],
        (err, result) => {
            if (err) {
                console.error(err);
                return res.status(500).json({ error: "Erreur lors de la modification de la personne" });
            }

            if (result.affectedRows === 0) {
                return res.status(404).json({ error: "Personne non trouvée" });
            }

            res.json({
                message: 'La personne a bien été modifiée',
                id: id
            });
        }
    );
});


app.delete('/persons', (req, res) => {
    const { first_name, last_name } = req.body;

    db.query('DELETE FROM persons WHERE first_name = ? AND last_name = ?', [first_name, last_name], (err, result) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: "Erreur lors de la suppression" });
        }

        if (result.affectedRows === 0) {
            return res.status(404).json({ error: "Personne non trouvée" });
        }

        res.json({ message: "Personne supprimée avec succès" });
    });
});


app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
})