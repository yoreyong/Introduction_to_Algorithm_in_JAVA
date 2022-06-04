package com.yore.decisiontree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @className: ID3
 * @description: TODO 类描述
 * @author: YORE
 * @date: 2022/6/2
 * @reference: https://blog.csdn.net/qq_38773180/article/details/79188510
 **/
public class ID3 {

    // Debug define class
    public interface Debug {
        boolean DEBUG_PRINT = false;
        boolean TEST_PRINT = true;
    }

    public class TreeNode {
        private String name;

        public TreeNode(String str) {
            name = str;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        ArrayList<String> label = new ArrayList<>();    // 和种子节点间的边标签
        ArrayList<TreeNode> node = new ArrayList<>();   // 对应子节点
    }

    private ArrayList<String> label = new ArrayList<>();    // 特征标签
    private ArrayList<ArrayList<String>> data = new ArrayList<>();  // 数据集
    private ArrayList<ArrayList<String>> test = new ArrayList<>();  // 测试数据集
    private ArrayList<String> sum = new ArrayList<>();  // 分类种类器
    private String kind;

    public ID3(String path, String pathTest) throws FileNotFoundException {
        // 初始化训练数据并得到分类种数
        getData(path);

        // 获取测试数据集
        getTestData(pathTest);

        init(data);
    }

    // 获取训练数据集
    public void getData(String path) throws FileNotFoundException {
        String str;
        int i = 0;
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader in = new BufferedReader(isr);
            while((str = in.readLine()) != null) {
                if(i == 0) {
                    String[] strs = str.split(",");
                    for(int j = 0; j < strs.length; j++) {
                        label.add(strs[j]);
                        if(Debug.TEST_PRINT) System.out.print(strs[j] + " ");
                    }
                    i++;
                    if(Debug.TEST_PRINT) System.out.println();
                    continue;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 获取测试数据集
    public void getTestData(String path) throws FileNotFoundException {

    }

    public void init(ArrayList<ArrayList<String>> data) {

    }

    public static void main(String[] args) throws Exception {
        String train = "./decision_tree_id3/data/trainData.txt";
        String test = "./decision_tree_id3/data/testData.txt";

        ID3 id3 = new ID3(train, test);
    }


}
