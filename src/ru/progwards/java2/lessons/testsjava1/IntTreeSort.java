package ru.progwards.java2.lessons.testsjava1;

import java.util.Arrays;
import java.util.Random;

/**
        * This class implements a funky tree sort algorithm for sorting integers.
        *
        * @author Rodion "rodde" Efremov
        * @version 1.6 (Feb 21, 2016)
        */
public class IntTreeSort {

    private static final int CONSOLE_WIDTH = 80;
    private static final int LENGTH = 10;
    private static final int DISTINCT_INTS = 5000;

    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(int[] array, int fromIndex, int toIndex) {
        if (toIndex - fromIndex < 2) {
            return;
        }

        new IntTreeSort(array, fromIndex, toIndex).sort();
    }

    private final int[] array;
    private final int fromIndex;
    private final int toIndex;
    private final HashTableEntry[] table;
    private final int mask;
    private TreeNode root;

    private IntTreeSort(int[] array, int fromIndex, int toIndex) {
        this.array = array;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;

        int capacity = computeCapacity(toIndex - fromIndex);

        this.table = new HashTableEntry[capacity];
        this.mask = capacity - 1;
    }

    private static int computeCapacity(int length) {
        int ret = 1;

        while (ret < length) {
            ret <<= 1;
        }

        return ret;
    }

    private static final class TreeNode {
        int key;
        int count;
        int height;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        TreeNode(int key) {
            this.key = key;
            this.count = 1;
        }
    }

    private static final class HashTableEntry {
        int key;
        TreeNode treeNode;
        HashTableEntry nextEntry;

        HashTableEntry(int key, TreeNode treeNode, HashTableEntry nextEntry) {
            this.key = key;
            this.treeNode = treeNode;
            this.nextEntry = nextEntry;
        }
    }

    private static int height(TreeNode node) {
        return node == null ? -1 : node.height;
    }

    private int index(int element) {
        return element & mask;
    }

    private TreeNode findTreeNode(int element, int elementHash) {
        HashTableEntry entry = table[elementHash];

        while (entry != null && entry.treeNode.key != element) {
            entry = entry.nextEntry;
        }

        return entry == null ? null : entry.treeNode;
    }

    private void sort() {
        int initialKey = array[fromIndex];
        root = new TreeNode(initialKey);
        table[index(initialKey)] = new HashTableEntry(initialKey, root, null);

        for (int i = fromIndex + 1; i < toIndex; ++i) {
            int currentElement = array[i];
            int currentElementHash = index(currentElement);

            TreeNode treeNode = findTreeNode(currentElement,
                    currentElementHash);

            if (treeNode != null) {
                treeNode.count++;
            } else {
                TreeNode newnode = add(currentElement);
                HashTableEntry newentry =
                        new HashTableEntry(currentElement,
                                newnode,
                                table[currentElementHash]);
                table[currentElementHash] = newentry;
            }
        }

        TreeNode node = minimum(root);
        int index = fromIndex;

        while (node != null) {
            int key = node.key;
            int count = node.count;

            for (int i = 0; i < count; ++i) {
                array[index++] = key;
            }

            node = successor(node);
        }
    }

