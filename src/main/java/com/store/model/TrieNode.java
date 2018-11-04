package com.store.model;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character,TrieNode> children;
    private int count;

    public TrieNode() {
        this.children = new HashMap<>();
        this.count = 0;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, TrieNode> children) {
        this.children = children;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
