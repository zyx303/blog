<!-- 文章详情模块 -->
<template>
        <div class="detailBox tcommonBox" >
            <span class="s-round-date">
                <span class="month" v-html="showInitDate(detailObj.createTime,'month')+'月'"></span>
                <span class="day" v-html="showInitDate(detailObj.createTime,'date')"></span>
            </span>
            <header>
                <h1>
                    <a :href="'#/DetailShare?aid='+detailObj.id" target="_blank">
                        {{detailObj.title}}
                    </a>
                </h1>
                <h2>
                    <i class="fa fa-fw fa-user"></i>发表于 <span >{{detailObj.createTime}}</span> •
                    <!-- <i class="fa fa-fw fa-eye"></i>{{detailObj.viewCount}} 次围观 • -->
                </h2>
                <div class="ui label">
                    <a :href="'#/Share?classId='+detailObj.categoryId">{{detailObj.categoryName}}</a>
                </div>
            </header>
            <div class="article-content markdown-body" v-html="detailObj.content" ref="articleHTML"></div>

            <!-- <div class="donate">
                <div class="donate-word">
                    <span @click="pdonate=!pdonate">赞赏</span>
                </div>
                <el-row :class="pdonate?'donate-body':'donate-body donate-body-show'" :gutter="30">
                    <el-col  :span="12"   class="donate-item">
                        <div class="donate-tip">
                            <img :src="detailObj.wechat_image?detailObj.wechat_image: 'static/img/wx_pay.png'" :onerror="$store.state.errorImg"/>
                            <span>微信扫一扫，向我赞赏</span>
                        </div>
                    </el-col>
                    <el-col :span="12"  class="donate-item">
                        <div class="donate-tip">
                            <img :src="detailObj.alipay_image?detailObj.alipay_image:'static/img/ali_pay.jpg'" :onerror="$store.state.errorImg"/>
                            <span>支付宝扫一扫，向我赞赏</span>
                        </div>
                    </el-col>
                </el-row>
            </div> -->
        </div>
</template>

<script>
import {initDate} from '../utils/server.js'
import {getArticle,updateViewCount} from '../api/article.js'
import { mavonEditor } from 'mavon-editor'
import katex from "katex"
import hljs from "highlight.js"
import 'highlight.js/styles/github.css'

    export default {
        data() { //选项 / 数据
            return {
                aid:'',//文章ID
                pdonate:true,//打开赞赏控制,
                detailObj:{},//返回详情数据
                haslogin:false,//是否已经登录
                userId:'',//用户id
            }
        },
        methods: { //事件处理器
            showInitDate:function(date,full){//年月日的编辑
                // console.log(detailObj.create_time,date,full);
                return initDate(date,full);
            },
            getArticleDetail:function(){
                getArticle(this.aid).then((response)=>{
                    this.detailObj = response
                    const markdownIt = mavonEditor.getMarkdownIt()
                    let content = markdownIt.render(response.content)
                      // latex渲染
                      .replace(/\$([^$]+)\$/g, (match, p1) => {
                        try {
                          return katex.renderToString(p1, {
                            throwOnError: false,
                          });
                        } catch (error) {
                          return match;
                        }
                      })
                    
                    // 处理图片，确保被p标签包裹
                    content = content.replace(/<img([^>]*)>/g, (match, p1) => {
                      // 如果图片已经被p标签包裹，则不处理
                      if(match.match(/<p>.*<img.*<\/p>/)) {
                        return match;
                      }
                      return `<p style="text-align:center"><img${p1}></p>`;
                    });

                    // 处理blockquote中的加粗内容
                    content = content.replace(/<blockquote>([\s\S]*?)<\/blockquote>/g, (match, p1) => {
                      // 将blockquote中的**text**替换为<strong>text</strong>
                      let innerContent = p1.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
                      return `<blockquote>${innerContent}</blockquote>`;
                    });

                    this.detailObj.content = content;
                }).finally(()=>{
                  //  代码高亮
                    this.$nextTick(() => {
                      let preEl = document.querySelectorAll('pre');
                      preEl.forEach((element) => {
                        let codeEl = element.querySelector('code');
                        hljs.highlightElement(codeEl)
                      });
                    });
                })
            },
            routeChange:function(){
                var that = this;
                that.aid = that.$route.query.aid==undefined?1:parseInt(that.$route.query.aid);//获取传参的aid
                //判断用户是否存在
                if(localStorage.getItem('userInfo')){
                    that.haslogin = true;
                    that.userInfo = JSON.parse(localStorage.getItem('userInfo'));
                    that.userId = that.userInfo.userId;
                    // console.log(that.userInfo);
                }else{
                    that.haslogin = false;
                }
                //获取详情接口
                this.getArticleDetail()
                updateViewCount(that.aid)
            },
            getToc() {
              const container = this.$refs.articleHTML;
              if (container) {
                const headers = container.querySelectorAll('h1, h2, h3, h4, h5, h6');
                let toc = Array.from(headers).map(header => {
                  const id = header.id || header.textContent.trim().toLowerCase().replace(/\s+/g, '-');
                  header.id = id;
                  return { id, text: header.textContent , p:header.tagName};
                });
                console.log(headers)
                this.$emit('getToc', toc);
              }
            }
        },
        watch: {
             // 如果路由有变化，会再次执行该方法
             '$route':'routeChange',
              detailObj() {
                this.$nextTick(() => {
                  this.getToc();
                });
              }
         },
        components: { //定义组件

        },
        created() { //生命周期函数
              var that = this;

              this.routeChange();
        },

    }
