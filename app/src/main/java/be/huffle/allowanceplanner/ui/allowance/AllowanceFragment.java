package be.huffle.allowanceplanner.ui.allowance;

import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import be.huffle.allowanceplanner.R;

import static android.content.Context.MODE_PRIVATE;

public class AllowanceFragment extends Fragment
{
	private AllowanceViewModel allowanceViewModel;
	private SharedPreferences sharedPreferences;
	private Editor editor;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState)
	{
		allowanceViewModel =
				new ViewModelProvider(this).get(AllowanceViewModel.class);

		View root = inflater.inflate(R.layout.fragment_allowance, container, false);

		final TextView textView = root.findViewById(R.id.text_allowance);
		final Button allowanceButton = root.findViewById(R.id.button_allowance);
		final Button expenseButton = root.findViewById(R.id.button_expense);

		allowanceButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				allowanceViewModel.setAllowance(1.00);
			}
		});

		sharedPreferences = root.getContext().getApplicationContext()
				.getSharedPreferences("MySharedPref",MODE_PRIVATE);
		editor = sharedPreferences.edit();

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