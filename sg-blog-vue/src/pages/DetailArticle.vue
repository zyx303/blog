<!-- 文章详情 -->
<template>
    <div>
        <sg-nav></sg-nav>
        <div  class="article-container" id="detail">
            <el-row  :gutter="30">
                <sg-article-menu :toc="toc"></sg-article-menu>
                <el-col :sm="24" :md="18" style="transition:all .5s ease-out;margin-bottom:30px;">
                    <sg-articleDetail @getToc="getToc"></sg-articleDetail>
                    <!-- <sg-message></sg-message> -->
                </el-col>
                <el-col :sm="24"  :md="6" >
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
import message from '../components/message.vue'
import articleMenu  from "../components/articleMenu.vue";

    export default {
        name:'DetailShare',
        data() { //选项 / 数据
            return {
              toc:''
            }
        },
        methods: { //事件处理器
            getToc(toc){
                this.toc = toc;
                // console.log(this.content)
            }
        },
        components: { //定义组件
            'sg-nav':header,
            'sg-articleDetail':articleDetail,
            'sg-message':message,
            'sg-rightlist':rightlist,
            'sg-article-menu':articleMenu
        },
        created() { //生命周期函数

        },
        mounted(){
            var anchor = document.querySelector("#detail");
            // console.log(anchor,anchor.offsetTop);
            var top = anchor.offsetTop-60;
            document.body.scrollTop = top;
             // Firefox
             document.documentElement.scrollTop = top;
             // Safari
             window.pageYOffset = top;
        }
    }
</script>

<style>
.bg {
  background-image: url('../../static/img/bg1.jpg');
  background-size: cover;  /* 让背景图片覆盖整个元素 */
  background-position: center;  /* 让背景图片居中显示 */
}
.article-container{
  max-width: 85%;
  margin-left: auto;
  margin-top: 20px;
  padding:0 10px;
}
</style>
