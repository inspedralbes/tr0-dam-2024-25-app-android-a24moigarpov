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
          {{ respuesta.respuesta }} <span v-if="respuesta.correcta">(Correcta)</span>
        </li>
      </ul>
      <div class="button-group">
        <button @click="editQuestion(index)" class="edit-button">Editar</button>
        <button @click="deleteQuestion(question.id)" class="delete-button">Eliminar</button>
      </div>
    </div>
    <form @submit.prevent="saveQuestion">
      <h3>{{ newQuestion.id ? 'Editar Pregunta' : 'Añadir Pregunta' }}</h3>
      <input v-model="newQuestion.pregunta" placeholder="Pregunta" required />
      <input v-model="newQuestion.imatge" placeholder="URL de la Imagen" required />
      <div v-for="(respuesta, idx) in newQuestion.respostes" :key="idx">
        <input v-model="respuesta.respuesta" placeholder="Respuesta" required />
        <label>
          <input type="checkbox" v-model="respuesta.correcta" /> Correcta
        </label>
      </div>
      <button type="submit">{{ newQuestion.id ? 'Actualizar' : 'Añadir' }}</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';  // Importar Axios

export default {
  data() {
    return {
      questions: [],
      newQuestion: {
        id: null,
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
  mounted() {
    this.fetchQuestions();  // Obtener preguntas al montar el componente
  },
  methods: {
    async fetchQuestions() {
      try {
        const response = await axios.get('http://localhost:3000/api/preguntes');
        this.questions = response.data;  // Guardar preguntas en el estado
      } catch (error) {
        console.error('Error fetching questions:', error);
      }
    },
    async saveQuestion() {
      try {
        if (this.newQuestion.id) {
          // Actualizar pregunta existente
          await axios.put(`http://localhost:3000/api/preguntes/${this.newQuestion.id}`, this.newQuestion);
        } else {
          // Añadir nueva pregunta
          await axios.post('http://localhost:3000/api/preguntes', this.newQuestion);
        }
        await this.fetchQuestions(); // Volver a obtener preguntas después de añadir o actualizar
        this.resetForm();
      } catch (error) {
        console.error('Error saving question:', error);
      }
    },
    async editQuestion(index) {
      this.newQuestion = { ...this.questions[index] }; // Cargar la pregunta para editar
    },
    async deleteQuestion(id) {
      try {
        await axios.delete(`http://localhost:3000/api/preguntes/${id}`);
        await this.fetchQuestions(); // Volver a obtener preguntas después de eliminar
      } catch (error) {
        console.error('Error deleting question:', error);
      }
    },
    resetForm() {
      this.newQuestion = {
        id: null,
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
/* (Estilos se mantienen igual) */
</style>
