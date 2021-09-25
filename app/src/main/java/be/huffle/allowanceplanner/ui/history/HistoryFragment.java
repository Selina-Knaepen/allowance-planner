package be.huffle.allowanceplanner.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import be.huffle.allowanceplanner.R;
import be.huffle.allowanceplanner.models.History;
import be.huffle.allowanceplanner.services.FileService;

public class HistoryFragment extends Fragment
{
	private HistoryViewModel historyViewModel;
	private FileService fileService;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState)
	{
		historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

		File file = new File(getContext().getExternalFilesDir(null), "allowance.csv");
		fileService = new FileService(file);
		List<History> historyList = fileService.readFile();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (History historyItem : historyList)
		{
			System.out.printf("Description: %s, Amount: %.2f, Date: %s%n",
					historyItem.getDescription(),
					historyItem.getAmount(),
					dateFormat.format(historyItem.getExecutedDate()));
		}

		View root = inflater.inflate(R.layout.fragment_history, container, false);
		final TextView textView = root.findViewById(R.id.text_history);
		historyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()

		{
			@Override
			public void onChanged(@Nullable String s)
			{
				textView.setText(s);
			}
		});
		return root;
	}
}