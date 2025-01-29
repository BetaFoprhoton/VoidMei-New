package org.matrixsukhoi.voidmei.prog;

import org.matrixsukhoi.voidmei.VoidMeiMainKt;

import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
* 读取properties文件 
* @author Qutr 
* 
*/ 
public class Config {
    private Properties properties;
    private InputStreamReader inputFile; 
    private FileOutputStream outputFile;

    /**
     * 初始化Configuration类 
     */ 
    public Config() {
        properties = new Properties();
    }

    /**
     * 初始化Configuration类 
     * @param filePath 要读取的配置文件的路径+名称 
     */ 
    public Config(String filePath) {
        properties = new Properties();

        try {
        	InputStream in = new FileInputStream(Objects.requireNonNull(ResourceKt.getResource(filePath)));
            inputFile = new InputStreamReader(in, StandardCharsets.UTF_8);
            properties.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            VoidMeiMainKt.getLogger().error("Reading property file failed: File path error or file does not exist. Details: {}", ex.getMessage());
        } catch (IOException ex) {
            VoidMeiMainKt.getLogger().error("Loading file failed, details: {}", ex.getMessage());
        } catch (Exception ex) {
            VoidMeiMainKt.getLogger().error("An unknown error occurred, details: {}", ex.getMessage());
        }
    }//end ReadConfigInfo(...)

    /**
     * 重载函数，得到key的值 
     * @param key 取得其值的键 
     * @return key的值 
     */ 
    public String getValue(String key) {
        if (properties.containsKey(key)) {
            //得到某一属性的值
            return properties.getProperty(key);
        } else return "";
    }//end getValue(...)

    /**
     * 重载函数，得到key的值 
     * @param fileName properties文件的路径+文件名 
     * @param key 取得其值的键 
     * @return key的值 
     */ 
    public String getValue(String fileName, String key) {
        try {
            String value = ""; 
            InputStream in = new FileInputStream(fileName);
            inputFile = new InputStreamReader(in, StandardCharsets.UTF_8);
            properties.load(inputFile);
            inputFile.close(); 
            if (properties.containsKey(key)) {
                value = properties.getProperty(key);
                return value; 
            } else
                return value; 
        } catch (FileNotFoundException e) { 
            VoidMeiMainKt.getLogger().error("File path error or file does not exist, details: {}", e.getMessage());
            return ""; 
        } catch (IOException e) {
            VoidMeiMainKt.getLogger().error("IO Exception occur, details: {}", e.getMessage());
            return ""; 
        } catch (Exception ex) {
            VoidMeiMainKt.getLogger().error("An unknown error occurred, details: {}", ex.getMessage());
            return ""; 
        } 
    }//end getValue(...) 

    /**
     * 清除properties文件中所有的key和其值 
     */ 
    public void clear() {
        properties.clear();
    }//end clear(); 

    /**
     * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替， 
     * 当key不存在时，该key的值是value 
     * @param key 要存入的键 
     * @param value 要存入的值 
     */ 
    public void setValue(String key, String value) {
        properties.setProperty(key, value);
    }//end setValue(...) 

    /**
     * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。 
     * @param fileName 文件路径+文件名称 
     * @param description 对该文件的描述 
     */ 
    public void saveFile(String fileName, String description) {
        try { 
            outputFile = new FileOutputStream(fileName); 
            properties.store(outputFile, description);
            outputFile.close(); 
        } catch (FileNotFoundException e) {
            VoidMeiMainKt.getLogger().error("File path error or file does not exist, details: {}", e.getMessage());
        } catch (IOException e){
            VoidMeiMainKt.getLogger().error("IO Exception occur, details: {}", e.getMessage());
        } 
    }//end saveFile(...) 
}