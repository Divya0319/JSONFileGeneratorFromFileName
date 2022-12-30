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
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonGeneratorForVastuForSale {
	static String sourceFileName = "for_sale_data.csv";
	static String jsonFileName = "forSaleData";
	static String folderName = "forSale";
	
	static String txtFilepath = "D:\\Personal Projects\\Androshow Github pages API\\" + sourceFileName;

	public static void main(String[] args) {

		File directoryPathForImages = new File(
				"D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName);
		// this line gets contents of that directory, the names of files and folders
		// using list
		String folders[] = directoryPathForImages.list();
		System.out.println("List of all folders: ");

		for (int i = 0; i < folders.length; i++) {
			System.out.println(folders[i]);
		}

		List<ForSaleData> forSaleDatas = readDataFromCsv(txtFilepath);

		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < forSaleDatas.size(); i++) {
			JSONObject jobj = new JSONObject();
			jobj.put("title", forSaleDatas.get(i).getTitle());
			jobj.put("description", forSaleDatas.get(i).getDescription());
			jobj.put("size", forSaleDatas.get(i).getSize());
			jobj.put("rate", forSaleDatas.get(i).getRate());
			jobj.put("bestFor", forSaleDatas.get(i).getBestFor());
			jobj.put("latitude", forSaleDatas.get(i).getLatitude());
			jobj.put("longitude", forSaleDatas.get(i).getLongitude());
			jobj.put("youtubeVideoId", forSaleDatas.get(i).getYoutubeVideoId());

			for (int j = 0; j < folders.length; j++) {
				
				if(jobj.containsKey("urls")) {
					JSONArray jarr = (JSONArray) jobj.get("urls");
					if(jarr.size() == forSaleDatas.size()) {
						break;
					}
				}
				
				String path = "D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName
						+ "\\" + folders[j];
				File images = new File(path);
				String[] imagesNames = images.list();
				JSONArray listOfImagesInThatFolder = new JSONArray();
				for (int k = 0; k < imagesNames.length; k++) {
					listOfImagesInThatFolder.add("https://divya0319.github.io/" + folderName + "/" +  folders[j] + "/" +  imagesNames[k]);
				}
				jobj.put("imageUrls", listOfImagesInThatFolder);
				

			}
			
			jsonArr.add(jobj);
			
			
		}

		String indented = "";
		try {
			FileWriter file = new FileWriter(
					"D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\apis\\" + jsonFileName
							+ ".json");
			String jsonStr = jsonArr.toJSONString();
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

	private static List<ForSaleData> readDataFromCsv(String fileName) {
		List<ForSaleData> forSaleDatas = new ArrayList<>();
		Path path = Paths.get(txtFilepath);
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] attributes = line.split(",");
				ForSaleData forSaleData = createForSaleObj(attributes);

				forSaleDatas.add(forSaleData);

				line = br.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return forSaleDatas;
	}

	private static ForSaleData createForSaleObj(String[] metadata) {
		String title = metadata[0];
		String description = metadata[1];
		String size = metadata[2];
		String rate = metadata[3];
		String bestFor = metadata[4];
		String latitude = metadata[5];
		String longitude = metadata[6];
		String youtubeVideoId = metadata[7];

		return new ForSaleData(title, description, size, rate, bestFor, latitude, longitude, youtubeVideoId);

	}

}
