
import { ref, Ref } from "vue"
interface user {
    username: string,//博客名称
    userImg: string,//用户头像
    LodingImgUrl: string,//过渡头像
    IndexBack: string,//主页默认背景色
    IndexBackImg: string,//主页背景图片
    GitHubUrl: string,//GITURL
    gitURL: string,
    drawURL: string,
    drawtilte: string,
    QqUrl: string,
    Bilbilurl: string,
    CsdnUrl: string,
    StartTilte: string
    //....
}




// 请求信息
export const music: Ref<user> = ref({
    username: "轻笑Chuckle",//博客名称
    userImg: "https://www.keaitupian.cn/cjpic/frombd/2/253/1676065055/2828606542.jpg",//用户头像
    LodingImgUrl: "",//过渡头像
    IndexBack: "",//主页默认背景色
    drawURL: "https://tse2-mm.cn.bing.net/th/id/OIP-C.OX22ZodoOmhwmPQ3-31sSgAAAA?rs=1&pid=ImgDetMain",
    IndexBackImg: "",//主页背景图片
    GitHubUrl: "",//GITURL
    gitURL: "string,",
    drawtilte: "漫天倾尘 风中轻笑",
    QqUrl: "https://space.bilibili.com/283189629?spm_id_from=333.788.0.0",
    Bilbilurl: "https://space.bilibili.com/283189629?spm_id_from=333.788.0.0",
    CsdnUrl: "https://blog.csdn.net/Idmusi?spm=1000.2115.3001.5343",
    StartTilte: "我走不开制动懂你发个额额"

})

// 