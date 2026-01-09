import { ElMessageBox } from 'element-plus';
import type { Action } from 'element-plus';

export const open = str => {
   ElMessageBox.alert(str, {
      // if you want to disable its autofocus
      // autofocus: false,
      confirmButtonText: '确定',
      callback: (action: Action) => {}
   });
};
