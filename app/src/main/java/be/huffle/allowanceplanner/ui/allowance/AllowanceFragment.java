package be.huffle.allowanceplanner.ui.allowance;

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

import be.huffle.allowanceplanner.R;

public class AllowanceFragment extends Fragment
{

	private AllowanceViewModel allowanceViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState)
	{
		allowanceViewModel =
				new ViewModelProvider(this).get(AllowanceViewModel.class);
		View root = inflater.inflate(R.layout.fragment_allowance, container, false);
		final TextView textView = root.findViewById(R.id.text_home);
		allowanceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
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