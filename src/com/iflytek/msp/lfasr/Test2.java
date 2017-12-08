package com.iflytek.msp.lfasr;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.iflytek.msp.po.json.JsonBean;
import com.iflytek.msp.po.json.Records;
import com.iflytek.msp.po.json.Results;
import com.iflytek.msp.po.jsonbean.Content;
import com.iflytek.msp.po.jsonbean.WordsResultList;
 
public class Test2 {
 
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://192.168.11.177:3306/record?useUnicode=true&characterEncoding=UTF-8";
 
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";
	
	
	
 
    public static void main(String[] args) {
    	
    	int a = 123/1000;
    	int times = (int)(new Date().getTime()/1000);
    	System.out.println(times);
    	System.exit(0);
    	Test2 test = new Test2();
    	test.test1();
//    	test.test();
    }
    
    public void test1(){
    	String str = "[{\"bg\":\"8970\",\"ed\":\"10830\",\"nc\":\"1.0\",\"onebest\":\"囔\",\"si\":\"0\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.6907\",\"wordBg\":\"3\",\"wordEd\":\"180\",\"wordsName\":\"囔\",\"wp\":\"n\"}]},{\"bg\":\"10840\",\"ed\":\"14420\",\"nc\":\"1.0\",\"onebest\":\"囡囡\",\"si\":\"1\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9990\",\"wordBg\":\"3\",\"wordEd\":\"352\",\"wordsName\":\"囡囡\",\"wp\":\"n\"}]},{\"bg\":\"14430\",\"ed\":\"18230\",\"nc\":\"1.0\",\"onebest\":\"囡。\",\"si\":\"2\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.7713\",\"wordBg\":\"368\",\"wordEd\":\"376\",\"wordsName\":\"囡\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"376\",\"wordEd\":\"376\",\"wordsName\":\"。\",\"wp\":\"p\"}]},{\"bg\":\"18240\",\"ed\":\"19050\",\"nc\":\"1.0\",\"onebest\":\"嗯\",\"si\":\"3\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9987\",\"wordBg\":\"22\",\"wordEd\":\"64\",\"wordsName\":\"嗯\",\"wp\":\"s\"}]},{\"bg\":\"19550\",\"ed\":\"24200\",\"nc\":\"1.0\",\"onebest\":\"囔\",\"si\":\"4\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.7998\",\"wordBg\":\"443\",\"wordEd\":\"453\",\"wordsName\":\"囔\",\"wp\":\"n\"}]},{\"bg\":\"24310\",\"ed\":\"27770\",\"nc\":\"1.0\",\"onebest\":\"囡\",\"si\":\"5\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.7650\",\"wordBg\":\"286\",\"wordEd\":\"295\",\"wordsName\":\"囡\",\"wp\":\"n\"}]},{\"bg\":\"29360\",\"ed\":\"33840\",\"nc\":\"1.0\",\"onebest\":\"当年弃香山北王\",\"si\":\"6\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"97\",\"wordEd\":\"128\",\"wordsName\":\"当年\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9714\",\"wordBg\":\"128\",\"wordEd\":\"234\",\"wordsName\":\"弃\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9690\",\"wordBg\":\"234\",\"wordEd\":\"310\",\"wordsName\":\"香山\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9996\",\"wordBg\":\"310\",\"wordEd\":\"358\",\"wordsName\":\"北\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9841\",\"wordBg\":\"358\",\"wordEd\":\"444\",\"wordsName\":\"王\",\"wp\":\"n\"}]},{\"bg\":\"34010\",\"ed\":\"39680\",\"nc\":\"1.0\",\"onebest\":\"动机真马长嘶剑气胡爽。十\",\"si\":\"7\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.4961\",\"wordBg\":\"35\",\"wordEd\":\"123\",\"wordsName\":\"动机\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9861\",\"wordBg\":\"123\",\"wordEd\":\"147\",\"wordsName\":\"真\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"147\",\"wordEd\":\"207\",\"wordsName\":\"马\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"207\",\"wordEd\":\"242\",\"wordsName\":\"长\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"242\",\"wordEd\":\"274\",\"wordsName\":\"嘶\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9998\",\"wordBg\":\"274\",\"wordEd\":\"300\",\"wordsName\":\"剑\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9836\",\"wordBg\":\"300\",\"wordEd\":\"336\",\"wordsName\":\"气\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9602\",\"wordBg\":\"336\",\"wordEd\":\"394\",\"wordsName\":\"胡\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9602\",\"wordBg\":\"394\",\"wordEd\":\"513\",\"wordsName\":\"爽\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"513\",\"wordEd\":\"513\",\"wordsName\":\"。\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.7750\",\"wordBg\":\"513\",\"wordEd\":\"540\",\"wordsName\":\"十\",\"wp\":\"n\"}]},{\"bg\":\"39700\",\"ed\":\"43380\",\"nc\":\"1.0\",\"onebest\":\"是黄河水妈妈吧。\",\"si\":\"8\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.4977\",\"wordBg\":\"20\",\"wordEd\":\"59\",\"wordsName\":\"是\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9837\",\"wordBg\":\"59\",\"wordEd\":\"256\",\"wordsName\":\"黄河水\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.8993\",\"wordBg\":\"256\",\"wordEd\":\"305\",\"wordsName\":\"妈妈\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9878\",\"wordBg\":\"305\",\"wordEd\":\"364\",\"wordsName\":\"吧\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"364\",\"wordEd\":\"364\",\"wordsName\":\"。\",\"wp\":\"p\"}]},{\"bg\":\"43870\",\"ed\":\"48410\",\"nc\":\"1.0\",\"onebest\":\"20年中空间谁能想刚\",\"si\":\"9\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"27\",\"wordEd\":\"119\",\"wordsName\":\"20\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"119\",\"wordEd\":\"150\",\"wordsName\":\"年\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"150\",\"wordEd\":\"212\",\"wordsName\":\"中\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9500\",\"wordBg\":\"212\",\"wordEd\":\"264\",\"wordsName\":\"空间\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"264\",\"wordEd\":\"302\",\"wordsName\":\"谁\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"302\",\"wordEd\":\"322\",\"wordsName\":\"能\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9527\",\"wordBg\":\"322\",\"wordEd\":\"386\",\"wordsName\":\"想\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.8273\",\"wordBg\":\"386\",\"wordEd\":\"448\",\"wordsName\":\"刚\",\"wp\":\"n\"}]},{\"bg\":\"48760\",\"ed\":\"53570\",\"nc\":\"1.0\",\"onebest\":\"女童长刀所想\",\"si\":\"10\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9989\",\"wordBg\":\"27\",\"wordEd\":\"262\",\"wordsName\":\"女童\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9974\",\"wordBg\":\"262\",\"wordEd\":\"330\",\"wordsName\":\"长刀\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"330\",\"wordEd\":\"382\",\"wordsName\":\"所\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"382\",\"wordEd\":\"476\",\"wordsName\":\"想\",\"wp\":\"n\"}]},{\"bg\":\"53590\",\"ed\":\"55290\",\"nc\":\"1.0\",\"onebest\":\"我我杀声\",\"si\":\"11\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9995\",\"wordBg\":\"37\",\"wordEd\":\"63\",\"wordsName\":\"我\",\"wp\":\"s\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"63\",\"wordEd\":\"84\",\"wordsName\":\"我\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"84\",\"wordEd\":\"124\",\"wordsName\":\"杀\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9848\",\"wordBg\":\"124\",\"wordEd\":\"164\",\"wordsName\":\"声\",\"wp\":\"n\"}]},{\"bg\":\"55450\",\"ed\":\"59280\",\"nc\":\"1.0\",\"onebest\":\"忠魂埋骨他乡\",\"si\":\"12\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"3\",\"wordEd\":\"62\",\"wordsName\":\"忠\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"62\",\"wordEd\":\"90\",\"wordsName\":\"魂\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"90\",\"wordEd\":\"122\",\"wordsName\":\"埋\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"122\",\"wordEd\":\"189\",\"wordsName\":\"骨\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"189\",\"wordEd\":\"284\",\"wordsName\":\"他乡\",\"wp\":\"n\"}]},{\"bg\":\"59300\",\"ed\":\"63400\",\"nc\":\"1.0\",\"onebest\":\"C拜时到家，我\",\"si\":\"13\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9998\",\"wordBg\":\"23\",\"wordEd\":\"76\",\"wordsName\":\"C\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9874\",\"wordBg\":\"76\",\"wordEd\":\"164\",\"wordsName\":\"拜\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.8997\",\"wordBg\":\"164\",\"wordEd\":\"200\",\"wordsName\":\"时\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"200\",\"wordEd\":\"319\",\"wordsName\":\"到家\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"319\",\"wordEd\":\"319\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.9980\",\"wordBg\":\"319\",\"wordEd\":\"384\",\"wordsName\":\"我\",\"wp\":\"n\"}]},{\"bg\":\"63410\",\"ed\":\"68280\",\"nc\":\"1.0\",\"onebest\":\"人叹息，更无语，血泪满筐\",\"si\":\"14\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"24\",\"wordEd\":\"91\",\"wordsName\":\"人\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"91\",\"wordEd\":\"154\",\"wordsName\":\"叹息\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"154\",\"wordEd\":\"154\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"154\",\"wordEd\":\"217\",\"wordsName\":\"更\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"217\",\"wordEd\":\"266\",\"wordsName\":\"无语\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"266\",\"wordEd\":\"266\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"266\",\"wordEd\":\"334\",\"wordsName\":\"血泪\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"334\",\"wordEd\":\"389\",\"wordsName\":\"满\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.6113\",\"wordBg\":\"389\",\"wordEd\":\"476\",\"wordsName\":\"筐\",\"wp\":\"n\"}]},{\"bg\":\"69600\",\"ed\":\"70980\",\"nc\":\"1.0\",\"onebest\":\"囡。\",\"si\":\"15\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.5302\",\"wordBg\":\"84\",\"wordEd\":\"132\",\"wordsName\":\"囡\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"132\",\"wordEd\":\"132\",\"wordsName\":\"。\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"132\",\"wordEd\":\"132\",\"wordsName\":\"\",\"wp\":\"g\"}]},{\"bg\":\"70990\",\"ed\":\"88680\",\"nc\":\"1.0\",\"onebest\":\"八单曲陈北王珍备忘。早前黄尘飞扬不愿，守土复不开张，堂堂中国要要死法\",\"si\":\"16\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.4394\",\"wordBg\":\"12\",\"wordEd\":\"76\",\"wordsName\":\"八\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"76\",\"wordEd\":\"254\",\"wordsName\":\"单曲\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9718\",\"wordBg\":\"254\",\"wordEd\":\"314\",\"wordsName\":\"陈\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9717\",\"wordBg\":\"314\",\"wordEd\":\"370\",\"wordsName\":\"北\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9979\",\"wordBg\":\"370\",\"wordEd\":\"496\",\"wordsName\":\"王\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9908\",\"wordBg\":\"496\",\"wordEd\":\"560\",\"wordsName\":\"珍\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9860\",\"wordBg\":\"560\",\"wordEd\":\"619\",\"wordsName\":\"备忘\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"619\",\"wordEd\":\"619\",\"wordsName\":\"。\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.9994\",\"wordBg\":\"619\",\"wordEd\":\"706\",\"wordsName\":\"早前\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"706\",\"wordEd\":\"740\",\"wordsName\":\"黄\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"740\",\"wordEd\":\"803\",\"wordsName\":\"尘\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"803\",\"wordEd\":\"992\",\"wordsName\":\"飞扬\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9934\",\"wordBg\":\"992\",\"wordEd\":\"1049\",\"wordsName\":\"不\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.7601\",\"wordBg\":\"1049\",\"wordEd\":\"1100\",\"wordsName\":\"愿\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"1100\",\"wordEd\":\"1100\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.7677\",\"wordBg\":\"1100\",\"wordEd\":\"1205\",\"wordsName\":\"守土\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4812\",\"wordBg\":\"1205\",\"wordEd\":\"1292\",\"wordsName\":\"复\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5164\",\"wordBg\":\"1292\",\"wordEd\":\"1291\",\"wordsName\":\"不\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5165\",\"wordBg\":\"1291\",\"wordEd\":\"1479\",\"wordsName\":\"开张\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"1479\",\"wordEd\":\"1479\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"1479\",\"wordEd\":\"1538\",\"wordsName\":\"堂堂\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"1538\",\"wordEd\":\"1596\",\"wordsName\":\"中国\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"1596\",\"wordEd\":\"1626\",\"wordsName\":\"要\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.6818\",\"wordBg\":\"1626\",\"wordEd\":\"1640\",\"wordsName\":\"要\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9562\",\"wordBg\":\"1640\",\"wordEd\":\"1716\",\"wordsName\":\"死\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9537\",\"wordBg\":\"1716\",\"wordEd\":\"1763\",\"wordsName\":\"法\",\"wp\":\"n\"}]},{\"bg\":\"90560\",\"ed\":\"91300\",\"nc\":\"1.0\",\"onebest\":\"好\",\"si\":\"17\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.7312\",\"wordBg\":\"3\",\"wordEd\":\"22\",\"wordsName\":\"好\",\"wp\":\"n\"}]},{\"bg\":\"91440\",\"ed\":\"96270\",\"nc\":\"1.0\",\"onebest\":\"大一号\",\"si\":\"18\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9957\",\"wordBg\":\"30\",\"wordEd\":\"109\",\"wordsName\":\"大\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9957\",\"wordBg\":\"109\",\"wordEd\":\"160\",\"wordsName\":\"一\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9955\",\"wordBg\":\"160\",\"wordEd\":\"182\",\"wordsName\":\"号\",\"wp\":\"n\"}]},{\"bg\":\"96290\",\"ed\":\"97500\",\"nc\":\"1.0\",\"onebest\":\"嗯\",\"si\":\"19\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9929\",\"wordBg\":\"3\",\"wordEd\":\"116\",\"wordsName\":\"嗯\",\"wp\":\"s\"}]},{\"bg\":\"97900\",\"ed\":\"101490\",\"nc\":\"1.0\",\"onebest\":\"囔，\",\"si\":\"20\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.5871\",\"wordBg\":\"3\",\"wordEd\":\"356\",\"wordsName\":\"囔\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"356\",\"wordEd\":\"356\",\"wordsName\":\"，\",\"wp\":\"p\"}]},{\"bg\":\"101530\",\"ed\":\"102830\",\"nc\":\"1.0\",\"onebest\":\"谁知\",\"si\":\"21\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"-1\",\"wordEd\":\"-1\",\"wordsName\":\"谁知\",\"wp\":\"n\"}]},{\"bg\":\"102840\",\"ed\":\"107370\",\"nc\":\"1.0\",\"onebest\":\"谁知\",\"si\":\"22\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"425\",\"wordEd\":\"432\",\"wordsName\":\"谁知\",\"wp\":\"n\"}]},{\"bg\":\"107800\",\"ed\":\"111160\",\"nc\":\"1.0\",\"onebest\":\"谁知\",\"si\":\"23\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"-1\",\"wordEd\":\"-1\",\"wordsName\":\"谁知\",\"wp\":\"n\"}]},{\"bg\":\"111420\",\"ed\":\"112520\",\"nc\":\"1.0\",\"onebest\":\"囡\",\"si\":\"24\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.6784\",\"wordBg\":\"3\",\"wordEd\":\"104\",\"wordsName\":\"囡\",\"wp\":\"n\"}]},{\"bg\":\"112570\",\"ed\":\"117270\",\"nc\":\"1.0\",\"onebest\":\"当年七墙上北王\",\"si\":\"25\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"13\",\"wordEd\":\"132\",\"wordsName\":\"当年\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9761\",\"wordBg\":\"132\",\"wordEd\":\"237\",\"wordsName\":\"七\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9501\",\"wordBg\":\"237\",\"wordEd\":\"314\",\"wordsName\":\"墙上\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.8797\",\"wordBg\":\"314\",\"wordEd\":\"355\",\"wordsName\":\"北\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.8851\",\"wordBg\":\"355\",\"wordEd\":\"464\",\"wordsName\":\"王\",\"wp\":\"n\"}]},{\"bg\":\"117280\",\"ed\":\"117830\",\"nc\":\"1.0\",\"onebest\":\"有\",\"si\":\"26\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"33\",\"wordEd\":\"52\",\"wordsName\":\"有\",\"wp\":\"n\"}]},{\"bg\":\"117840\",\"ed\":\"122960\",\"nc\":\"1.0\",\"onebest\":\"攻击，这马长嘶剑气如霜，西\",\"si\":\"27\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9775\",\"wordBg\":\"3\",\"wordEd\":\"62\",\"wordsName\":\"攻击\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"62\",\"wordEd\":\"62\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.9389\",\"wordBg\":\"62\",\"wordEd\":\"89\",\"wordsName\":\"这\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"89\",\"wordEd\":\"150\",\"wordsName\":\"马\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"150\",\"wordEd\":\"184\",\"wordsName\":\"长\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"184\",\"wordEd\":\"218\",\"wordsName\":\"嘶\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"218\",\"wordEd\":\"246\",\"wordsName\":\"剑\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"246\",\"wordEd\":\"280\",\"wordsName\":\"气\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"280\",\"wordEd\":\"336\",\"wordsName\":\"如\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"336\",\"wordEd\":\"454\",\"wordsName\":\"霜\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"454\",\"wordEd\":\"454\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.6428\",\"wordBg\":\"454\",\"wordEd\":\"508\",\"wordsName\":\"西\",\"wp\":\"n\"}]},{\"bg\":\"122970\",\"ed\":\"127100\",\"nc\":\"1.0\",\"onebest\":\"是黄河水妈妈。\",\"si\":\"28\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9950\",\"wordBg\":\"40\",\"wordEd\":\"190\",\"wordsName\":\"是\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"190\",\"wordEd\":\"292\",\"wordsName\":\"黄河水\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9987\",\"wordBg\":\"292\",\"wordEd\":\"408\",\"wordsName\":\"妈妈\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"408\",\"wordEd\":\"408\",\"wordsName\":\"。\",\"wp\":\"p\"}]},{\"bg\":\"127110\",\"ed\":\"131570\",\"nc\":\"1.0\",\"onebest\":\"20点钟后见谁能像高\",\"si\":\"29\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"25\",\"wordEd\":\"121\",\"wordsName\":\"20\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9844\",\"wordBg\":\"121\",\"wordEd\":\"150\",\"wordsName\":\"点\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9844\",\"wordBg\":\"150\",\"wordEd\":\"212\",\"wordsName\":\"钟\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9748\",\"wordBg\":\"212\",\"wordEd\":\"240\",\"wordsName\":\"后\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9758\",\"wordBg\":\"240\",\"wordEd\":\"264\",\"wordsName\":\"见\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"264\",\"wordEd\":\"304\",\"wordsName\":\"谁\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"304\",\"wordEd\":\"322\",\"wordsName\":\"能\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9290\",\"wordBg\":\"322\",\"wordEd\":\"386\",\"wordsName\":\"像\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9454\",\"wordBg\":\"386\",\"wordEd\":\"440\",\"wordsName\":\"高\",\"wp\":\"n\"}]},{\"bg\":\"131730\",\"ed\":\"134220\",\"nc\":\"1.0\",\"onebest\":\"女王\",\"si\":\"30\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.7517\",\"wordBg\":\"3\",\"wordEd\":\"244\",\"wordsName\":\"女王\",\"wp\":\"n\"}]},{\"bg\":\"134370\",\"ed\":\"136910\",\"nc\":\"1.0\",\"onebest\":\"常���告诉我想\",\"si\":\"31\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"12\",\"wordEd\":\"74\",\"wordsName\":\"常常\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9939\",\"wordBg\":\"74\",\"wordEd\":\"132\",\"wordsName\":\"告诉\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9939\",\"wordBg\":\"132\",\"wordEd\":\"156\",\"wordsName\":\"我\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"156\",\"wordEd\":\"238\",\"wordsName\":\"想\",\"wp\":\"n\"}]},{\"bg\":\"136930\",\"ed\":\"138670\",\"nc\":\"1.0\",\"onebest\":\"我烧身\",\"si\":\"32\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"31\",\"wordEd\":\"73\",\"wordsName\":\"我\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9349\",\"wordBg\":\"73\",\"wordEd\":\"114\",\"wordsName\":\"烧\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.6726\",\"wordBg\":\"114\",\"wordEd\":\"150\",\"wordsName\":\"身\",\"wp\":\"n\"}]},{\"bg\":\"138680\",\"ed\":\"151650\",\"nc\":\"1.0\",\"onebest\":\"忠魂埋骨大侠大侠poss白死到家哦，忍叹息，更无语血泪慢光\",\"si\":\"33\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9700\",\"wordBg\":\"90\",\"wordEd\":\"141\",\"wordsName\":\"忠\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9700\",\"wordBg\":\"141\",\"wordEd\":\"158\",\"wordsName\":\"魂\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9700\",\"wordBg\":\"158\",\"wordEd\":\"189\",\"wordsName\":\"埋\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9700\",\"wordBg\":\"189\",\"wordEd\":\"341\",\"wordsName\":\"骨\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4438\",\"wordBg\":\"341\",\"wordEd\":\"463\",\"wordsName\":\"大侠\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5465\",\"wordBg\":\"463\",\"wordEd\":\"463\",\"wordsName\":\"大侠\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4692\",\"wordBg\":\"463\",\"wordEd\":\"587\",\"wordsName\":\"poss\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5336\",\"wordBg\":\"587\",\"wordEd\":\"704\",\"wordsName\":\"白\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9985\",\"wordBg\":\"704\",\"wordEd\":\"825\",\"wordsName\":\"死\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9999\",\"wordBg\":\"825\",\"wordEd\":\"894\",\"wordsName\":\"到家\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4665\",\"wordBg\":\"894\",\"wordEd\":\"825\",\"wordsName\":\"哦\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"825\",\"wordEd\":\"825\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.3640\",\"wordBg\":\"825\",\"wordEd\":\"954\",\"wordsName\":\"忍\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"954\",\"wordEd\":\"1017\",\"wordsName\":\"叹息\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"1017\",\"wordEd\":\"1017\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"1017\",\"wordEd\":\"1066\",\"wordsName\":\"更\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"1066\",\"wordEd\":\"1107\",\"wordsName\":\"无语\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"1107\",\"wordEd\":\"1134\",\"wordsName\":\"血泪\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.6816\",\"wordBg\":\"1134\",\"wordEd\":\"1190\",\"wordsName\":\"慢\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.7963\",\"wordBg\":\"1190\",\"wordEd\":\"1279\",\"wordsName\":\"光\",\"wp\":\"n\"}]},{\"bg\":\"151950\",\"ed\":\"152890\",\"nc\":\"1.0\",\"onebest\":\"嗯\",\"si\":\"34\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"35\",\"wordEd\":\"88\",\"wordsName\":\"嗯\",\"wp\":\"s\"}]},{\"bg\":\"153540\",\"ed\":\"154710\",\"nc\":\"1.0\",\"onebest\":\"是吧\",\"si\":\"35\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.4884\",\"wordBg\":\"23\",\"wordEd\":\"82\",\"wordsName\":\"是\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.6191\",\"wordBg\":\"82\",\"wordEd\":\"109\",\"wordsName\":\"吧\",\"wp\":\"n\"}]},{\"bg\":\"154770\",\"ed\":\"155890\",\"nc\":\"1.0\",\"onebest\":\"P等\",\"si\":\"36\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.5174\",\"wordBg\":\"3\",\"wordEd\":\"80\",\"wordsName\":\"P\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.6835\",\"wordBg\":\"80\",\"wordEd\":\"107\",\"wordsName\":\"等\",\"wp\":\"n\"}]},{\"bg\":\"155900\",\"ed\":\"162790\",\"nc\":\"1.0\",\"onebest\":\"区余震北方人北望草青，黄尘飞扬，\",\"si\":\"37\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.2723\",\"wordBg\":\"3\",\"wordEd\":\"62\",\"wordsName\":\"区\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4494\",\"wordBg\":\"62\",\"wordEd\":\"147\",\"wordsName\":\"余震\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4123\",\"wordBg\":\"147\",\"wordEd\":\"331\",\"wordsName\":\"北方\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4247\",\"wordBg\":\"331\",\"wordEd\":\"393\",\"wordsName\":\"人\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"393\",\"wordEd\":\"424\",\"wordsName\":\"北\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"424\",\"wordEd\":\"454\",\"wordsName\":\"望\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"454\",\"wordEd\":\"510\",\"wordsName\":\"草\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"510\",\"wordEd\":\"542\",\"wordsName\":\"青\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"542\",\"wordEd\":\"542\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"542\",\"wordEd\":\"574\",\"wordsName\":\"黄\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9943\",\"wordBg\":\"574\",\"wordEd\":\"664\",\"wordsName\":\"尘\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9179\",\"wordBg\":\"664\",\"wordEd\":\"674\",\"wordsName\":\"飞扬\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"674\",\"wordEd\":\"674\",\"wordsName\":\"，\",\"wp\":\"p\"}]},{\"bg\":\"163930\",\"ed\":\"172260\",\"nc\":\"1.0\",\"onebest\":\"不敢守土不开，抢堂堂中国要当死亡\",\"si\":\"38\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"25\",\"wordEd\":\"126\",\"wordsName\":\"不敢\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"126\",\"wordEd\":\"233\",\"wordsName\":\"守土\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"233\",\"wordEd\":\"320\",\"wordsName\":\"不\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.6304\",\"wordBg\":\"320\",\"wordEd\":\"378\",\"wordsName\":\"开\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"378\",\"wordEd\":\"378\",\"wordsName\":\"，\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.5915\",\"wordBg\":\"378\",\"wordEd\":\"512\",\"wordsName\":\"抢\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"512\",\"wordEd\":\"569\",\"wordsName\":\"堂堂\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"569\",\"wordEd\":\"627\",\"wordsName\":\"中国\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"627\",\"wordEd\":\"655\",\"wordsName\":\"要\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9982\",\"wordBg\":\"655\",\"wordEd\":\"682\",\"wordsName\":\"当\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9988\",\"wordBg\":\"682\",\"wordEd\":\"827\",\"wordsName\":\"死亡\",\"wp\":\"n\"}]},{\"bg\":\"173800\",\"ed\":\"179260\",\"nc\":\"1.0\",\"onebest\":\"Ilike嗯\",\"si\":\"39\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9467\",\"wordBg\":\"128\",\"wordEd\":\"128\",\"wordsName\":\"I\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9467\",\"wordBg\":\"128\",\"wordEd\":\"262\",\"wordsName\":\"like\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"262\",\"wordEd\":\"318\",\"wordsName\":\"嗯\",\"wp\":\"s\"}]},{\"bg\":\"179280\",\"ed\":\"180390\",\"nc\":\"1.0\",\"onebest\":\"地方\",\"si\":\"40\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9978\",\"wordBg\":\"15\",\"wordEd\":\"108\",\"wordsName\":\"地方\",\"wp\":\"n\"}]},{\"bg\":\"180400\",\"ed\":\"187220\",\"nc\":\"1.0\",\"onebest\":\"区域辰被王人王到前方方程欢迎，\",\"si\":\"41\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.8685\",\"wordBg\":\"3\",\"wordEd\":\"92\",\"wordsName\":\"区域\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.8492\",\"wordBg\":\"92\",\"wordEd\":\"147\",\"wordsName\":\"辰\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9648\",\"wordBg\":\"147\",\"wordEd\":\"210\",\"wordsName\":\"被\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5517\",\"wordBg\":\"210\",\"wordEd\":\"329\",\"wordsName\":\"王\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4861\",\"wordBg\":\"329\",\"wordEd\":\"389\",\"wordsName\":\"人\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5098\",\"wordBg\":\"389\",\"wordEd\":\"453\",\"wordsName\":\"王\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5865\",\"wordBg\":\"453\",\"wordEd\":\"501\",\"wordsName\":\"到\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5872\",\"wordBg\":\"501\",\"wordEd\":\"572\",\"wordsName\":\"前方\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.4128\",\"wordBg\":\"572\",\"wordEd\":\"637\",\"wordsName\":\"方程\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"637\",\"wordEd\":\"669\",\"wordsName\":\"欢迎\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"669\",\"wordEd\":\"669\",\"wordsName\":\"，\",\"wp\":\"p\"}]},{\"bg\":\"187240\",\"ed\":\"193650\",\"nc\":\"1.0\",\"onebest\":\"呀感受不夸奖\",\"si\":\"42\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9766\",\"wordBg\":\"3\",\"wordEd\":\"140\",\"wordsName\":\"呀\",\"wp\":\"s\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"140\",\"wordEd\":\"355\",\"wordsName\":\"感受\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.5896\",\"wordBg\":\"355\",\"wordEd\":\"441\",\"wordsName\":\"不\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.9925\",\"wordBg\":\"441\",\"wordEd\":\"566\",\"wordsName\":\"夸奖\",\"wp\":\"n\"}]},{\"bg\":\"193670\",\"ed\":\"196620\",\"nc\":\"1.0\",\"onebest\":\"他们重要的司法\",\"si\":\"43\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9871\",\"wordBg\":\"14\",\"wordEd\":\"42\",\"wordsName\":\"他们\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"42\",\"wordEd\":\"132\",\"wordsName\":\"重要\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"132\",\"wordEd\":\"156\",\"wordsName\":\"的\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"156\",\"wordEd\":\"272\",\"wordsName\":\"司法\",\"wp\":\"n\"}]},{\"bg\":\"198240\",\"ed\":\"198990\",\"nc\":\"1.0\",\"onebest\":\"唉\",\"si\":\"44\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.9105\",\"wordBg\":\"3\",\"wordEd\":\"34\",\"wordsName\":\"唉\",\"wp\":\"s\"}]},{\"bg\":\"199180\",\"ed\":\"200540\",\"nc\":\"1.0\",\"onebest\":\"来，\",\"si\":\"45\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"29\",\"wordEd\":\"120\",\"wordsName\":\"来\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"120\",\"wordEd\":\"120\",\"wordsName\":\"，\",\"wp\":\"p\"}]},{\"bg\":\"200550\",\"ed\":\"204940\",\"nc\":\"1.0\",\"onebest\":\"谁知\",\"si\":\"46\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"1.0000\",\"wordBg\":\"25\",\"wordEd\":\"361\",\"wordsName\":\"谁知\",\"wp\":\"n\"}]},{\"bg\":\"207730\",\"ed\":\"209960\",\"nc\":\"1.0\",\"onebest\":\"囔。\",\"si\":\"47\",\"speaker\":\"0\",\"wordsResultList\":[{\"alternativeList\":[],\"wc\":\"0.6509\",\"wordBg\":\"3\",\"wordEd\":\"112\",\"wordsName\":\"囔\",\"wp\":\"n\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"112\",\"wordEd\":\"112\",\"wordsName\":\"。\",\"wp\":\"p\"},{\"alternativeList\":[],\"wc\":\"0.0000\",\"wordBg\":\"112\",\"wordEd\":\"112\",\"wordsName\":\"\",\"wp\":\"g\"}]}]";
    	
    	
    	Gson gson = new Gson();    
        //把JSON数据转化为对象
    	List<Content> list = new ArrayList<>();
    	JsonArray array = new JsonParser().parse(str).getAsJsonArray();
    	
    	String segementStr = "";
    	String originalStr = "";
    	for(int i=0; i < array.size(); i++){
    		
    		Content content = gson.fromJson(array.get(i), Content.class);
    		List<WordsResultList> wordsResultList = content.getWordsResultList();
//    		System.out.println(content.getWordsResultList().size());
    		//获取原始字符串
    		originalStr += content.getOnebest();
    		//獲取分詞后的字符串
    		for(int j=0; j < wordsResultList.size(); j++){
    			segementStr += " "+wordsResultList.get(j).getWordsName();
    		}
    	}
    	System.out.println(originalStr);
    	System.out.println(segementStr);
    	System.exit(0);
    	
//    	for (final JsonElement elem : array) {
//            list.add(gson.fromJson(elem, Content.class));
//            
//            Content content = gson.fromJson(elem, Content.class);
//            System.out.println(content.getOnebest());
//            List<WordsResult> tmpList = content.getWordsResult();
//            System.out.println(tmpList);
//            for(final WordsResult elem2 : tmpList){
//            	System.out.println(elem2.getWordsName());
//            }
//        }
    }
    
