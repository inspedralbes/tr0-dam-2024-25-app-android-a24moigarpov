const express = require('express');
const fs = require('fs');
const path = require('path');  // Importar el módulo 'path' para manejar rutas
const cors = require('cors');  // Importar cors

const app = express();
const PORT = 3000;

// Middleware
app.use(cors());  // Habilitar CORS para todas las rutas
app.use(express.json());  // Para parsear JSON

// Ruta para obtener todas las preguntas
app.get('/api/preguntes', function(req, res) {
    const filePath = path.join(__dirname, 'preguntes.json'); // Usar path.join para asegurar la ruta correcta
    fs.readFile(filePath, 'utf8', function(err, data) {
        if (err) {
            console.error(err);  // Imprimir el error en la consola
            return res.status(500).send({ message: 'Error al leer el archivo' });
        }
        res.send(JSON.parse(data).preguntes);
    });
});

// Ruta para obtener una pregunta por ID
app.get('/api/preguntes/:id', function(req, res) {
    const id = parseInt(req.params.id);
    const filePath = path.join(__dirname, 'preguntes.json');
    fs.readFile(filePath, 'utf8', function(err, data) {
        if (err) {
            console.error(err);
            return res.status(500).send({ message: 'Error al leer el archivo' });
        }
        const preguntes = JSON.parse(data).preguntes;
        const pregunta = preguntes.find(function(p) {
            return p.id === id;
        });
        if (pregunta) {
            res.send(pregunta);
        } else {
            res.status(404).send({ message: 'Pregunta no encontrada' });
        }
    });
});

// Ruta para agregar una nueva pregunta
app.post('/api/preguntes', function(req, res) {
    const newPregunta = req.body;
    const filePath = path.join(__dirname, 'preguntes.json');
    fs.readFile(filePath, 'utf8', function(err, data) {
        if (err) {
            console.error(err);
            return res.status(500).send({ message: 'Error al leer el archivo' });
        }
        const json = JSON.parse(data);
        newPregunta.id = json.preguntes.length + 1;  // Asignar un nuevo ID
        json.preguntes.push(newPregunta);
        fs.writeFile(filePath, JSON.stringify(json, null, 2), function(err) {
            if (err) {
                console.error(err);
                return res.status(500).send({ message: 'Error al guardar la pregunta' });
            }
            res.status(201).send(newPregunta);
        });
    });
});

// Ruta para actualizar una pregunta
app.put('/api/preguntes/:id', function(req, res) {
    const id = parseInt(req.params.id);
    const updatedPregunta = req.body;
    const filePath = path.join(__dirname, 'preguntes.json');
    fs.readFile(filePath, 'utf8', function(err, data) {
        if (err) {
            console.error(err);
            return res.status(500).send({ message: 'Error al leer el archivo' });
        }
        const json = JSON.parse(data);
        const index = json.preguntes.findIndex(function(p) {
            return p.id === id;
        });
        if (index !== -1) {
            json.preguntes[index] = Object.assign({}, json.preguntes[index], updatedPregunta);
            fs.writeFile(filePath, JSON.stringify(json, null, 2), function(err) {
                if (err) {
                    console.error(err);
                    return res.status(500).send({ message: 'Error al guardar la pregunta actualizada' });
                }
                res.send(json.preguntes[index]);
            });
        } else {
            res.status(404).send({ message: 'Pregunta no encontrada' });
        }
    });
});

// Ruta para eliminar una pregunta
app.delete('/api/preguntes/:id', function(req, res) {
    const id = parseInt(req.params.id);
    const filePath = path.join(__dirname, 'preguntes.json');
    fs.readFile(filePath, 'utf8', function(err, data) {
        if (err) {
            console.error(err);
            return res.status(500).send({ message: 'Error al leer el archivo' });
        }
        const json = JSON.parse(data);
        const index = json.preguntes.findIndex(function(p) {
            return p.id === id;
        });
        if (index !== -1) {
            json.preguntes.splice(index, 1);
            fs.writeFile(filePath, JSON.stringify(json, null, 2), function(err) {
                if (err) {
                    console.error(err);
                    return res.status(500).send({ message: 'Error al guardar la pregunta eliminada' });
                }
                res.send({ message: 'Pregunta eliminada' });
            });
        } else {
            res.status(404).send({ message: 'Pregunta no encontrada' });
        }
    });
});

app.listen(PORT, function() {
    console.log('Servidor corriendo en http://localhost:' + PORT);
});