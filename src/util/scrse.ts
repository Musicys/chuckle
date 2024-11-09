
import {ref,Ref} from "vue"



// 定义类型

export const scrollProgress = ref<number>(0);



// Istab


export const IsScroll=ref(false)

export const handleScroll=(e:any)=>{

      IsScroll.value= e.target.scrollTop==0?false:true;
      const scrollTop = e.target.scrollTop;
      const scrollHeight = e.target.scrollHeight - e.target.clientHeight;
    
      // 计算滚动百分比
      scrollProgress.value = Math.floor((scrollTop / scrollHeight) * 100)|0;
      console.log(scrollProgress.value);
      
     
      
      
    

}

