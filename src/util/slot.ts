import { ref,Ref } from 'vue'
export const drawer:Ref<boolean> = ref(true)
export const handleClose:Function=():void=>{
  console.log("执行");
  
    drawer.value=!drawer.value
}