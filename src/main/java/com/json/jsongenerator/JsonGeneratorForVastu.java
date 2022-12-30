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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonGeneratorForVastu {
	static String folderName = "floorPlans";
	static String jsonFileName = "floorplans";
	static String title = "Floor Plan";

	static String txtFilepath = "D:\\Personal Projects\\Androshow Github pages API\\area.txt";

	public static void main(String[] args) {
		// this line gets reference of specified directory
		File directoryPath = new File(
				"D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName);
		// this line gets contents of that directory, the names of files and folders
		// using list
		String contents[] = directoryPath.list();
		System.out.println("List of all files: ");

		for (int i = 0; i < contents.length; i++) {
			contents[i] = contents[i].replace(".jpg", "");
		}
		
		List<Area> areas = readAreasFromCsv(txtFilepath);

		List<String> coll = Arrays.asList(contents);
		coll = coll.stream().map(Integer::valueOf).sorted().map(String::valueOf).collect(Collectors.toList());
		System.out.println(coll.toString());
		
		JsonArray jsonArr = new JsonArray();
		for (int i = 0; i < coll.size(); i++) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("title", title);
			jobj.addProperty("url", "https://divya0319.github.io/" + folderName + "/" + coll.get(i) + ".jpg");
			jobj.addProperty("plotArea", areas.get(i).getPlotArea());
			jobj.addProperty("buildUpArea", areas.get(i).getBuildupArea());
			jobj.addProperty("facing", areas.get(i).getFacing());
			jsonArr.add(jobj);
		}

		String indented = "";
		try {
			FileWriter file = new FileWriter(
					"D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\apis\\" +  jsonFileName
							+ ".json");
	
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

	private static List<Area> readAreasFromCsv(String fileName) {
		List<Area> areas = new ArrayList<>();
		Path path = Paths.get(txtFilepath);
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = br.readLine();
			while (line != null) {
				String[] attributes = line.split(",");
				Area area = createArea(attributes);
				
				areas.add(area);
				
				line = br.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return areas;
	}
	
	private static Area createArea(String[] metadata) {
		String plotArea = metadata[0];
		String buildupArea = metadata[1];
		String facing = metadata[2];
		
		return new Area(plotArea, buildupArea, facing);
		
	}

}
