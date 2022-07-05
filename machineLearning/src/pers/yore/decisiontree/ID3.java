package pers.yore.decisiontree;

import java.io.*;
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

            while((str = in.readLine()) != null) {  // readline - read a line of text
                // i == 0,因为第一行读取的是label
                if(i == 0) {
                    String[] strs = str.split(",");
                    for (String s : strs) {
                        label.add(s);
                        if (Debug.TEST_PRINT) System.out.print(s + " ");
                    }
                    i++;
                    if(Debug.TEST_PRINT) System.out.println();
                    continue;
                }

                // 读取参数
                String[] strs = str.split(",");
                ArrayList<String> line = new ArrayList<>();
                for (String s : strs) {
                    line.add(s);
                    if (Debug.TEST_PRINT) System.out.print(s + " ");
                }
                data.add(line);
                if(Debug.TEST_PRINT) System.out.println();
                i++;
            }
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 获取测试数据集
    public void getTestData(String path) throws FileNotFoundException {
        String str;

        try{
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader in = new BufferedReader(isr);

            while((str = in.readLine()) != null) {
                String[] strs = str.split(",");
                ArrayList<String> line = new ArrayList<>();
                for(String s : strs) {
                    line.add(s);
                    if(Debug.TEST_PRINT) System.out.print(s + " ");
                }
                if(Debug.TEST_PRINT) System.out.println();
                test.add(line);
            }
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void init(ArrayList<ArrayList<String>> data) {
        // 得到种类数
        sum.add(data.get(0).get(data.get(0).size() - 1));
        for(int i = 0; i < data.size(); i++) {
            if(sum.contains(data.get(i).get(data.get(0).size() - 1)) == false) {
                sum.add(data.get(i).get(data.get(0).size() - 1));
            }
        }
        System.out.println();
    }


    /**
     * @description: 计算条件熵并返回信息增益值
     *
     * @param:
     * @return:
     */
    public double condition(int a, ArrayList<ArrayList<String>> data) {
        ArrayList<String> all = new ArrayList<>();
        double c = 0.0;

        all.add(data.get(0).get(a));
        // 得到属性种类
//        for(int i = 0; i < data.size(); i++) {
//
//        }
    }


    /**
     * @description: 计算信息增益最大属性
     *
     * @param:
     * @return:
     */
    public int Gain(ArrayList<ArrayList<String>> data) {
        ArrayList<Double> num = new ArrayList<>();

        // 保存各信息增益值
        for(int i = 0; i < data.get(0).size() - 1; i++) {
            num.add(condition(i, data));
        }
    }


    /**
     * @description: 构建决策树
     *
     * @param:
     * @return:
     */
    public TreeNode creatTree(ArrayList<ArrayList<String>> data) {
        int index = Gain(data);
    }


    /**
     * @description: 输出决策时
     *
     * @param:
     * @return:
     */
    public void print(ArrayList<ArrayList<String>> data) {
        System.out.println("构建的决策树如下：");
        TreeNode node = null;
        node = creatTree(data); // 类
//        put(node);  // 递归调用
    }


    public static void main(String[] args) throws Exception {
        String train = "./decision_tree_id3/data/trainData.txt";    // 训练数据集
        String test = "./decision_tree_id3/data/testData.txt";      // 测试数据集
        String result = "./decision_tree_id3/data/result.txt";      // 预测结果集

        ID3 id3 = new ID3(train, test); // 初始化数据


    }


}
