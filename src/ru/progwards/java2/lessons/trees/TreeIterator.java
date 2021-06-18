package ru.progwards.java2.lessons.trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*Для обычного BinaryTree из примера в лекциях сделать итератор,
   который позволяет в обычном for получить прямой обход дерева.
   В самом дереве дополнительную информацию для этого хранить нельзя,
   все хранить только в итераторе. В !!!! BinaryTree !!!! добавить метод public TreeIterator<K,V> getIterator()*/

public class TreeIterator<K extends Comparable<K>, V> implements Iterator<BinaryTree.TreeLeaf> {
    BinaryTree<K, V> tree;
    Deque<BinaryTree.TreeLeaf> parents; // стэк родителей
    int parentNow;
    BinaryTree.TreeLeaf next;


    public TreeIterator(BinaryTree<K, V> tree) {
        this.tree = tree;
        next = tree.root;
        parentNow = -1;
        parents = new ArrayDeque<>();
    }



    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public BinaryTree.TreeLeaf next() {
        if (next == null) throw new NoSuchElementException("BinaryTree TreeIterator");
        BinaryTree.TreeLeaf result = next;

        if (result.left != null) {
            parents.offerLast(result);
            next = result.left;
        } else if (result.right != null) {
            parents.offerLast(result);
            next = result.right;
        } else {
            stepBack(); // возвращаемся обратно
        }
        return result;
    }

    private void stepBack() {
        BinaryTree.TreeLeaf parent = parents.peekLast();
        while (parent != null && (parent.right == next || parent.right==null)) {
            next = parents.pollLast();
            parent = parents.peekLast();
        }
        next = parent == null ? null : parent.right;
    }

    public static void main(String[] args) throws TreeException {
        BinaryTree<Integer, String> bt = new BinaryTree();
        bt.add(99,"7");
        bt.add(4,"0");
        bt.add(5,"2");
        bt.add(9,"9");
        bt.add(8,"4");
        bt.add(6,"9");
        bt.add(7,"2");
        bt.add(56,"1");
        bt.add(50,"54");
        bt.add(3,"30");
        bt.add(10,"24");
        bt.add(60,"12");
        Iterator i =  bt.getIterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}

