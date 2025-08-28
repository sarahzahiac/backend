const express = require('express');
const cors = require('cors');
const db = require('./database');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = 8000;

/* POST /persons ; PUT /persons/{id} ; DELETE /persons/{id} */

app.post('/persons', (req, res) => {
    const {name, age, gender} = req.body;
    const query = 'INSERT INTO persons (name, age, gender) VALUES (?, ?, ?)';

    db.query(query, [name, age, gender], (err, result) => {
        if (err) {
            console.error('Error inserting person:', err);
            res.status(500).json({error: 'Database error'});
            return;
        }
        res.status(201).json({id: result.insertId, name, age, gender});
    });
})

app.put('/persons/:id', (req, res) => {
    const {id} = req.params;
    const {name, age, gender} = req.body;

    const query = 'UPDATE persons SET name = ?, age = ?, gender = ? WHERE id = ?';
    db.query(query, [name, age, gender, id], (err, result) => {
        if (err) {
            console.error('Error updating person:', err); 
            res.status(500).json({error: 'Database error'});
            return;
        }
        if (result.affectedRows === 0) {
            res.status(404).json({error: 'Person not found'});
            return;
        }   
        res.status(200).json({id, name, age, gender});
    });
});

app.delete('/persons/:id', (req, res) => {
    const {id} = req.params;
    const query = 'DELETE FROM persons WHERE id = ?';

    db.query(query, [id], (err, result) => {
        if (err) {
            console.error('Error deleting person:', err);
            res.status(500).json({error: 'Database error'});
            return;
        }
        if (result.affectedRows === 0) {
            res.status(404).json({error: 'Person not found'});
            return;
        }
        res.status(204).send();
    });
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
})