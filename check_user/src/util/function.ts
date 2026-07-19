import { router } from '@/router/index';

interface RouteParams {
   query?: Record<string, string | number>;
   params?: Record<string, string | number>;
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

const home = (obj: RouteParams = {}): void => {
   go('/home', obj);
};

const arg = (obj: RouteParams = {}): void => {
   go('/arg', obj);
};

const tree = (obj: RouteParams = {}): void => {
   go('/tree', obj);
};

const music = (obj: RouteParams = {}): void => {
   go('/muisc', obj);
};

const mine = (obj: RouteParams = {}): void => {
   go('/mine', obj);
};

const start = (obj: RouteParams = {}): void => {
   go('/', obj);
};

const detail = (id: string | number): void => {
   go('/datail', { query: { id: id + '' } });
};

const desc = (id: string | number): void => {
   go('/desc/' + id);
};

const descMl = (str: string): void => {
   go2('/desc', '#' + str.toLowerCase());
};

export default { home, arg, tree, mine, music, start, desc, descMl, detail };
