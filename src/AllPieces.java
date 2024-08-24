public class AllPieces {
    // T piece
    public static final int[][][] T_SHAPE = {
            {{0, 1, 0},                // 0 degrees
                {1, 1, 1}},
            {{1, 0},                   // 90 degrees
                    {1, 1},
                    {1, 0}},
            {{1, 1, 1},                // 180 degrees
                    {0, 1, 0}},
            {{0, 1},                   // 270 degrees
                    {1, 1},
                    {0, 1}}
    };

    public static final int[][][] Z_SHAPE = {
            // Can only be rotated two different ways, the other shape is the S_SHAPE

            {{1,1,0},{0,1,1}}  // 0 degrees
            ,{{0,1},{1,1}, {1,0}} // 90 degrees Alternate;
    };
    public static final int[][][] S_SHAPE = {
            {{0,1,1},{1,1,0}} // 0 degrees
            ,{{1,0},{1,1},{0,1}}
    };

    public static final int[][][] I_SHAPE = {
            // 0 degrees
            {
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            // 90 degrees
            {
                    {0, 0, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 1, 0}
            },
            // 180 degrees
            {
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            // 270 degrees
            {
                    {0, 0, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 1, 0}
            }
    };
    public static final int[][][] O_SHAPE = {
            // No rotate possible for this shape
            {{1,1},{1,1}},
            {{1,1},{1,1}},
            {{1,1},{1,1}},
            {{1,1},{1,1}},
    };

    public static final int[][][] L_SHAPE = {
            //// 0 degrees
            {{1,0},
             {1,0},
             {1,1}},
            //
            {{1,1,1},
             {1,0,0}},
            // 90 degrees

            //
            //180 degrees
            {{1,1},
             {0,1},
             {0,1}},
            //
            // 360 degrees
            {{0,0,1},
            {1,1,1}},
            //
    };
    public static final int[][][] J_SHAPE = {
            // 0 degrees
            {{0,1},
            {0,1},
            {1,1}},
            //

            // 90 degrees
            {{1,0,0},
             {1,1,1}},
            //

            // 180 degrees
            {{1,1},
             {1,0},
             {1,0}},
            //

            // 360 degrees
            {{1,1,1},
             {0,0,1}}
            //
    };
}