</script>

<style lang="less">
@import '../../static/markdown_theme/dyzj-light.css';

.detailBox .article-content{
    font-size: 15px;
    white-space: normal;
    word-wrap: break-word;
    word-break: break-all;
    overflow-x: hidden;
}
.detailBox .article-content p{
    margin:10px 0;
    line-height:24px;
    word-wrap: break-word;
    word-break: break-all;
    overflow-x: hidden;
}
.detailBox .article-content pre{
    word-wrap: break-word;
    word-break: break-all;
    overflow-x: hidden;
}
.detailBox .article-content img{
    max-width: 100%!important;
    height: auto!important;
    overflow-x: hidden;
}
.detailBox .article-content a{
    color:#df2050!important;
}
.detailBox .article-content a:hover{
    text-decoration: underline;
    color: #f00!important;
}
.detailBox .article-content i{
    font-style: italic;
}
.detailBox .article-content strong{
    font-weight: bold;
}
.detailBox .article-content ul{
    list-style-type: disc!important;
    list-style: disc!important;
    padding-left: 40px!important;
    li{
        list-style-type: disc!important;
        list-style: disc!important;
    }
}
.detailBox .article-content h1, .detailBox .article-content h2, .detailBox .article-content h3{
    font-size: 200%;
    font-weight: bold;
}
 .detailBox .article-content h4, .detailBox .article-content h5, .detailBox .article-content h6{
    font-size: 150%;
    font-weight: bold;
}
.detailBox .viewdetail{
    margin:10px 0 ;
    line-height: 24px;
    text-align: center;
}
/*分享图标*/
.dshareBox {
    margin-top:40px;
    position: relative;
}
.dshareBox a{
    position: relative;
    display: inline-block;
    width: 32px;
    height: 32px;
    font-size: 18px;
    border-radius: 50%;
    line-height: 32px;
    text-align: center;
    vertical-align: middle;
    margin: 4px;
    background: #fff;
    transition: background 0.6s ease-out;
}
.dshareBox .ds-weibo{
    border: 1px solid #ff763b;
    color: #ff763b;
}
.dshareBox .ds-weibo:hover{
    color: #fff;
    background: #ff763b;
}
.dshareBox .ds-qq{
    color: #56b6e7;
    border: 1px solid #56b6e7;
}
.dshareBox .ds-qq:hover{
    color: #fff;
    background: #56b6e7;
}
.dshareBox .ds-wechat{
    color: #7bc549;
    border: 1px solid #7bc549;
}
.dshareBox .ds-wechat:hover{
    color: #fff;
    background: #7bc549;
}
.dshareBox .ds-wechat:hover .wechatShare{
    opacity: 1;
    visibility: visible;
}
.detailBox .bdshare-button-style0-32 a{
    float:none;
    background-image: none;
    text-indent: inherit;
}
/*点赞 收藏*/
.dlikeColBox{
    display: inline-block;
    float:right;
}
.dlikeBox,.dcollectBox{
    display: inline-block;
    padding:0 10px;
    height:35px;
    color: #e26d6d;
    line-height: 35px;
    border-radius: 35px;
    border: 1px solid #e26d6d;
    cursor: pointer;
}

