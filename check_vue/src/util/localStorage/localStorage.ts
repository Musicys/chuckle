interface localhost {
   //用户信息
   APlOSTUSERXRSEQR: string;
}

const localconfig: localhost = {
   APlOSTUSERXRSEQR: `APlOSTUSERXRSEQR`
};

const readLost: (stirng) => any[] = tilte => {
   const value = localStorage.getItem(`${tilte}`);

   return JSON.parse(value);
};

const updateLost: (string, any) => void = (tilte, arr: any) => {
   if (typeof arr == 'string') {
      localStorage.setItem(`${tilte}`, arr);
   } else {
      localStorage.setItem(`${tilte}`, JSON.stringify(arr));
   }
};

const deletelost: (stirng) => void = tilte => {
   localStorage.removeItem(`${tilte}`);
};

//读
export const aireadlost = () => {
   return readLost(localconfig.APlOSTUSERXRSEQR);
};

//存

export const aiupdatelost = arr => {
   updateLost(localconfig.APlOSTUSERXRSEQR, arr);
};

//删\
export const aideletelost = () => {
   deletelost(localconfig.APlOSTUSERXRSEQR);
};
