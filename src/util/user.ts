
import {ref,Ref} from "vue"
interface user{
    username:string,//博客名称
    userImg:string,//用户头像
    LodingImgUrl:string,//过渡头像
    IndexBack:string,//主页默认背景色
    IndexBackImg:string,//主页背景图片
    GitHubUrl:string,//GITURL
    gitURL:string,
    drawURL:string,
    drawtilte:string
    //....
}
// 请求信息
export const music:Ref<user>=ref({
    username:"轻笑Chuckle",//博客名称
    userImg:"https://www.keaitupian.cn/cjpic/frombd/2/253/1676065055/2828606542.jpg",//用户头像
    LodingImgUrl:"",//过渡头像
    IndexBack:"",//主页默认背景色
    drawURL:"https://img.shetu66.com/2023/11/14/1699962327144215.png",
    IndexBackImg:"",//主页背景图片
    GitHubUrl:"",//GITURL
    gitURL:"string,",
    drawtilte:"漫天倾尘 风中轻笑"
})

// 