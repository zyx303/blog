<template>
  <div class="article-menu">
    <ul>
      <li v-for="item in toc" :key="item.id" :class="item.p.toLowerCase()">
        <a @click.prevent="scrollTo(item.id)">{{ item.text }}</a>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "articleMenu",
  props: {
    toc: {
      type: Array,
      required: true
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

<style scoped>
.article-menu {
  height: 100%;
  overflow-y: auto;
  box-sizing: border-box;
}

.article-menu::-webkit-scrollbar {
  width: 4px;
}

.article-menu::-webkit-scrollbar-thumb {
  background: rgba(144, 147, 153, 0.3);
  border-radius: 4px;
}

.article-menu::-webkit-scrollbar-thumb:hover {
  background: rgba(144, 147, 153, 0.5);
}

.article-menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.article-menu li {
  padding: 4px 0;
  line-height: 1.5;
}

.article-menu li a {
  display: block;
  color: #606266;
  text-decoration: none;
  transition: all 0.3s ease;
  cursor: pointer;
  font-size: 14px;
  padding: 6px 12px;
  border-radius: 4px;
  opacity: 0.9;
}

.article-menu li a:hover {
  color: #409EFF;
  background-color: #ecf5ff;
  padding-left: 16px;
  opacity: 1;
}

.article-menu li.h1 {
  font-weight: 600;
  margin-left: 0;
}

.article-menu li.h1 a {
  font-size: 15px;
  color: #303133;
}

.article-menu li.h2 {
  font-weight: 500;
  margin-left: 12px;
}

.article-menu li.h2 a {
  color: #606266;
}

.article-menu li.h3 {
  font-weight: normal;
  margin-left: 24px;
}

.article-menu li.h3 a {
  color: #909399;
  font-size: 13px;
}

.article-menu li.h4 {
  margin-left: 36px;
}

.article-menu li.h4 a {
  color: #909399;
  font-size: 13px;
  opacity: 0.9;
}

.article-menu li.h5 {
  margin-left: 48px;
}

.article-menu li.h5 a {
  color: #909399;
  font-size: 12px;
  opacity: 0.8;
}
</style>
