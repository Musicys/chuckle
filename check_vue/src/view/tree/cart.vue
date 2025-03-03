<template>


    <div class="pr_list_two">
        <div> <img v-lazy="'https://tse2-mm.cn.bing.net/th/id/OIP-C.NcBmhZg6-PBSrbbo2SVssgAAAA?rs=1&pid=ImgDetMain'"
                alt=""></div>


        <div class="pr-list-tops">

            <div>
                <div><span style="font-size: 1.2em; color: #49B1F5;">music</span> <span>2024-12-4</span> </div>

            </div>



            <div><span style="color: #49B1F5;">@music:</span>真帅</div>

        </div>

    </div>

</template>

<script setup lang="ts">


</script>

<style lang="scss" scoped>
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