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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

		JsonArray jsonArr = new JsonArray();
		for (int i = 0; i < forSaleDatas.size(); i++) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("title", forSaleDatas.get(i).getTitle());
			jobj.addProperty("description", forSaleDatas.get(i).getDescription());
			jobj.addProperty("size", forSaleDatas.get(i).getSize());
			jobj.addProperty("rate", forSaleDatas.get(i).getRate());
			jobj.addProperty("bestFor", forSaleDatas.get(i).getBestFor());
			jobj.addProperty("latitude", forSaleDatas.get(i).getLatitude());
			jobj.addProperty("longitude", forSaleDatas.get(i).getLongitude());
			jobj.addProperty("youtubeVideoId", forSaleDatas.get(i).getYoutubeVideoId());

			jsonArr.add(jobj);

		}

		List<List<String>> imageUrls = new ArrayList<List<String>>();

		for (int j = 0; j < folders.length; j++) {

			String path = "D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName + "\\"
					+ folders[j];
			String onlinePath = "https://divya0319.github.io/" + folderName + "/" + folders[j];
			File images = new File(path);
			String[] imagesNames = images.list();

			for (int k = 0; k < imagesNames.length; k++) {
				imagesNames[k] = onlinePath + "/" + imagesNames[k];
			}
			List<String> imageNamesArray = Arrays.asList(imagesNames);
			imageUrls.add(imageNamesArray);

		}

		for (int i = 0; i < jsonArr.size(); i++) {
			JsonObject jobj = (JsonObject) jsonArr.get(i);

			Gson gson = new Gson();
			JsonArray imageUrlsforIndividualProperty = gson.toJsonTree(imageUrls.get(i)).getAsJsonArray();
			jobj.add("imageUrls", imageUrlsforIndividualProperty);

		}

		String indented = "";
		try {
			FileWriter file = new FileWriter(
					"D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\apis\\" + jsonFileName
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
