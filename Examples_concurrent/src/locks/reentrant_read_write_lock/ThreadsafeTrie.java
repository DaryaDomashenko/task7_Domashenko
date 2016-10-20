package locks.reentrant_read_write_lock;

/**Префиксное дерево*/

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;

public class ThreadsafeTrie {
    ReadWriteLock rwlLock = new ReentrantReadWriteLock();

    static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<Character, TrieNode>();
        boolean leaf;
    }

    TrieNode root = new TrieNode();

    public void put(String s) {
        TrieNode v = root;

        rwlLock.writeLock().lock();
        {
            for (char ch : s.toLowerCase().toCharArray()) {
                if (!v.children.containsKey(ch)) {
                    v.children.put(ch, new TrieNode());
                }
                v = v.children.get(ch);
            }
            v.leaf = true;
        }
        rwlLock.writeLock().unlock();
    }

    public boolean find(String s) {
        TrieNode v = root;
        boolean result = true;

        rwlLock.readLock().lock();
        {
            for (char ch : s.toLowerCase().toCharArray()) {
                if (!v.children.containsKey(ch)) {
                    result = false;
                    break;
                } else {
                    v = v.children.get(ch);
                }
            }
        }
        rwlLock.readLock().unlock();
        return result;
    }

    static Map<Integer,String> levelSpacesMap = new ConcurrentHashMap<Integer,String>();

    static String getSpace(int level) {
        String result = levelSpacesMap.get(level);
        if (result == null) {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<level; i++) {
                sb.append(" ");
            }
            result = sb.toString();
            levelSpacesMap.put(level,result);
        }
        return result;
    }

    public void printSorted() {
        rwlLock.readLock().lock();
        {
            printSorted2(root,0);
        }
        rwlLock.readLock().unlock();
    }

    private void printSorted2(TrieNode node, int level) {
        for (Character ch : node.children.keySet()) {
            System.out.println(getSpace(level)+ch);
            printSorted2(node.children.get(ch), level+1);
        }
        if (node.leaf) {
            System.out.println();
        }
    }

    // Usage example
    public static void main(String[] args) {
        ThreadsafeTrie trie = new ThreadsafeTrie();
        trie.put("hello");
        trie.put("house");
        trie.put("hell");
        trie.put("world");
        System.out.println(trie.find("hello"));
        trie.printSorted();
    }
}