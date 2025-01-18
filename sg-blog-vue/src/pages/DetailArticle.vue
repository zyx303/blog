<!-- 文章详情 -->
<template>
    <div>
        <sg-nav></sg-nav>
        <div class="article-container" id="detail">
            <aside :class="['article-menu-wrapper', {'menu-show': showMenu}]" v-if="toc.length > 0">
                <sg-article-menu :toc="toc"></sg-article-menu>
            </aside>
            <el-tooltip :content="showMenu ? '收起目录' : '展开目录'" placement="right" effect="light">
                <div class="menu-trigger" v-if="toc.length > 0" @click="toggleMenu">
                    <i :class="[showMenu ? 'el-icon-arrow-left' : 'el-icon-arrow-right']"></i>
                </div>
            </el-tooltip>
            <el-row :gutter="30">
                <el-col :sm="24" :md="18">
                    <sg-articleDetail @getToc="getToc"></sg-articleDetail>
                </el-col>
                <el-col :sm="24" :md="6">
                    <sg-rightlist></sg-rightlist>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import rightlist from '../components/rightlist.vue'
import articleDetail from '../components/articleDetail.vue'
import articleMenu from "../components/articleMenu.vue"

export default {
    name: 'DetailShare',
    data() {
        return {
            toc: [],
            showMenu: true
        }
    },
    methods: {
        getToc(toc) {
            this.toc = toc;
        },
        toggleMenu() {
            this.showMenu = !this.showMenu;
        }
    },
    components: {
        'sg-nav': header,
        'sg-articleDetail': articleDetail,
        'sg-rightlist': rightlist,
        'sg-article-menu': articleMenu
    },
    mounted() {
        const anchor = document.querySelector("#detail");
        if (anchor) {
            const top = anchor.offsetTop - 60;
            window.scrollTo({
                top: top,
                behavior: 'smooth'
            });
        }
    }
}
</script>

<style>
.article-container {
    position: relative;
    max-width: 1400px;
    margin: 20px auto 0;
    padding: 0 15px 30px;
    min-height: calc(100vh - 100px);
}

.menu-trigger {
    position: fixed;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    background-color: #f5f7fa;
    padding: 15px 8px;
    border-radius: 0 8px 8px 0;
    box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    z-index: 1001;
    transition: all 0.3s ease;
}

.menu-trigger:hover {
    background-color: #ecf5ff;
}

.menu-trigger i {
    font-size: 16px;
    color: #909399;
    transition: all 0.3s ease;
}

.menu-trigger:hover i {
    color: #409EFF;
}

.article-menu-wrapper {
    position: fixed;
    left: -280px;
    top: 60px;
    width: 280px;
    height: calc(100vh - 60px);
    background-color: transparent;
    padding: 20px 0;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 1000;
}

.article-menu-wrapper .article-menu {
    background-color: rgba(245, 247, 250, 0.9);
    backdrop-filter: blur(10px);
    border: 1px solid #e4e7ed;
    border-radius: 4px;
    box-shadow: 2px 0 15px rgba(0, 0, 0, 0.1);
    height: 100%;
    padding: 15px;
}

.menu-show {
    left: 0;
    transform: translateX(0);
}

.menu-show + .menu-trigger {
    left: 280px;
}

@media screen and (max-width: 768px) {
    .article-menu-wrapper {
        display: none;
    }
    
    .menu-trigger {
        display: none;
    }
    
    .article-container {
        padding: 0 10px 20px;
    }
}

.katex-html {
    display: none;
}
</style>
