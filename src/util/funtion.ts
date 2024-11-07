import {router} from "@/router/index";
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
const home:Function=(obj={})=>{go("/home",obj)}
const arg:Function=(obj={})=>{go("/arg",obj)}
const tree:Function=(obj={})=>{go("/tree",obj)}
const muisc:Function=(obj={})=>{go("/muisc",obj)}
const mine:Function=(obj={})=>{go("/mine",obj)}
export default {home,arg,tree,mine,muisc}

