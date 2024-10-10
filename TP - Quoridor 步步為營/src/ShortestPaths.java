import java.util.*;

public class ShortestPaths {

    static class Point {
        int x, y, dist;
        
        public Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public static List<List<Point>> findAllShortestPaths(int[][] map, int startX, int startY, int endX, int endY) {
        List<List<Point>> result = new ArrayList<>();
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY, 0));

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int x = current.x;
            int y = current.y;
            int dist = current.dist;

            if (x == endX && y == endY) {
                // Found a shortest path to the destination
                List<Point> path = new ArrayList<>();
                while (x != startX || y != startY) {
                    path.add(new Point(x, y, dist));
                    int nextX = x - dx[prev[x][y]];
                    int nextY = y - dy[prev[x][y]];
                    x = nextX;
                    y = nextY;
                    dist--;
                }
                path.add(new Point(startX, startY, 0));
                Collections.reverse(path);
                result.add(path);
            }

            for (int i = 0; i < 4; i++) {
                int nextX = x + dx[i];
                int nextY = y + dy[i];

                if (isValid(nextX, nextY, rows, cols) && !visited[nextX][nextY] && map[nextX][nextY] != 1) {
                    queue.add(new Point(nextX, nextY, dist + 1));
                    visited[nextX][nextY] = true;
                    prev[nextX][nextY] = i;
                }
            }
        }

        return result;
    }

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int[][] prev;

    static boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public static void main(String[] args) {
        // 省略了地图的定义
        int[][] map = {
        		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {3, 3, 3, 1, 3, 1, 3, 1, 3, 1, 3}};

        int startX = 2;
        int startY = 2;
        int endX = 10;
        int endY = 0;

        prev = new int[map.length][map[0].length];

        List<List<Point>> paths = findAllShortestPaths(map, startX, startY, endX, endY);

        // Print the result
        for (List<Point> path : paths) {
            for (Point point : path) {
                System.out.print("(" + point.x + "," + point.y + ") ");
            }
            System.out.println();
        }
    }
}