    public void test(){
    	String s = "{\"error\":0,\"status\":\"success\",\"results\":[{\"currentCity\":\"青岛\",\"index\":[{\"title\":\"穿衣\",\"zs\":\"较冷\",\"tipt\":\"穿衣指数\",\"des\":\"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。\"},{\"title\":\"紫外线强度\",\"zs\":\"最弱\",\"tipt\":\"紫外线强度指数\",\"des\":\"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。\"}]}]}";    
        Gson gson = new Gson();    
//        
//        
        //把JSON数据转化为对象
        JsonBean jsonBean = gson.fromJson(s, JsonBean.class);
        System.out.println(jsonBean.getError());
        
        if(jsonBean.getError() == 0){
        	List<Results> results = jsonBean.getResults();
        	for(int i = 0; i < results.size(); i++){
        		List<com.iflytek.msp.po.json.Index> indexList = 
        		results.get(i).getIndex();
        		for(int j=0;j< indexList.size();j++){
        			com.iflytek.msp.po.json.Index ii = indexList.get(j);
        			
        			System.out.print(ii.getTipt()+" | ");
        			System.out.print(ii.getTitle()+" | ");
        			System.out.print(ii.getDes()+" | ");
        			
        			System.out.println("-------------");
        			
        		}
        		
        	}
        }
    }
    
