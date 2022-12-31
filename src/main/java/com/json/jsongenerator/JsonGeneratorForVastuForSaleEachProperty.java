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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonGeneratorForVastuForSaleEachProperty {
	static String sourceFileName = "forSaleSource.csv";
	static String jsonFileName = "forSale";
	static String folderName = "forSale";

	static String txtFilepath = "D:\\Personal Projects\\Androshow Github pages API\\" + sourceFileName;

	static List<String> folders = new ArrayList<>();

	public static void main(String[] args) {

		File directoryPathForImages = new File(
				"D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName);
		// this line gets contents of that directory, the names of files and folders
		// using list
		for (String directoryPath : directoryPathForImages.list()) {
			folders.add(directoryPath);

		}
		System.out.println("List of all folders: ");

		// creating array of all image urls before hand
		List<List<String>> allImageUrls = getAllImageUrls();

		for (int i = 0; i < folders.size(); i++) {
			String path = "D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\forSale\\"
					+ folders.get(i) + "\\" + sourceFileName;
			System.out.println(folders.get(i));
			ForSaleData forSaleData = readDataFromCsv(path);

			JsonObject jobj = new JsonObject();
			jobj.addProperty("title", forSaleData.getTitle());
			jobj.addProperty("description", forSaleData.getDescription());
			jobj.addProperty("size", forSaleData.getSize());
			jobj.addProperty("rate", forSaleData.getRate());
			jobj.addProperty("bestFor", forSaleData.getBestFor());
			jobj.addProperty("latitude", forSaleData.getLatitude());
			jobj.addProperty("longitude", forSaleData.getLongitude());
			jobj.addProperty("youtubeVideoId", forSaleData.getYoutubeVideoId());

			Gson gson = new Gson();
			JsonArray imageUrlsforIndividualProperty = gson.toJsonTree(allImageUrls.get(i)).getAsJsonArray();
			jobj.add("imageUrls", imageUrlsforIndividualProperty);

			// creates multiple json files at individual property folders
			String indented = "";
			try {
				String writerPath = "D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\forSale\\"
						+ folders.get(i) + "\\" + jsonFileName + ".json";
				FileWriter file = new FileWriter(writerPath);
				Gson gsonWriter = new GsonBuilder().setPrettyPrinting().create();
				indented = gsonWriter.toJson(jobj);
				file.write(indented);
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("JSON file created: " + indented);

		}

	}

	private static ForSaleData readDataFromCsv(String fileName) {
		ForSaleData forSaleData = new ForSaleData();
		for (int i = 0; i < folders.size(); i++) {
			String onlinePath = "https://divya0319.github.io/" + folderName + "/" + folders.get(i);
			File csvFile = new File(fileName);
			Path csvPath = Paths.get(fileName);
			try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
				String line = br.readLine();
				line = br.readLine();
				while (line != null) {
					String[] attributes = line.split(",");
					forSaleData = createForSaleObj(attributes);

					line = br.readLine();

				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return forSaleData;
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

	// generates image urls for all the properties in one go
	private static List<List<String>> getAllImageUrls() {
		List<List<String>> allImageUrls = new ArrayList<List<String>>();

		for (int j = 0; j < folders.size(); j++) {

			String path = "D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName + "\\"
					+ folders.get(j);
			String onlinePath = "https://divya0319.github.io/" + folderName + "/" + folders.get(j);
			File images = new File(path);
			String[] files = images.list();
			List<String> imageNamesArray = new ArrayList<>();

			for (String fileName : files) {
				int index = fileName.lastIndexOf(".");

				if (index > 0) {
					String extension = fileName.substring(index + 1);
					if (extension.equals("jpeg") || extension.equals("jpg")) {
						imageNamesArray.add(fileName);
					}
				}
			}

			for (int k = 0; k < imageNamesArray.size(); k++) {
				imageNamesArray.set(k, onlinePath + "/" + imageNamesArray.get(k));
			}
			allImageUrls.add(imageNamesArray);

		}

		return allImageUrls;
	}

}
