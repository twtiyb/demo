package math.undirected;

import java.util.Stack;

/**
 * 无向无权无环图<br>
 * 寻找起点到终点的所有路径
 */
public class GrfAllEdge {
    // 图的顶点总数
    private int total;
    // 各顶点基本信息
    private String[] nodes;
    // 图的邻接矩阵
    private int[][] matirx;

    public GrfAllEdge(int total, String[] nodes) {
        this.total = total;
        this.nodes = nodes;
        this.matirx = new int[total][total];
    }

    private void printStack(Stack<Integer> stack, int k) {
        for (Integer i : stack) {
            System.out.print(this.nodes[i] + ",");
        }
        System.out.print(this.nodes[k] + ",");
    }

    /**
     * 寻找起点到终点的所有路径
     *
     * @param underTop 紧挨着栈顶的下边的元素
     * @param goal     目标
     * @param stack
     */
    private void dfsStack(int underTop, int goal, Stack<Integer> stack) {
        // System.out.print("\n栈元素:");
        // this.printStack(stack);

        if (stack.isEmpty()) {
            return;
        }

        // 访问栈顶元素，但不弹出
        int k = stack.peek().intValue();
        // 紧挨着栈顶的下边的元素
        int uk = underTop;

        if (k == goal) {
            System.out.print("\n起点与终点不能相同");
            return;
        }

        // 对栈顶的邻接点依次递归调用，进行深度遍历
        for (int i = 0; i < this.total; i++) {
            // 有边，并且不在左上到右下的中心线上
            if (this.matirx[k][i] == 1 && k != i) {
                // 排除环路
                if (stack.contains(i)) {
                    // 由某顶点A，深度访问其邻接点B时，由于是无向图，所以存在B到A的路径，在环路中，我们要排除这种情况
                    // 严格的请，这种情况也是一个环
                    if (i != uk) {
                        System.out.print("\n有环:");
                        this.printStack(stack, i);
                    }
                    continue;
                }

                // 打印路径
                if (i == goal) {
                    System.out.print("\n路径:");
                    this.printStack(stack, i);
                    continue;
                }

                // 深度遍历
                stack.push(i);
                dfsStack(k, goal, stack);
            }
        }

        stack.pop();
    }

    private void printMatrix() {
        System.out.println("----------------- matrix -----------------");
        System.out.println("---0-1-2-3-4-5-6-7-8--");
        System.out.println("---A-B-C-D-E-F-G-H-I--");
        for (int i = 0; i < this.total; i++) {
            System.out.print(" " + this.nodes[i] + "|");
            for (int j = 0; j < this.total; j++) {
                System.out.print(this.matirx[i][j] + "-");
            }
            System.out.print("\n");
        }
        System.out.println("----------------- matrix -----------------");
    }

    // 设置[i][i]位置处的元素值为0，0表示图中的定点i未被访问，1表示图中的定点i已被访问
    private void resetVisited() {
        for (int i = 0; i < this.total; i++) {
            this.matirx[i][i] = 0;
        }
    }

    // 初始化图数据
    // 0---1---2---3---4---5---6---7---8---
    // A---B---C---D---E---F---G---H---I---
    private void initGrf() {
        // A-B, A-D, A-E
        this.matirx[0][1] = 1;
        this.matirx[1][0] = 1;
        this.matirx[0][3] = 1;
        this.matirx[3][0] = 1;
        this.matirx[0][4] = 1;
        this.matirx[4][0] = 1;
        // B-C
        this.matirx[1][2] = 1;
        this.matirx[2][1] = 1;
        // C-F
        this.matirx[2][5] = 1;
        this.matirx[5][2] = 1;
        // D-E, D-G
        this.matirx[3][4] = 1;
        this.matirx[4][3] = 1;
        this.matirx[3][6] = 1;
        this.matirx[6][3] = 1;
        // E-F, E-H
        this.matirx[4][5] = 1;
        this.matirx[5][4] = 1;
        this.matirx[4][7] = 1;
        this.matirx[7][4] = 1;
        // F-H, F-I
        this.matirx[5][7] = 1;
        this.matirx[7][5] = 1;
        this.matirx[5][8] = 1;
        this.matirx[8][5] = 1;
        // G-H
        this.matirx[6][7] = 1;
        this.matirx[7][6] = 1;
        // H-I
        this.matirx[7][8] = 1;
        this.matirx[8][7] = 1;
    }

    // 初始化图数据
    // 0---1---2---3---4---5---6---7---8---
    // A---B---C---D---E---F---G---H---I---
    private void initGrf2() {
        // A-B, A-D, A-E
        this.matirx[0][1] = 1;
        this.matirx[1][0] = 1;
        this.matirx[0][3] = 1;
        this.matirx[3][0] = 1;
        this.matirx[0][4] = 1;
        this.matirx[4][0] = 1;
        // B-C
        this.matirx[1][2] = 1;
        this.matirx[2][1] = 1;
        // C-F
        this.matirx[2][5] = 1;
        this.matirx[5][2] = 1;
        // D-E
        this.matirx[3][4] = 1;
        this.matirx[4][3] = 1;
        // E-F, E-H
        this.matirx[4][5] = 1;
        this.matirx[5][4] = 1;
        this.matirx[4][7] = 1;
        this.matirx[7][4] = 1;
        // F-H, F-I
        this.matirx[5][7] = 1;
        this.matirx[7][5] = 1;
        this.matirx[5][8] = 1;
        this.matirx[8][5] = 1;
        // G-H
        this.matirx[6][7] = 1;
        this.matirx[7][6] = 1;
        // H-I
        this.matirx[7][8] = 1;
        this.matirx[8][7] = 1;
    }

    // 初始化图数据
    // 0---1---2---3---4---5---6---7---8---
    // A---B---C---D---E---F---G---H---I---
    private void initGrf3() {
        // A-D, A-E
        this.matirx[0][3] = 1;
        this.matirx[3][0] = 1;
        this.matirx[0][4] = 1;
        this.matirx[4][0] = 1;
        // B-C
        this.matirx[1][2] = 1;
        this.matirx[2][1] = 1;
        // C-F
        this.matirx[2][5] = 1;
        this.matirx[5][2] = 1;
        // E-H, E-I
        this.matirx[4][7] = 1;
        this.matirx[7][4] = 1;
        this.matirx[4][8] = 1;
        this.matirx[8][4] = 1;
        // F-I
        this.matirx[5][8] = 1;
        this.matirx[8][5] = 1;
        // G-H
        this.matirx[6][7] = 1;
        this.matirx[7][6] = 1;
    }

    public static void main(String[] args) {
        String[] nodes = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        GrfAllEdge grf = new GrfAllEdge(9, nodes);
        grf.initGrf();
        grf.printMatrix();

        System.out.print("\n------ 寻找起点到终点的所有路径开始 ------");
        grf.resetVisited();
        int origin = 0;
        int goal = 8;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(origin);
        grf.dfsStack(-1, goal, stack);
        System.out.print("\n------ 寻找起点到终点的所有路径结束 ------");
    }

}