    public void connectMySQL(){
    	 Connection conn = null;
         Statement stmt = null;
         try{
             // 注册 JDBC 驱动
             Class.forName("com.mysql.jdbc.Driver");
         
             // 打开链接
             System.out.println("连接数据库...");
             conn = DriverManager.getConnection(DB_URL,USER,PASS);
         
             // 执行查询
             System.out.println(" 实例化Statement对...");
             stmt = conn.createStatement();
             String sql;
             sql = "SELECT id, name, url FROM websites";
             ResultSet rs = stmt.executeQuery(sql);
         
             // 展开结果集数据库
             while(rs.next()){
                 // 通过字段检索
                 int id  = rs.getInt("id");
                 String name = rs.getString("name");
                 String url = rs.getString("url");
     
                 // 输出数据
                 System.out.print("ID: " + id);
                 System.out.print(", 站点名称: " + name);
                 System.out.print(", 站点 URL: " + url);
                 System.out.print("\n");
             }
             // 完成后关闭
             rs.close();
             stmt.close();
             conn.close();
         }catch(SQLException se){
             // 处理 JDBC 错误
             se.printStackTrace();
         }catch(Exception e){
             // 处理 Class.forName 错误
             e.printStackTrace();
         }finally{
             // 关闭资源
             try{
                 if(stmt!=null) stmt.close();
             }catch(SQLException se2){
             }// 什么都不做
             try{
                 if(conn!=null) conn.close();
             }catch(SQLException se){
                 se.printStackTrace();
             }
         }
         System.out.println("Goodbye!");
    }
    
    public void testParseJson(String str_json){
    	Gson gson = new Gson();
    }
    
}

