//import java.util.HashMap;
//import java.util.Map;
//
///**
// * sudoku solver
// * @date 2018/1/13.
// */
//public class Solve {
//    private static final int N=3;
//
//    private int[][] nums;
//
//    private int[][] results;
//    public static void main(String[] args) {
//        int x[][]={
//                {0,0,0,0,0,0,0,0,0,0},
//                {0,0,0,0,0,0,0,0,0,0},
//                {0,8,0,5,0,2,0,6,0,0},
//                {0,0,5,0,7,0,1,0,0,0},
//                {0,0,0,2,0,8,0,0,0,0},
//                {0,0,4,0,1,0,8,0,0,0},
//                {0,5,0,8,0,7,0,3,0,0},
//                {7,0,2,3,0,0,4,0,5,0},
//                {0,4,0,0,0,0,0,7,0,0},
//        };
//
//        int y[][]={
//                {0,2,0,0,0,9,0,1,0,0},
//                {5,0,6,0,0,0,3,0,9,0},
//                {0,8,0,5,0,2,0,6,0,0},
//                {0,0,5,0,7,0,1,0,0,0},
//                {0,0,0,2,0,8,0,0,0,0},
//                {0,0,4,0,1,0,8,0,0,0},
//                {0,5,0,8,0,7,0,3,0,0},
//                {7,0,2,3,0,0,4,0,5,0},
//                {0,4,0,0,0,0,0,7,0,0},
//        };
//        Solve solve = new Solve();
//        solve.setNums(y);
//        solve.function(0,0);
//
//    }
//
//    public void function(int r, int c) {
//        if (r>=nums.length) {
//            showOut();
//            return;
//        }
//        if (c==0&&(r==nums.length/N||r==nums.length/N*2||r==nums.length)) {
//            if (!checkedbox(nums,r)) {
//                return;
//            };
//
//        }
//        if (c>=nums.length) {
//            function( r+1, 0);
//            return;
//        }
//
//        if (nums[r][c]==0) {
//            for (int i = 1; i <= nums.length; i++) {
//                if (checked(nums,r,c,i)) {
//                    nums[r][c]=i;
//                    function( r, c+1);
//                    nums[r][c]=0;
//                }
//            }
//        }else{
//            function(r, c+1);
//        }
//        return;
//    }
//    private boolean checkedbox(int[][] x, int r) {
//        for (int k = 0; k < x.length; k+=x.length/N) {
//            Map<Integer, Integer> map=new HashMap<>();
//            for (int i = r-N; i < r; i++) {
//                for (int j = k; j < k+x.length/N; j++) {
//                    if (map.containsKey(x[i][j])) {
//                        return false;
//                    }
//                    map.put(x[i][j], 1);
//                }
//            }
//
//        }
//        return true;
//    }
//
//    private boolean checked(int[][] x, int r, int c, int i) {
//        for (int j = 0; j < x.length; j++) {
//            if (x[j][c]==i) {
//                return false;
//            }
//            if (x[r][j]==i) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void showOut() {
//        results = new int[nums[0].length][nums.length];
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = 0; j < nums.length; j++) {
//                System.out.print(nums[i][j]+" ");
//                results[i][j] = nums[i][j];
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    public int[][] result(){
//        return results;
//    }
//
//    public int[][] getNums() {
//        return nums;
//    }
//
//    public void setNums(int[][] nums) {
//        this.nums = nums;
//    }
//}
