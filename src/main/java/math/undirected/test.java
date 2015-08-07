package math.undirected;

import java.util.Stack;

public class test {

    public static void Gr(Integer[][] point, Stack<Integer> pathStack) {
        if (pathStack.size() == point.length) {
            printPath(pathStack, point);
        } else {
            Integer[] arry = point[pathStack.peek()];
            for (int i = 0; i < arry.length; i++) {
                if (point[pathStack.peek()][i] != 0 && !pathStack.contains(i)) {
                    Stack<Integer> copy = (Stack<Integer>) pathStack.clone();
                    copy.push(i);
                    Gr(point, copy);
                }
            }
        }
    }

    public static void printPath(Stack<Integer> pathStack, Integer[][] point) {
        String[] pointStr = new String[]{"A", "B", "C", "D", "E"};
        int total = 0;
        for (int i = 0; i < pathStack.size(); i++) {
            Integer next = pathStack.get(i);
            if (i != pathStack.size() - 1) {
                total += point[next][pathStack.get(i + 1)];
            } else {
                total += point[0][pathStack.get(pathStack.size() - 1)];
            }
            System.out.print(pointStr[next] + "-->");
        }
        System.out.println(total);
    }

    public static void main(String[] args) {
        Stack<Integer> pathStack = new Stack<Integer>();
        Integer[][] point = new Integer[5][5];
        // A
        point[0][0] = 0;
        point[0][1] = 3;
        point[0][2] = 5;
        point[0][3] = 4;
        point[0][4] = 4;


        // B
        point[1][0] = 3;
        point[1][1] = 0;
        point[1][2] = 4;
        point[1][3] = 5;
        point[1][4] = 4;

        // C
        point[2][0] = 5;
        point[2][1] = 4;
        point[2][2] = 0;
        point[2][3] = 3;
        point[2][4] = 4;

        // D
        point[3][0] = 4;
        point[3][1] = 5;
        point[3][2] = 3;
        point[3][3] = 0;
        point[3][4] = 4;

        // E
        point[4][0] = 4;
        point[4][1] = 5;
        point[4][2] = 3;
        point[4][3] = 0;
        point[4][4] = 4;

        pathStack.push(0);
        Gr(point, pathStack);
    }

}