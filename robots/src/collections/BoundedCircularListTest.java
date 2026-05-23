package collections;

import java.util.Iterator;
import java.util.List;

public class BoundedCircularListTest {

    public static void main(String[] args) {
        System.out.println("Запуск тестов BoundedCircularList...");

        try {
            testAddAndSizeLimit();
            testGetByIndex();
            testSubList();
            testIteratorConsistency();
            System.out.println("\n✅ ВСЕ ТЕСТЫ ПРОЙДЕНЫ!");
        } catch (AssertionError e) {
            System.err.println("\n❌ ОШИБКА ТЕСТА: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Ожидалось: " + expected + ", но получено: " + actual);
        }
    }

    private static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Ожидалось true, но получено false");
        }
    }

    private static void testAddAndSizeLimit() {
        System.out.print("Тест ограничения размера... ");
        BoundedCircularList<String> list = new BoundedCircularList<>(3);
        list.add("A"); list.add("B"); list.add("C");
        assertEquals(3, list.size());
        list.add("D"); // должно вытеснить A
        assertEquals(3, list.size());
        assertEquals("B", list.get(0));
        assertEquals("D", list.get(2));
        System.out.println("OK");
    }

    private static void testGetByIndex() {
        System.out.print("Тест доступа по индексу... ");
        BoundedCircularList<Integer> list = new BoundedCircularList<>(5);
        for (int i = 0; i < 10; i++) list.add(i);
        assertEquals(5, list.size());
        assertEquals(5, list.get(0)); // самый старый из сохранённых
        assertEquals(9, list.get(4)); // самый новый
        System.out.println("OK");
    }

    private static void testSubList() {
        System.out.print("Тест subList... ");
        BoundedCircularList<String> list = new BoundedCircularList<>(5);
        list.add("1"); list.add("2"); list.add("3"); list.add("4");
        List<String> sub = list.subList(1, 3);
        assertEquals(2, sub.size());
        assertEquals("2", sub.get(0));
        assertEquals("3", sub.get(1));
        System.out.println("OK");
    }

    private static void testIteratorConsistency() {
        System.out.print("Тест итератора при изменении... ");
        BoundedCircularList<Integer> list = new BoundedCircularList<>(5);
        list.add(1); list.add(2); list.add(3);

        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(1, it.next());

        // Добавляем данные параллельно итерации
        list.add(4); list.add(5); list.add(6);

        // Итератор работает со снимком, не падает и не видит новые элементы
        assertTrue(it.hasNext());
        assertEquals(2, it.next());
        assertEquals(3, it.next());
        if (it.hasNext()) throw new AssertionError("Итератор должен закончиться");
        System.out.println("OK");
    }
}