package be.huffle.allowanceplanner.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import be.huffle.allowanceplanner.models.History;

public class FileService
{
	InputStream inputStream;

	public FileService(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public List readFile()
	{
		List<History> historyList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
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
		finally
		{
			try
			{
				inputStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return historyList;
	}
}
