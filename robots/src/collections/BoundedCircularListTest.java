package collections;

import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BoundedCircularListTest {

    @Test
    void testAddAndSizeLimit() {
        BoundedCircularList<String> list = new BoundedCircularList<>(3);
        list.add("A"); list.add("B"); list.add("C");
        assertEquals(3, list.size(), "Размер должен быть 3 после добавления 3 элементов");

        list.add("D"); // должно вытеснить "A"

        assertEquals(3, list.size(), "Размер должен остаться 3 после переполнения");
        assertEquals("B", list.get(0), "Первый элемент должен быть 'B' (старый 'A' вытеснен)");
        assertEquals("D", list.get(2), "Последний элемент должен быть 'D'");
    }

    @Test
    void testGetByIndex() {
        BoundedCircularList<Integer> list = new BoundedCircularList<>(5);
        for (int i = 0; i < 10; i++) list.add(i); // Добавляем 0..9

        assertEquals(5, list.size(), "Размер должен быть ограничен 5");
        assertEquals(5, list.get(0), "Самый старый из сохранённых должен быть 5");
        assertEquals(9, list.get(4), "Самый новый должен быть 9");
    }

    @Test
    void testSubList() {
        BoundedCircularList<String> list = new BoundedCircularList<>(5);
        list.add("1"); list.add("2"); list.add("3"); list.add("4");

        List<String> sub = list.subList(1, 3); // [1, 3) = элементы с индексами 1 и 2

        assertEquals(2, sub.size(), "Размер под списка должен быть 2");
        assertEquals("2", sub.get(0), "Первый элемент под списка должен быть '2'");
        assertEquals("3", sub.get(1), "Второй элемент под списка должен быть '3'");
    }

    @Test
    void testIteratorConsistencyDuringModification() {
        BoundedCircularList<Integer> list = new BoundedCircularList<>(5);
        list.add(1); list.add(2); list.add(3);

        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext(), "Итератор должен иметь следующий элемент");
        assertEquals(1, it.next(), "Первый элемент итератора должен быть 1");

        // Добавляем данные параллельно итерации — итератор НЕ должен упасть
        list.add(4); list.add(5); list.add(6);

        // Итератор работает со снимком, поэтому продолжает видеть старые данные
        assertTrue(it.hasNext(), "Итератор должен иметь следующий элемент");
        assertEquals(2, it.next(), "Второй элемент итератора должен быть 2");
        assertEquals(3, it.next(), "Третий элемент итератора должен быть 3");
        assertFalse(it.hasNext(), "Итератор должен закончиться после 3 элементов снимка");
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        BoundedCircularList<Integer> list = new BoundedCircularList<>(100);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 1000; i++) list.add(i);
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                // Просто итерируем, чтобы проверить, что нет ConcurrentModificationException
                list.iterator().forEachRemaining(x -> {});
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        assertEquals(100, list.size(), "Размер должен остаться 100 после многопоточного теста");
    }
}