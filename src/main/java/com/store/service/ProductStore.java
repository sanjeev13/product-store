package com.store.service;

import com.store.model.TrieNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductStore {

    @Autowired
    private CatalogueService catalogueService;

    private TrieNode rootNode;
    private boolean initialized;

    public ProductStore() {
        initialized = false;
    }

    private void initialize(){
        List<String> names = catalogueService.getAllProductNames();

        rootNode = new TrieNode();

        TrieNode root = rootNode;

        for(String str : names){
            char[] chars = str.toCharArray();
            for(char ch : chars){

                if(root.getChildren().containsKey(ch)){
                    root = root.getChildren().get(ch);
                }else{
                    TrieNode newNode = new TrieNode();
                    root.getChildren().put(ch,newNode);
                    root = newNode;
                }
            }
            root.setCount(root.getCount()+1);
            root = rootNode;
        }
        initialized = true;
    }

    public List<String> search(String keyword){
        if(! initialized ){
            initialize();
        }

        List<String> matchingProducts = new ArrayList<>();
        TrieNode root = rootNode;

        char[] chars = keyword.toCharArray();

        for(int i=0;i<chars.length && root != null;i++){
            char ch = chars[i];
            if(root.getChildren().containsKey(ch)) {
                root = root.getChildren().get(ch);
            }else {
                root = null;
            }
        }

        // root now is the node which represents last character from input keyword in Trie
        if(root != null){
            Map<String,Integer> allNames = new HashMap<>();
            allStringsFromNode(root,new StringBuilder(),allNames);

            // map of count --> string to sort in required order i.e. most repeated first
            Map<Integer,List<String>> reverseMap = new TreeMap<>(Collections.reverseOrder());

            for(String str : allNames.keySet()){
                int count = allNames.get(str);
                String name = keyword + str;
                if(reverseMap.containsKey(count)){
                    addDuplicates(reverseMap.get(count),name,count);
                }else{
                    List<String> names = new ArrayList<>();
                    addDuplicates(names,name,count);

                    reverseMap.put(count,names);
                }
            }

            for(Integer key : reverseMap.keySet()){
                matchingProducts.addAll(reverseMap.get(key));
            }
        }
        return matchingProducts;
    }

    private void addDuplicates(List<String> strings, String name, int count) {
        for(int i=0;i<count;i++){
            strings.add(name);
        }
    }

    /**
     * sets all string that starts from the current node to the map
     * @param root
     * @param str
     * @param map
     */
    private void allStringsFromNode(TrieNode root, StringBuilder str, Map<String,Integer> map) {
        if(root.getCount() > 0){
            map.put(str.toString(),root.getCount());
        }

        for(char key : root.getChildren().keySet()){
            str.append(key);
            allStringsFromNode(root.getChildren().get(key),str,map);
            str.deleteCharAt(str.length()-1);
        }
    }
}
