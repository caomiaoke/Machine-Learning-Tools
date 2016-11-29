package com.github.milo.math;

import org.junit.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

//import static junit.framework.TestCase.assertEquals;

/**
 * Created by milo on 16-11-28.
 */
public class MathTest {
    public MathTest(){}

    @BeforeClass
    public static void setUpClass() throws Exception{}
    @AfterClass
    public static void tearDownClass() throws Exception{}
    @Before
    public void setUp(){}
    @After
    public void tearDown(){}

    @Test
    public void testMin_args(){
        System.out.print("min ");
        int a = -1;
        int b = 0;
        int c = 1;
        int expResult = -1;
        int result = Math.min(a,b,c);
        assertEquals(expResult,result);
    }

    /**
     * 测试返回列向量均值函数
     */
    @Test
    public void testColMean(){
        System.out.println("colMean");
        double[][] A = {
                {0.7220180, 0.07121225, 0.6881997},
                {-0.2648886, -0.89044952, 0.3700456},
                {-0.6391588, 0.44947578, 0.6240573}
        };

        double[] r = {-0.06067647, -0.12325383, 0.56076753};

        double[] result = Math.colMean(A);
        for (int i = 0; i < r.length; i++) {
            assertEquals(result[i], r[i], 1E-7);
        }

    }
}
