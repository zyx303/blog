<!-- 首页 -->
<template>
    <div class="home">
        <sg-navbar></sg-navbar>
        <div class="container">
            <el-row  :gutter="30">
                <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;opacity: 0.96;">
                    <sg-articlelist></sg-articlelist>
                </el-col>
                <el-col :sm="24"  :md="8" >
                    <sg-rightlist></sg-rightlist>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import articlelist from '../components/articlelist.vue'
import rightlist from '../components/rightlist.vue'
    export default {
        name:'Home',
        data() { //选项 / 数据
            return {

            }
        },
        methods: { //事件处理器

        },
        components: { //定义组件
            'sg-navbar':header,
            'sg-articlelist':articlelist,
            'sg-rightlist':rightlist,
        },
        created() { //生命周期函数

        }
    }
</script>

<style>
.home {
  background-image: url('../../static/img/keduoli.jpg');
  background-size: cover;  /* 让背景图片覆盖整个元素 */
  background-position: center;  /* 让背景图片居中显示 */
}
</style>
