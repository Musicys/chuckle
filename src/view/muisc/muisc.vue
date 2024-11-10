<template>
    <div class="page">
        <div class="cd-muisc" v-if="Ispc"  >
            <div class="cd-muisc-top" >
                <div class="muisc-userheader"></div>
                <div class="cd-toptext">Hello <br>
                <strong>设计灵感</strong>
                </div>
            </div>
            <div class="cd-muisc-mian" >
                <ul>
                    发现音乐
                    <li>首页</li>
                    <li>专辑</li>
                    <li>歌手</li>
                    <li>博客</li>
                </ul>
            </div>
            <div class="cd-muisc-footer" >
                <ul>
                    我的音乐
                    <li>本地音乐</li>
                    <li>我喜欢</li>
                    <li>创建歌单</li>
                </ul>
            </div>
        </div>
        <div class="zt-muisc">
            <div class="zt-music-top">
                <div class="zt-but">
                    <div></div>
                    <div></div>
                   
                </div>
                <div class="divleft">
                    <input type="text" class="search" placeholder="   猜你喜欢...">
                    <button class="zt-buttom" type="button" value=" ">搜索</button>
                </div>
            </div>
            <div class="zt-music-main">
                <div class="music-t">
                </div>
                <span class="mym">ones的专属好歌 ></span>
            </div>
            <div class="zt-music-footer">
               <div class="muisc-footer-fl">
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
               </div>
               <div class="muisc-footer-day">
               </div>
            </div>
        </div>
        <div class="more-muisc" v-if="Ispc" >
            <div class="more-muisc-top">
               <div class="vip">开通会员</div>
            </div>
            <div class="more-muisc-mid">
                <div class="muisc-song-zzr">
                    <h4>音乐制作人</h4>
                    <span class="more">查看更多</span>
                </div>
                <div class="more-muisc-singer">
                    <div class="more-singer">
                        <div class="singer-head"></div>
                    </div>
                    <div class="more-singer-xx">
                        <div class="singer-naem">
                            <h4>Jacob Jones</h4>
                            <span>148.4万粉丝.254万播放量</span>
                        </div>
                    </div>
                    
                </div>
                <div class="more-muisc-singer">
                    <div class="more-singer">
                        <div class="singer-head"></div>
                    </div>
                    <div class="more-singer-xx">
                        <div class="singer-naem">
                            <h4>Jacob Jones</h4>
                            <span>148.4万粉丝.254万播放量</span>
                        </div>
                    </div>
                    
                </div>
            </div>
            <div class="more-muisc-footer"></div>
        </div>
    </div>
</template>

<script setup lang="ts">
import {music_data  } from "@/util/music";
import {Ispc} from "@/util/windows.ts"
</script>

<style lang="scss" scoped>
.page{
    height: 80vh;
   
    background: var(--cart-bgmuisc-bagk);
    color: var(--cart-muisc-color);
    
    border-radius: 20px;
    display: flex;
     overflow: hidden;   
     justify-content:space-around;
     align-items: center;
     &>div:nth-child(2){
            margin: 1em;
     }
}
ul{
    position: relative;
    top: 20px;
    left: 15px;
}
li{
    list-style: none;
    margin: 15px;
    color: #817e7e;
    cursor: pointer;
}
.cd-muisc{
    display: inline-block;
    height: 70vh;
    flex: 1;
   
  
    border-radius: 20px;
}
.cd-muisc-top{
    height: 15vh;
    width: 100%;
    /* background-color: red; */
}
.muisc-userheader{
    width: 40%;
    height: 50%;
    background-color: #fa8072;
    border-radius: 10px;
    position: relative;
    left: 15px;
    top: 10px;
}
.cd-toptext{
    margin-top: 5px;
    position: relative;
    left: 20px;
    top: 10px;}
