package com.json.jsongenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;


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
        
        JSONArray jsonArr = new JSONArray();
        for(int i = 0; i < contents.length; i++) {
        	System.out.println(contents[i]);
        	JSONObject jobj = new JSONObject();
			contents[i] = contents[i].replace("_", " ");
        	jobj.put("TITLE", contents[i]);
        	jobj.put("URL", "https://divya0319.github.io/" + folderName + "/" + contentsWithUnderscore.get(i));
        	jsonArr.add(jobj);
        }
        
        
        
        try {
			FileWriter file = new FileWriter("D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\apis\\" + jsonFileName + ".json");
			String jsonStr = jsonArr.toJSONString();
			jsonStr = jsonStr.replace("\\","");
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(jsonStr, Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			file.write(indented);
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("JSON file created: " + jsonArr);
    }
}
