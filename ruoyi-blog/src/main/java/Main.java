import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] in = new int[30];//入度
        int[] out = new int[30];//出度
        List<Integer>[] graph = new ArrayList[30];
        for (int i = 0; i < m; i++) {
            String[] split = sc.next().split("<");
            int son = split[0].charAt(0)- 'A' + 1;
            int father = split[1].charAt(0) - 'A' + 1;
            graph[father].add(son);
            in[son]++;
            out[father]++;

        }
        Queue<Integer> q = new LinkedList<>();
        //查出入度为0的开始遍历
        for (int i = 1; i <= n; i++) {
            if(in[i] == 0){
                q.add(i);
            }
        }
        while (!q.isEmpty()){
            //弹出一个入度为0的点，然后遍历它的所有子节点，子节点的入度减一，如果入度减一后为0，则加入队列
            Integer poll = q.poll();
            in[poll]--;//入度减一

        }


    }
}