    private TreeNode minimum(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    private TreeNode successor(TreeNode node) {
        if (node.right != null) {
            return minimum(node.right);
        }

        TreeNode parent = node.parent;

        while (parent != null && parent.right == node) {
            node = parent;
            parent = parent.parent;
        }

        return parent;
    }

    private TreeNode add(int key) {
        TreeNode parent = null;
        TreeNode node = root;

        while (node != null) {
            if (key < node.key) {
                parent = node;
                node = node.left;
            } else if (key > node.key) {
                parent = node;
                node = node.right;
            } else {
                break;
            }
        }

        TreeNode newnode = new TreeNode(key);

        if (key < parent.key) {
            parent.left = newnode;
        } else {
            parent.right = newnode;
        }

        newnode.parent = parent;
        fixAfterInsertion(parent);
        return newnode;
    }

    private void fixAfterInsertion(TreeNode node) {
        TreeNode parent = node.parent;
        TreeNode grandParent;
        TreeNode subTree;

        while (parent != null) {
            if (height(parent.left) == height(parent.right) + 2) {
                grandParent = parent.parent;

                if (height(parent.left.left) >= height(parent.left.right)) {
                    subTree = rightRotate(parent);
                } else {
                    subTree = leftRightRotate(parent);
                }

                if (grandParent == null) {
                    root = subTree;
                } else if (grandParent.left == parent) {
                    grandParent.left = subTree;
                } else {
                    grandParent.right = subTree;
                }

                if (grandParent != null) {
                    grandParent.height = Math.max(
                            height(grandParent.left),
                            height(grandParent.right)) + 1;
                }

                return;
            } else if (height(parent.right) == height(parent.left) + 2) {
                grandParent = parent.parent;

                if (height(parent.right.right) >= height(parent.right.left)) {
                    subTree = leftRotate(parent);
                } else {
                    subTree = rightLeftRotate(parent);
                }

                if (grandParent == null) {
                    root = subTree;
                } else if (grandParent.left == parent) {
                    grandParent.left = subTree;
                } else {
                    grandParent.right = subTree;
                }

                if (grandParent != null) {
                    grandParent.height =
                            Math.max(height(grandParent.left),
                                    height(grandParent.right)) + 1;
                }

                return;
            }

            parent.height = Math.max(height(parent.left),
                    height(parent.right)) + 1;
            parent = parent.parent;
        }
    }

    private TreeNode leftRotate(TreeNode node1) {
        TreeNode node2 = node1.right;
        node2.parent = node1.parent;
        node1.parent = node2;
        node1.right = node2.left;
        node2.left = node1;

        if (node1.right != null) {
            node1.right.parent = node1;
        }

        node1.height = Math.max(height(node1.left), height(node1.right)) + 1;
        node2.height = Math.max(height(node2.left), height(node2.right)) + 1;
        return node2;
    }

    private TreeNode rightRotate(TreeNode node1) {
        TreeNode node2 = node1.left;
        node2.parent = node1.parent;
        node1.parent = node2;
        node1.left = node2.right;
        node2.right = node1;

        if (node1.left != null) {
            node1.left.parent = node1;
        }

        node1.height = Math.max(height(node1.left), height(node1.right)) + 1;
        node2.height = Math.max(height(node2.left), height(node2.right)) + 1;
        return node2;
    }

    private TreeNode rightLeftRotate(TreeNode node1) {
        TreeNode node2 = node1.right;
        node1.right = rightRotate(node2);
        return leftRotate(node1);
    }

    private TreeNode leftRightRotate(TreeNode node1) {
        TreeNode node2 = node1.left;
        node1.left = leftRotate(node2);
        return rightRotate(node1);
    }

    public static String title(String text) {
        return title(text, '=');
    }

    private static String title(String text, char c) {
        StringBuilder sb = new StringBuilder();

        int left = (CONSOLE_WIDTH - 2 - text.length()) / 2;
        int right = CONSOLE_WIDTH - 2 - text.length() - left;

        for (int i = 0; i < left; ++i) {
            sb.append(c);
        }

        sb.append(' ').append(text).append(' ');

        for (int i = 0; i < right; ++i) {
            sb.append(c);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(title("Сортировка через бинарное дерево в строке и целых чисел"));
        long start=System.nanoTime();
        int[]a=new int[]{1000,999,888,777,897,678,99,55,44,88,99,33,33,66,44,11,-33,-77,-66,777,333,111,222,-222,-999,-10000,-555,-444};
       // long start=System.nanoTime();
        sort(a);
        long sorted=System.nanoTime()-start;
        System.out.println(Arrays.toString(a)+"Execution time: " + sorted);
    }
}


