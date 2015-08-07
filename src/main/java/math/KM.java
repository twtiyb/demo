package math;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class KM {

    public static final int INF = 1000000;

    private int row, col, size;

    private static int[][] edge;

    private int[] flag;

    private int[] hQuan;

    private int[] vQuan;

    private boolean[] hToken;

    private boolean[] vToken;

    public KM(int[][] pic) {
        edge = pic;
        row = edge.length;
        col = edge[0].length;
        hToken = new boolean[row];
        vToken = new boolean[col];
        hQuan = new int[row];
        vQuan = new int[col];
        size = row > col ? col : row;
        if (row == size) {
            flag = new int[col];
        } else {
            flag = new int[row];
        }
        init();
    }

    private void init() {
        for (int i = 0; i < flag.length; i++) {
            flag[i] = -1;
        }
        for (int i = 0; i < hToken.length; i++) {
            hToken[i] = false;
        }
        for (int i = 0; i < vToken.length; i++) {
            vToken[i] = false;
        }
        if (row == size) {
            for (int i = 0; i < vQuan.length; i++) {
                vQuan[i] = 0;
            }
            for (int i = 0; i < hQuan.length; i++) {
                hQuan[i] = -INF;
                for (int j = 0; j < vQuan.length; j++) {
                    hQuan[i] = max(hQuan[i], edge[i][j]);
                }
            }
        } else {
            for (int i = 0; i < hQuan.length; i++) {
                hQuan[i] = 0;
            }
            for (int i = 0; i < vQuan.length; i++) {
                vQuan[i] = -INF;
                for (int j = 0; j < hQuan.length; j++) {
                    vQuan[i] = max(vQuan[i], edge[j][i]);
                }
            }
        }
    }

    public boolean km() {
        int[][] map = new int[row][col];
        if (row == size) {
            int dmin = INF;
            do {
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        if (hQuan[i] + vQuan[j] == edge[i][j])
                            map[i][j] = 1;
                        else
                            map[i][j] = 0;
                    }
                }
                if (hasPerfectMatch(map))
                    return true;

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        if (hToken[i] && !vToken[j]) {
                            dmin = min(dmin, hQuan[i] + vQuan[j] - edge[i][j]);
                        }
                    }
                }
                if (dmin != INF && dmin > 0) {
                    for (int i = 0; i < row; i++) {
                        if (hToken[i])
                            hQuan[i] -= dmin;
                    }
                    for (int i = 0; i < col; i++) {
                        if (vToken[i])
                            vQuan[i] += dmin;
                    }
                }
            } while (dmin != INF && dmin > 0);
            return false;
        } else {
            int dmin = INF;
            do {
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        if (hQuan[i] + vQuan[j] == edge[i][j])
                            map[i][j] = 1;
                        else
                            map[i][j] = 0;
                    }
                }
                if (hasPerfectMatch(map))
                    return true;

                for (int i = 0; i < col; i++) {
                    for (int j = 0; j < row; j++) {
                        if (vToken[i] && !hToken[j]) {
                            dmin = min(dmin, hQuan[i] + vQuan[j] - edge[i][j]);
                        }
                    }
                }
                if (dmin != INF && dmin > 0) {
                    for (int i = 0; i < row; i++) {
                        if (hToken[i])
                            hQuan[i] += dmin;
                    }
                    for (int i = 0; i < col; i++) {
                        if (vToken[i])
                            vQuan[i] -= dmin;
                    }
                }
            } while (dmin != INF && dmin > 0);
            return false;
        }
    }

    /**
     * judge whether the map has a perfect match.
     *
     * @param map indicates whether each two points have been connected.
     * @return true if there is a perfect match in the map, or false.
     */
    public boolean hasPerfectMatch(int[][] map) {
        int i, j;
        if (row == size) {
            for (i = 0; i < flag.length; i++)
                flag[i] = -1;

            for (i = 0; i < size; i++) {

                for (j = 0; j < hToken.length; j++)
                    hToken[j] = false;
                for (j = 0; j < vToken.length; j++)
                    vToken[j] = false;
                if (!findAugumentPath(i, map/* ,token */))
                    break;
            }
            if (i < row)
                return false;
            return true;
        } else {
            for (i = 0; i < flag.length; i++)
                flag[i] = -1;

            for (i = 0; i < size; i++) {

                for (j = 0; j < hToken.length; j++)
                    hToken[j] = false;
                for (j = 0; j < vToken.length; j++)
                    vToken[j] = false;
                if (!findAugumentPath(i, map))
                    break;
            }
            if (i < row)
                return false;
            return true;
        }
    }

    /**
     * judge whether there is a augument path at the <code>pos</code> point in
     * <code>map</code>
     *
     * @param pos the order of the special point in the map
     * @param map the map's maltrix
     * @return true if there is a augumemt path at the <code>pos</code> point in
     * the <code>map</code
     */
    public boolean findAugumentPath(int pos, int[][] map) {
        if (row == size) {
            hToken[pos] = true;
            for (int i = 0; i < col; i++) {
                if (map[pos][i] == 1 && vToken[i] == false) {
                    vToken[i] = true;

                    if (flag[i] == -1 || findAugumentPath(flag[i], map)) {
                        flag[i] = pos;
                        return true;
                    }
                }
            }
            return false;
        } else {
            vToken[pos] = true;
            for (int i = 0; i < row; i++) {
                if (map[i][pos] == 1 && hToken[i] == false) {
                    hToken[i] = true;

                    if (flag[i] == -1 || findAugumentPath(flag[i], map)) {
                        flag[i] = pos;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * compute the max number of two
     *
     * @param i one digit used to compare with another one
     * @param j the other digit used to compare with the first one
     * @return the bigger one number of this two
     */
    public int max(int i, int j) {
        return i > j ? i : j;
    }

    public int min(int i, int j) {
        return i > j ? j : i;
    }

    public static void main(String[] args) {
        Double ll = 1.2;
        Double ss = 1.2;
        if (ll == ss) {
            System.out.println("dddd");
        }

        //关于时间的设置
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("修改前:" + cal.getTime());
//    	System.out.println("修改前:"+simpleDateFormat.format(cal.getTime()));
        cal.set(Calendar.MINUTE, 44);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 55);
//    	System.out.println("修改后:"+simpleDateFormat.format(cal.getTime()));
        System.out.println("修改后:" + cal.getTime());

        //关于Map.get().isEmpty的查看
        Map<String, String> map = new HashMap<String, String>();
        System.out.print(map.get(1));
    }


    private int name;
    private int value;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}