.cd-muisc-mian{
    width: 100%;
    height: 25vh;
    /* background-color: blue; */
}
.cd-muisc-footer{
    width: 100%;
    height: 25vh;
    /* background-color: green; */
}
.zt-muisc{
    display: inline-block;
    flex: 3;
    height: 75vh;
  
 
    border-radius: 20px;
}
.zt-music-top{
    width: 100%;
    height: 50px;



    /* background-color: red; */
}
.zt-but{
    width: 15%;
    height: 100%;
    display: inline-block;
    
    
}
.zt-but div{
    display: inline-block;
    width: 20%;
    height: 30%;
    background-color: #817e7e;
    border-radius: 50% ;
    margin: 5px;
    position: relative;
    top: 10px;
    left: 10px;
}
.divleft{
    display: inline-block;
    width: 85%;
}
.search{
    width: 80%;
    height: 25px;
    border-radius: 30px;
    border: 0px;
    outline:none;
    cursor:pointer;
    position: relative;
    top: 5px;
    left: 10px;

}
.zt-buttom{
    width: 50px;
    height: 30px;
    border-radius: 30px;
    cursor:pointer;
    outline:none;
    border: none;
    position: relative;
    left: -40px;
    top: 5px;
    background-color: #fa8072;
}
.zt-music-main{
    width: 100%;
    height: 300px;
    /* background-color: red; */
}
.music-t{
    width: 90%;
    height: 80%;
    background-color: #fa8072;
    border-radius: 20px;
    position: relative;
    top: 5px;
    left: 25px;
    // box-shadow: 7px 3px 10px #fa8072db;
}
.mym{
    position: relative;
    top: 15px;
    left: 32px;
}
.zt-music-footer{
    width: 100%;
    height: 200px;
    /* background-color: red; */
}
.muisc-footer-fl{
    display: inline-block;
    width: 60%;
    height: 100%;
    /* background-color: red; */
}
.zt-music-footer .muisc-footer-fl div{
    display: inline-flex;
    width: 20%;
    height: 70px;
    background-color: #fa8072;
    border-radius: 5px;
    margin-left: 4% ;
    margin-top: 4%;
}
.muisc-footer-day{
    display: inline-block;
    width: 38%;
    height: 100%;
    background-color: #fa8072;
    border-radius: 20px;
}
.more-muisc{
    display: inline-block;
    flex: 1;
    height: 75vh;
    /* background-color: rgb(255, 255, 255); */
   
    border-radius: 20px;
}
.more-muisc-top{
    width: 100%;
    height: 40px;
    /* background-color: red; */
}
.more-muisc-top .vip{
    width: 70%;
    height: 23px;

    margin: 0 auto;
    border-radius: 10px;
    position: relative;
    top: 10px;
    font-size: 90%;
    text-align: center;
    color: #ff0000;
}
.more-muisc-mid{
    width: 100%;
    height: 180px;
    /* background-color: red; */
}
.more{
    display: block;
    font-size: 80%;
    color: #c3b2b2;
    text-align: right;
    cursor: pointer;
}
.more-muisc-singer{
    width: 100%;
    height: 80px;
    display: flex;
    /* background-color: rgb(206, 148, 148); */
}
.more-singer{
    display: inline-block;
    width: 30%;
    height: 100%;
    /* background-color: #fff; */
}
.more-singer-xx{
    display: inline-block;
    width: 60%;
    height: 100%;
    /* background-color: #1eaec7; */
    flex: 1;
    overflow: hidden;
  
}
.singer-head{
    width: 50px;
    height: 50px;
    background-color: #fa8072;
    border-radius: 10px;
    
 
}
.singer-naem{
   
}
.singer-naem h4{
    margin-top: 10px;
    margin-bottom: 10px;
    
}
.singer-naem span{
 
   color: #b0a7a7;
   font-size: 80%;
}
.more-muisc-footer{
    margin-top: 10px;
    width: 100%;
    height: 320px;

    border-radius: 20px;
}
</style>