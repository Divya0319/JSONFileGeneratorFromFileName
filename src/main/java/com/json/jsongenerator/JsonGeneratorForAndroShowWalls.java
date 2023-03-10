package com.json.jsongenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class JsonGeneratorForAndroShowWalls 
{
	static String folderName = "walls";
	static String jsonFileName = "walls";
	static List<String> contentsWithUnderscore;
    public static void main( String[] args )
    {
    	// this line gets reference of specified directory
        File directoryPath = new File("D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName);
        // this line gets contents of that directory, the names of files and folders using list()
        String contents[] = directoryPath.list();
		contentsWithUnderscore = new ArrayList<>();
        System.out.println("List of all files: ");
        
        //List<String> coll = Arrays.asList(contents);
        //coll = coll.stream()
        //		.map(Integer::valueOf)
        //		.sorted()
        //		.map(String::valueOf)
        //		.collect(Collectors.toList());
		
		for(int i = 0; i < contents.length; i++) {
			contentsWithUnderscore.add(contents[i]);
		}
		
		for(int i = 0; i < contents.length; i++) {
			String eachString = contents[i];
			String cap = eachString.substring(0,1).toUpperCase() + eachString.substring(1);
			contents[i] = cap;
        	contents[i] = contents[i].replace(".jpeg", "");
			
        }
        
        JsonArray jsonArr = new JsonArray();
        for(int i = 0; i < contents.length; i++) {
        	System.out.println(contents[i]);
        	JsonObject jobj = new JsonObject();
			contents[i] = contents[i].replace("_", " ");
        	jobj.addProperty("TITLE", contents[i]);
        	jobj.addProperty("URL", "https://divya0319.github.io/" + folderName + "/" + contentsWithUnderscore.get(i));
        	jsonArr.add(jobj);
        }
        
        String indented = "";
        
        try {
			FileWriter file = new FileWriter("D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\apis\\" + jsonFileName + ".json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			indented = gson.toJson(jsonArr);
			file.write(indented);
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("JSON file created: " + indented);
    }
}
