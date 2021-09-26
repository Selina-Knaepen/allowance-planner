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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import be.huffle.allowanceplanner.R;
import be.huffle.allowanceplanner.adapters.HistoryAdapter;
import be.huffle.allowanceplanner.models.History;
import be.huffle.allowanceplanner.services.FileService;

public class HistoryFragment extends Fragment
{
	private FileService fileService;
	private HistoryAdapter adapter;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_history, container, false);

		RecyclerView recyclerView = root.findViewById(R.id.rv_history);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		adapter = new HistoryAdapter();
		recyclerView.setAdapter(adapter);
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
				layoutManager.getOrientation());
		recyclerView.addItemDecoration(dividerItemDecoration);
		recyclerView.setVisibility(View.VISIBLE);

		File file = new File(getContext().getExternalFilesDir(null), "allowance.csv");
		fileService = new FileService(file);
		List<History> historyList = fileService.readFile();

		adapter.setHistoryList(historyList);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (History historyItem : historyList)
		{
			System.out.printf("Description: %s, Amount: %.2f, Date: %s%n",
					historyItem.getDescription(),
					historyItem.getAmount(),
					dateFormat.format(historyItem.getExecutedDate()));
		}

		return root;
	}
}