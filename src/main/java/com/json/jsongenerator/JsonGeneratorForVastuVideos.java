package com.json.jsongenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonGeneratorForVastuVideos {
	static String sourceFileName = "vastu_video_links.txt";
	static String jsonFileName = "vastuVideos";

	static String txtFilepath = "D:\\\\Personal Projects\\\\Androshow Github pages API\\" + sourceFileName;

	public static void main(String[] args) {

		List<Video> videos = readVideosFromCsv(txtFilepath);
		
		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < videos.size(); i++) {
			JSONObject jobj = new JSONObject();
			jobj.put("title", videos.get(i).getTitle());
			jobj.put("vidId", videos.get(i).getVidId());
			jobj.put("url", videos.get(i).getUrl());
			jsonArr.add(jobj);
		}

		String indented = "";
		try {
			FileWriter file = new FileWriter(
					"D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\apis\\" +  jsonFileName
							+ ".json");
			String jsonStr = jsonArr.toJSONString();
			jsonStr = jsonStr.replace("\\", "");
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(jsonStr, Object.class);
			indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			file.write(indented);
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("JSON file created: " + indented);
	}

	private static List<Video> readVideosFromCsv(String fileName) {
		List<Video> videoObjs = new ArrayList<>();
		Path path = Paths.get(txtFilepath);
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = br.readLine();
			while (line != null) {
				String[] attributes = line.split(",");
				Video vidObj = createVideoObj(attributes);
				
				videoObjs.add(vidObj);
				
				line = br.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return videoObjs;
	}
	
	private static Video createVideoObj(String[] metadata) {
		String title = metadata[0];
		String vidId = metadata[1];
		String url = metadata[2];
		
		return new Video(title, vidId, url);
		
	}

}
