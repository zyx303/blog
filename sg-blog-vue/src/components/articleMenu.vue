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
  overflow-y: auto; /* 启用垂直滚动条 */
  max-height: 500px; /* 设置最大高度，可以根据需要调整 */
  position: fixed;
  top: 30%;
  left: 10px;
  width: 12%;
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
  font-size: 1.3em;
  margin-left: 0;
}

.toc-container li.h2 {
  font-weight: bolder;
  font-size: 1.1em;
  margin-left: 10px;
}

.toc-container li.h3 {
  font-weight: normal;
  margin-left: 15px;
}

.toc-container li.h4 {
  font-weight: lighter;
  margin-left: 20px;
}

.toc-container li.h5 {
  font-weight: lighter;
  margin-left: 30px;
}
.toc-container a {
  text-decoration: none;
  color: #333;
}

.toc-container a:hover {
  text-decoration: underline;
}
</style>
