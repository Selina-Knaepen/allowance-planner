package be.huffle.allowanceplanner.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import be.huffle.allowanceplanner.models.History;

public class FileService
{
	private File file;

	public FileService(File file)
	{
		this.file = file;
	}

	public List<History> readFile()
	{
		List<History> historyList = new ArrayList<>();

		if (!file.exists())
		{
			return historyList;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			String line;
			History historyItem;

			while ((line = reader.readLine()) != null)
			{
				String[] row = line.split(",");
				historyItem = new History(
						new SimpleDateFormat("yyyy-MM-dd").parse(row[0]),
						Float.parseFloat(row[1]),
						row[2]);
				historyList.add(historyItem);
			}
		}
		catch (IOException | ParseException e)
		{
			e.printStackTrace();
		}

		return historyList;
	}

	public void addItem(History history)
	{
		try
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(history.getExecutedDate());
			String content = String.format("%s,%.2f,%s",
					date,
					history.getAmount(),
					history.getDescription());

			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(content);
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
