
package lab4_sia;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Владимир
 */
public class PSetTest {
    
    public PSetTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        System.out.println("Готово!");
    }

    /**
     * Test of add method, of class PSet.
     */
    @Test
    public void testAdd() {
        System.out.println("Тестирование добавление элемента в множество...");
        int item = 1;
        PSet instance = new PSet();
        
        boolean expResult = true;
        boolean result = instance.add(item);
        assertEquals("Добавление нового элемента провалилось!", expResult, result);
        
        expResult = false;
        result = instance.add(item);
        assertEquals("Добавление существующего элемента провалилось!", expResult, result);
        
        expResult = true;
        instance.add(2);
        result = instance.add(3);
        assertEquals("Добавление c малым вращением провалилось!", expResult, result);
        
        instance.add(5);
        instance.add(6);
        result = instance.add(7);
        assertEquals("Другое добавление c малым вращением провалилось!", expResult, result);
        
        instance.remove(7);
        result = instance.add(4);
        assertEquals("Добавление c большим вращением провалилось!", expResult, result);
    }

    /**
     * Test of remove method, of class PSet.
     */
    @Test
    public void testRemove() {
        System.out.println("Тестирование удаления элемента из множества...");
        int item = 1;
        PSet instance = new PSet();
        
        instance.add(item);
        boolean expResult = true;
        boolean result = instance.remove(item);
        assertEquals("Удаление существующего элемента провалилось!", expResult, result);
        
        expResult = false;
        result = instance.remove(item);
        assertEquals("Удаление отсутствующего элемента провалилось!", expResult, result);
    }

    /**
     * Test of contains method, of class PSet.
     */
    @Test
    public void testContains() {
        System.out.println("Тестирование поиска элемента в множестве...");
        int item = 1;
        PSet instance = new PSet();
        
        instance.add(item);
        boolean expResult = true;
        boolean result = instance.contains(item);
        assertEquals("Поиск существующего элемента провалился!", expResult, result);
        
        instance.remove(item);
        expResult = false;
        result = instance.contains(item);
        assertEquals("Поиск отсутствующего элемента провалился!", expResult, result);
    }

    /**
     * Test of size method, of class PSet.
     */
    @Test
    public void testSize() {
        System.out.println("Тестирование подсчёта мощности множества...");
        PSet instance = new PSet();
        
        int expResult = 0;
        int result = instance.size();
        assertEquals("Проверка пустого множества провалилась!", expResult, result);
        
        instance.add(1); // добавляем 1 элемент в множество
        expResult = 1;
        result = instance.size();
        assertEquals("Проверка непустого множества провалилась!", expResult, result);
    }

    /**
     * Test of getDotScript method, of class PSet.
     */
    @Test
    public void testGetDotScript() {
        System.out.println("Тестирование генерации dot-инструкций...");
        PSet instance = new PSet();
        
        instance.add(2);
        String expResult = "digraph G {" + System.lineSeparator() + "\tnode[shape=circle];";
        expResult += System.lineSeparator() + "\t\"2\";" + System.lineSeparator() + "}";
        String result = instance.getDotScript();
        assertEquals("Проверка множества с 1 элементом провалилась.", expResult, result);
        
        instance.add(1);
        instance.add(3);
        expResult = expResult.substring(0, expResult.length()-1); // убираем последний символ '}'
        expResult += "\t\"2\" -> \"1\";" + System.lineSeparator();
        expResult += "\t\"1\";" + System.lineSeparator();
        expResult += "\t\"2\" -> \"3\";" + System.lineSeparator();
        expResult += "\t\"3\";" + System.lineSeparator() + "}";
        result = instance.getDotScript();
        assertEquals("Проверка множества с 3 элементами провалилась.", expResult, result);
    }
}