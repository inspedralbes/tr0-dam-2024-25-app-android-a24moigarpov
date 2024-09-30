<template>
    <div class="editor-container">
      <button @click="resetForm" class="add-button">Añadir Pregunta</button>

      <h1>Editor de Preguntas</h1>
      
  
      <div v-if="questions.length === 0">No hay preguntas disponibles.</div>
      <div v-for="(question, index) in questions" :key="index" class="question-card">
        <div class="question">
          <strong>{{ question.pregunta }}</strong>
          <img :src="question.imatge" alt="Pregunta Imatge" class="question-image" />
        </div>
        <ul class="respuesta-list">
          <li v-for="(respuesta, idx) in question.respostes" :key="idx">
            {{ respuesta.resposta }} <span v-if="respuesta.correcta">(Correcta)</span>
          </li>
        </ul>
        <div class="button-group">
          <button @click="editQuestion(index)" class="edit-button">Editar</button>
          <button @click="deleteQuestion(index)" class="delete-button">Eliminar</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import preguntas from './preguntas.json';
  
  export default {
    data() {
      return {
        questions: preguntas.preguntes,
        newQuestion: {
          pregunta: '',
          imatge: '',
          respostes: [
            { respuesta: '', correcta: false },
            { respuesta: '', correcta: false },
            { respuesta: '', correcta: false },
            { respuesta: '', correcta: false }
          ],
        },
      };
    },
    methods: {
      editQuestion(index) {
        this.newQuestion = { ...this.questions[index] };
        this.questions.splice(index, 1);
      },
      deleteQuestion(index) {
        this.questions.splice(index, 1);
      },
      resetForm() {
        this.newQuestion = {
          pregunta: '',
          imatge: '',
          respostes: [
            { respuesta: '', correcta: false },
            { respuesta: '', correcta: false },
            { respuesta: '', correcta: false },
            { respuesta: '', correcta: false }
          ],
        };
      },
    },
  };
  </script>
  
  <style scoped>
  .editor-container {
    max-width: 800px;
    margin: auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }
  
  h1 {
    text-align: center;
  }
  
  .add-button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    float: right;
    margin-bottom: 20px;
  }
  
  .question-card {
    background-color: #fff;
    padding: 15px;
    margin: 10px 0;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  }
  
  .question {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .question-image {
    max-width: 150px;
    max-height: 100px;
    margin-left: 10px;
  }
  
  .respuesta-list {
    list-style-type: none;
    padding: 0;
  }
  
  .button-group {
    display: flex;
    justify-content: flex-end;
  }
  
  .edit-button, .delete-button {
    margin-left: 10px;
    padding: 5px 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .edit-button {
    background-color: #FFA500;
    color: white;
  }
  
  .delete-button {
    background-color: #f44336;
    color: white;
  }
  </style>
  