<script>

export default {
  name: "articleMenu",
  data() {
    return {
    };
  },
  props: {
    toc: {
      type: Array,
      required:true
    }
  },
  methods: {
    scrollTo(id) {
      const element = document.getElementById(id);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }
}
</script>

<template>
  <div class="toc-container">
    <ul>
      <li v-for="item in toc" :key="item.id" :class="item.p.toLowerCase()">
        <a :href="'#' + item.id">{{ item.text }}</a>
      </li>
    </ul>
<!--    {{content}}-->
  </div>
</template>

<style scoped>
.toc-container {
  position: fixed;
  top: 30%;
  left: 10px;
  width: 150px;
  background: #f9f9f9;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.toc-container ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.toc-container li {
  margin: 5px 0;
}

.toc-container li.h1 {
  font-weight: bold;
  font-size: 1.2em;
  margin-left: 0;
}

.toc-container li.h2 {
  margin-left: 10px;
}

.toc-container li.h4 {
  margin-left: 20px;
}

.toc-container a {
  text-decoration: none;
  color: #333;
}

.toc-container a:hover {
  text-decoration: underline;
}
</style>