/*赞赏*/
.donate-word{
    margin:40px 0;
    text-align: center;
}
.donate-word span{
    display: inline-block;
    width:80px;
    height:34px;
    line-height: 34px;
    color:#fff;
    background: #e26d6d;
    margin:0 auto;
    border-radius: 4px;
    cursor: pointer;
}
.donate-body{
    display: none;
}
.donate-body-show{
    display: block;
}
.donate-item{
    text-align: right;
}
.donate-item:last-child{
    text-align: left;
}
.donate-item img{
    width:100%;
    display: block;
    height:auto;
}
.donate-item div{
    display: inline-block;
    width: 150px;
    padding: 0 6px;
    box-sizing: border-box;
    text-align: center;
}
.donate-item div span{
    display: inline-block;
    width:100%;
    margin: 10px 0;
    text-align: center;
}
.donate-body .donate-item:first-of-type div{
    color:#44b549;
}
.donate-body .donate-item:nth-of-type(2) div{
    color:#00a0e9;
}

.bd_weixin_popup{
    position: fixed!important;
}

/* 移除原有的markdown-body样式,使用主题样式 */
.markdown-body {
  font-family: var(--body-font);
  color: var(--body-color);
  background-color: var(--bg-color);
  line-height: 1.6;
  word-wrap: break-word;
  padding: 45px;
  font-size: 16px;
}

.markdown-body pre,
.markdown-body code {
  font-family: var(--code-font);
  background-color: var(--codeblockbg-color);
  color: var(--codeblockfont-color);
}

.markdown-body pre {
  padding: 16px;
  overflow: auto;
  border-radius: 3px;
}

.markdown-body code {
  padding: 2px 4px;
  border-radius: 3px;
}

.markdown-body blockquote {
  padding: 0 1em;
  color: var(--blockquoteicon-color);
  border-left: 0.25em solid var(--blockquoteicon-color);
  background-color: var(--blockquotebg-color);
}

.markdown-body blockquote strong {
  font-weight: 600;
  color: inherit;
}

.markdown-body blockquote p {
  margin: 0.5em 0;
  line-height: 1.6;
}

.markdown-body table {
  border-collapse: collapse;
  width: 100%;
  margin: 15px 0;
}

.markdown-body table th {
  background-color: var(--tablethbg-color);
}

.markdown-body table th,
.markdown-body table td {
  border: 1px solid var(--tableborder-color);
  padding: 6px 13px;
}

.markdown-body table tr:nth-child(2n) {
  background-color: var(--tableevenrow-color);
}

.markdown-body a {
  color: var(--atext-color);
  text-decoration: none;
  border-bottom: 1px solid var(--abottom-color);
}

.markdown-body a:hover {
  color: var(--ahover-color);
  text-decoration: none;
}

.markdown-body img {
  max-width: 100%;
  margin: 2px 0;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

.markdown-body h1,
.markdown-body h2,
.markdown-body h3,
.markdown-body h4,
.markdown-body h5,
.markdown-body h6 {
  margin-top: 24px;
  margin-bottom: 16px;
  font-weight: 600;
  line-height: 1.25;
  color: var(--body-color);
}


.markdown-body ul,
.markdown-body ol {
  padding-left: 2em;
  margin-top: 0;
  margin-bottom: 16px;
}

.markdown-body ul li::marker,
.markdown-body ol li::marker {
  color: var(--ulol-color);
}

</style>
