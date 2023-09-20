package com.example.worksheetone;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    Color randomCol = new Color(1, 2, 3, 4);
    //    Color randomCol = new Color(1,2,3,4);
    HashMap<Integer, ArrayList<Integer>> map = new HashMap();
    HelloController hc = new HelloController();
    //int[] newDisjointArray;


    //before you run each test how you want your variables to appear
    //setting up an array with elements
    @BeforeEach
    void setUp() {
        //newDisjointArray[0] = -1;
        hc.newDisjointArray = new int[]{-1, 1, 2, 3, 4, 5, -1, 7, 8, 9, 10, -1};
        HelloController.Union(hc.newDisjointArray, 1, 2);
        HelloController.Union(hc.newDisjointArray, 1, 5);
        HelloController.Union(hc.newDisjointArray, 2, 3);
        HelloController.Union(hc.newDisjointArray, 3, 7);
        HelloController.Union(hc.newDisjointArray, 4, 5);
        HelloController.Union(hc.newDisjointArray, 4, 8);
        HelloController.Union(hc.newDisjointArray, 5, 9);
        HelloController.Union(hc.newDisjointArray, 8, 9);
        HelloController.Union(hc.newDisjointArray, 9, 10);

    }

    //this test method will check if the actual number will match the expected number
    @Test
    void checkTHatUnionWorks() {

        assertEquals(4, HelloController.find(hc.newDisjointArray, 1));
        assertNotEquals(4, hc.newDisjointArray[0]);
        assertEquals(4, HelloController.find(hc.newDisjointArray, 2));
        assertEquals(4, HelloController.find(hc.newDisjointArray, 3));
        assertEquals(4, HelloController.find(hc.newDisjointArray, 4));
        assertEquals(4, HelloController.find(hc.newDisjointArray, 5));
        assertEquals(4, HelloController.find(hc.newDisjointArray, 7));
        assertEquals(4, HelloController.find(hc.newDisjointArray, 8));
        assertEquals(4, HelloController.find(hc.newDisjointArray, 9));
        assertEquals(4, HelloController.find(hc.newDisjointArray, 10));
        //assertEquals(-1, HelloController.find(hc.newDisjointArray, -1));


    }
}
//    @Test
//    void checkThatMergeWorks(){
//        assertEquals(0.5, randomCol.getBlue());
//
//
//    }



