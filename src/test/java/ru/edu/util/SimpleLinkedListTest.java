package ru.edu.util;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.edu.model.Box;

import java.util.List;
import java.util.ListIterator;

public class SimpleLinkedListTest {

    @Test
    public void testSize() {
        SimpleLinkedList<Box> boxes = new SimpleLinkedList<>();
        boxes.add(getBox1());
        boxes.add(getBox2());
        Assert.assertEquals(boxes.size(), 2);
    }

    @Test
    public void testIsEmpty() {
        SimpleLinkedList<Box> boxes = new SimpleLinkedList<>();
        Assert.assertTrue(boxes.isEmpty());
    }

    @Test
    public void testContains() {
        List<Box> referenceList = getReferenceList();
        Assert.assertTrue(referenceList.contains(getBox1()));
        Assert.assertTrue(referenceList.contains(getBox2()));
    }

    @Test
    public void testToArray() {
        Object[] array = getReferenceList().toArray();
        Assert.assertEquals(array.length, 2);
        Assert.assertEquals(array[0], getBox1());
        Assert.assertEquals(array[1], getBox2());
    }

    @Test
    public void testAdd() {
        List<Box> referenceList = getReferenceList();
        referenceList.add(getBox3());
        Assert.assertEquals(referenceList.size(), 3);
        Assert.assertEquals(referenceList.get(0), getBox1());
        Assert.assertEquals(referenceList.get(1), getBox2());
        Assert.assertEquals(referenceList.get(2), getBox3());
    }

    @Test
    public void testRemove() {
        List<Box> referenceList = getReferenceList();
        referenceList.remove(getBox2());
        Assert.assertEquals(referenceList.size(), 1);
        Assert.assertEquals(referenceList.get(0), getBox1());
        referenceList.remove(0);
        Assert.assertEquals(referenceList.size(), 0);
        Assert.assertTrue(referenceList.isEmpty());
    }

    @Test
    public void testAddAll() {
        List<Box> referenceList = new SimpleLinkedList<>();
        referenceList.add(getBox3());
        referenceList.addAll(List.of(getBox1(), getBox2()));
        Assert.assertEquals(referenceList.size(), 3);
        Assert.assertEquals(referenceList.get(0), getBox3());
        Assert.assertEquals(referenceList.get(1), getBox1());
        Assert.assertEquals(referenceList.get(2), getBox2());
    }

    @Test
    public void testClear() {
        List<Box> referenceList = getReferenceList();
        referenceList.clear();
        Assert.assertEquals(referenceList.size(), 0);
        Assert.assertTrue(referenceList.isEmpty());
    }

    @Test
    public void testGet() {
        List<Box> referenceList = getReferenceList();
        Assert.assertEquals(referenceList.size(), 2);
        Assert.assertEquals(referenceList.get(0), getBox1());
        Assert.assertEquals(referenceList.get(1), getBox2());
    }

    @Test
    public void testSet() {
        List<Box> referenceList = getReferenceList();
        referenceList.set(0, getBox3());
        Assert.assertEquals(referenceList.size(), 2);
        Assert.assertEquals(referenceList.get(0), getBox3());
        Assert.assertEquals(referenceList.get(1), getBox2());
    }

    @Test
    public void testIndexOf() {
        List<Box> referenceList = getReferenceList();
        Assert.assertEquals(referenceList.indexOf(getBox1()), 0);
        Assert.assertEquals(referenceList.indexOf(getBox2()), 1);
        Assert.assertEquals(referenceList.indexOf(getBox3()), -1);
    }

    @Test
    public void lastIndexOf() {
        List<Box> referenceList = getReferenceList();
        referenceList.add(getBox1());
        Assert.assertEquals(referenceList.lastIndexOf(getBox1()), 2);
    }

    @Test
    public void listIterator() {
        List<Box> referenceList = getReferenceList();

        ListIterator<Box> iterator = (ListIterator<Box>) referenceList.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertFalse(iterator.hasPrevious());

        Assert.assertEquals(iterator.next(), getBox1());
        Assert.assertTrue(iterator.hasPrevious());
        Assert.assertTrue(iterator.hasNext());

        Assert.assertEquals(iterator.next(), getBox2());
        Assert.assertTrue(iterator.hasPrevious());
        Assert.assertFalse(iterator.hasNext());

        Assert.assertEquals(iterator.previous(), getBox2());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.hasPrevious());

        Assert.assertEquals(iterator.previous(), getBox1());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertFalse(iterator.hasPrevious());
    }

    @Test
    public void testToString() {
        List<Box> referenceList = getReferenceList();
        Assert.assertEquals(referenceList.toString(),
                "SimpleLinkedList{Box{width=1, height=1, depth=1}, Box{width=2, height=2, depth=2}}");
    }

    @Test
    public void testHash() {
        List<Box> boxes1 = List.of(getBox1(), getBox2());
        List<Box> boxes2 = getReferenceList();
        Assert.assertEquals(boxes1.hashCode(), boxes2.hashCode());
    }

    @Test
    public void testEqual() {
        List<Box> boxes1 = List.of(getBox1(), getBox2());
        List<Box> boxes2 = getReferenceList();
        Assert.assertTrue(boxes2.equals(boxes1));
    }

    private Box getBox1() {
        return Box.getSingleBox();
    }

    private Box getBox2() {
        return Box.Builder.builder().withHeight(2).withWidth(2).withDepth(2).build();
    }

    private Box getBox3() {
        return Box.Builder.builder().withHeight(3).withWidth(3).withDepth(3).build();
    }

    private List<Box> getReferenceList() {
        SimpleLinkedList<Box> result = new SimpleLinkedList<>();
        result.add(getBox1());
        result.add(getBox2());
        return result;
    }

}