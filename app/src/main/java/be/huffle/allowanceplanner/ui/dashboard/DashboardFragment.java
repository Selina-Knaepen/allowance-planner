package be.huffle.allowanceplanner.ui.dashboard;

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

import java.io.InputStream;
import java.util.List;

import be.huffle.allowanceplanner.R;
import be.huffle.allowanceplanner.models.History;
import be.huffle.allowanceplanner.services.FileService;

public class DashboardFragment extends Fragment
{

	private DashboardViewModel dashboardViewModel;
	private FileService fileService;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState)
	{
		dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

		InputStream inputStream = getResources().openRawResource(R.raw.allowance);
		fileService = new FileService(inputStream);
		List<History> historyList = fileService.readFile();

		View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
		final TextView textView = root.findViewById(R.id.text_dashboard);
		dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
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