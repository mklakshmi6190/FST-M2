package activities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class Activity1 {
    static  ArrayList<String> list;
    @BeforeEach
    public void setup() {
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");
    }

    @Test
    public void insertTest(){

    assertEquals(2,list.size(),"Wrong Size");
    list.add("gamma");
    assertEquals(3,list.size(),"Wrong Size");
    assertEquals("alpha", list.get(0), "Wrong Element");
    assertEquals("beta", list.get(1), "Wrong Element");
    assertEquals("gamma", list.get(2), "Wrong Element");

    }
    @Test
    public void replaceTest(){
        list.set(1, "delta");
        assertEquals(2, list.size(), "Wrong size");
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("delta", list.get(1), "Wrong element");
    }
}
