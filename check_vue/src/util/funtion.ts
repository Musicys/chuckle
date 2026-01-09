import { router } from '@/router/index';
interface RouteParams {
   query?: Record<any, string | number>;
   params?: Record<any, string | number>;
}
const go = (url: string, obj?: RouteParams): void => {
   router.push({
      path: url,
      query: obj?.query
   });
};

const go2 = (url: string, hash?: string): void => {
   router.push({
      path: url,
      hash: hash
   });
};
const home: Function = (obj = {}) => {
   go('/home', obj);
};
const arg: Function = (obj = {}) => {
   go('/arg', obj);
};
const tree: Function = (obj = {}) => {
   go('/tree', obj);
};
const muisc: Function = (obj = {}) => {
   go('/muisc', obj);
};
const mine: Function = (obj = {}) => {
   go('/mine', obj);
};
const start: Function = (obj = {}) => {
   go('/', obj);
};

const datail: Function = (obj = {}) => {
   console.log(obj);
   go('/datail', { query: { id: obj + '' } });
};

const desc: Function = (obj = {}) => {
   console.log(obj);
   go('/desc', { query: { id: obj + '' } });
};

const descml: Function = (str: string) => {
   console.log(str.toLowerCase());

   go2('/desc', '#' + str.toLowerCase());
};

export default { home, arg, tree, mine, muisc, start, desc, descml, datail };
