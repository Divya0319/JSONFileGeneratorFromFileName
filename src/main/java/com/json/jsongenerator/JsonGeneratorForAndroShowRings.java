package com.json.jsongenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.tritonus.share.sampled.file.TAudioFileFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class JsonGeneratorForAndroShowRings 
{
	static String folderName = "rings";
	static String jsonFileName = "ringtones";
	static List<String> contentsWithUnderscore;
    public static void main( String[] args )
    {
    	// this line gets reference of specified directory
        File directoryPath = new File("D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName);
        // this line gets contents of that directory, the names of files and folders using list()
        String contents[] = directoryPath.list();
      
		contentsWithUnderscore = new ArrayList<>();
        System.out.println("List of all files: ");
        
        for(int i = 0; i < contents.length; i++) {
        	System.out.println(contents[i]);
        }
        
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
        	contents[i] = contents[i].replace(".mp3", "");
        }
        
        JsonArray jsonArr = new JsonArray();
        for(int i = 0; i < contents.length; i++) {
            File mp3File = new File("D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName + "\\" + contentsWithUnderscore.get(i));
        	JsonObject jobj = new JsonObject();
        	String duration = "";
        	try {
    			duration = getDurationWithMp3Spi(mp3File);
    		} catch (UnsupportedAudioFileException | IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        	contents[i] = contents[i].replace("_", " ");
        	jobj.addProperty("TITLE", contents[i]);
        	jobj.addProperty("DURATION", duration);
        	jobj.addProperty("URL", "https://divya0319.github.io/" + folderName + "/" + contentsWithUnderscore.get(i));
        	jsonArr.add(jobj);
        }
        
        
        String indented = "";
        try {
			FileWriter file = new FileWriter("D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\apis\\" + jsonFileName + ".json");
			String jsonStr = jsonArr.toString();
			jsonStr = jsonStr.replace("\\","");
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(jsonStr, Object.class);
			indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			file.write(indented);
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println();
        System.out.println("JSON file created: " + indented);
    }
    
    private static String getDurationWithMp3Spi(File file) throws UnsupportedAudioFileException, IOException {
    	
    	AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
    	if(fileFormat instanceof TAudioFileFormat) {
    		Map<?, ?> properties = ((TAudioFileFormat)fileFormat).properties();
    		String key = "duration";
    		long microseconds = (long)properties.get(key);
    		int milli = (int)(microseconds / 1000);
    		int sec = (milli / 1000) % 60;
    		int min = (milli  / 1000) / 60;
    		String strMin = "";
    		if(min < 1) {
    			strMin = "0" + min;
    		}
    		
    		return new String(strMin + ":" + sec);
    	} else 
    		throw new UnsupportedAudioFileException();
    	
    }
}
