
import {ref,Ref} from "vue"


// Istab


export const IsScroll=ref(false)

export const handleScroll=(e:any)=>{

      IsScroll.value= e.target.scrollTop==0?false:true;
      
      console.log(IsScroll.value);
      

}

