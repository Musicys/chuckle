<template>

    <div></div>
    <hr>
    <div class="tilte">
        <span>评论 </span><el-icon>
            <Comment />
        </el-icon>
    </div>
    <div>
        <CommentBox />



    </div>
    <div class="tilte">
        <span>3评论 </span>
    </div>

    <div class="pr-list" v-for="i in 3">

        <span style="border: none;" class="pr_list_img">
            <img v-lazy="'https://tse2-mm.cn.bing.net/th/id/OIP-C.NcBmhZg6-PBSrbbo2SVssgAAAA?rs=1&pid=ImgDetMain'"
                alt="">
        </span>
        <div>
            <div class="pr-list-top">
                <div><span style="font-size: 1.2em; color: #49B1F5;">music</span> <span>2024-12-4</span></div>
                <div><el-icon>
                        <Promotion />
                    </el-icon></div>
            </div>
            <div>真帅</div>
            <CommentBox />
            <div class="pr_list_two" v-for="i in 3">
                <div> <img
                        v-lazy="'https://tse2-mm.cn.bing.net/th/id/OIP-C.NcBmhZg6-PBSrbbo2SVssgAAAA?rs=1&pid=ImgDetMain'"
                        alt=""></div>


                <div class="pr-list-tops">

                    <div>
                        <div><span style="font-size: 1.2em; color: #49B1F5;">music</span> <span>2024-12-4</span> </div>
                        <div><el-icon>
                                <Promotion />
                            </el-icon></div>
                    </div>



                    <div><span style="color: #49B1F5;">@music:</span>真帅</div>
                    <CommentBox />
                </div>

            </div>



        </div>

    </div>



</template>

<script setup lang="ts">

import CommentBox from "./CommentBox.vue";
</script>

<style lang="scss" scoped>
.tilte {
    margin: 1em auto;
    display: flex;
    justify-content: start;
    align-items: center;
    font-size: 1.2em;

    &>span {
        margin: auto .5em;

    }


}

.pr-list {
    display: flex;

    .pr_list_img {
        display: inline-block;
        width: 50px;
        height: 100%;
        border: none;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 1em;

        &>img {
            width: 100%;
            border-radius: 50%;
            height: 50px;
            margin: auto .5em;
        }
    }


    &>div {
        width: 100%;
        display: flex;
        flex-direction: column;


        .pr-list-top {
            display: flex;
            justify-content: space-between;
            align-items: center;

            width: 100%;
            margin-bottom: 1em;
        }

        border-radius: 5px;
        padding: 1em .5em;
        background: none;
        box-shadow: .1px .1px 1px var(--bk-font-color);

    }

    margin-bottom: 1.2em;



}

.pr_list_two {
    margin: auto 1.2em;
    font-size: .9em;
    margin-top: 1em;
    display: flex;

    &>div {
        &>img {
            width: 40px;
            border-radius: 50%;
            height: 40px;
            margin: auto .5em;
        }
    }

    .pr-list-tops {
        width: 100%;
        display: flex;
        flex-direction: column;

        &>div:nth-child(1) {
            display: flex;

            justify-content: space-between;
            align-items: center;
            width: 100%;

        }

        &>div:nth-child(2) {

            margin-top: .5em;



        }
    }
}
</style>