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

		List<ForSaleDataForPropertyList> forSaleDatas = readDataFromCsv(txtFilepath);

		JsonArray jsonArr = new JsonArray();
		for (int i = 0; i < forSaleDatas.size(); i++) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("title", forSaleDatas.get(i).getTitle());
			jobj.addProperty("description", forSaleDatas.get(i).getDescription());

			jsonArr.add(jobj);

		}

		List<String> imageUrls = new ArrayList<String>();

		// this loop creates an array of all image urls

		for (int j = 0; j < folders.length; j++) {

			String path = "D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName + "\\"
					+ folders[j];
			String onlinePath = "https://divya0319.github.io/" + folderName + "/" + folders[j];
			File images = new File(path);
			String fileName = images.list()[0];

			int index = fileName.lastIndexOf(".");

			if (index > 0) {
				String extension = fileName.substring(index + 1);
				if (extension.equals("jpeg") || extension.equals("jpg")) {

					fileName = onlinePath + "/" + fileName;

				}
			}

			imageUrls.add(fileName);

		}

		for (int i = 0; i < jsonArr.size(); i++) {
			JsonObject jobj = (JsonObject) jsonArr.get(i);

			jobj.addProperty("mainImageUrl", imageUrls.get(i));

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

	private static List<ForSaleDataForPropertyList> readDataFromCsv(String fileName) {
		List<ForSaleDataForPropertyList> forSaleDatas = new ArrayList<>();
		Path path = Paths.get(txtFilepath);
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] attributes = line.split(",");
				ForSaleDataForPropertyList forSaleData = createForSaleObj(attributes);

				forSaleDatas.add(forSaleData);

				line = br.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return forSaleDatas;
	}

	private static ForSaleDataForPropertyList createForSaleObj(String[] metadata) {
		String title = metadata[0];
		String description = metadata[1];

		return new ForSaleDataForPropertyList(title, description);

	}

}
