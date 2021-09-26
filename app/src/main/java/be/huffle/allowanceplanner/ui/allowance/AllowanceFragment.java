package be.huffle.allowanceplanner.ui.allowance;

import android.content.Intent;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import be.huffle.allowanceplanner.R;
import be.huffle.allowanceplanner.activities.AddAllowanceActivity;
import be.huffle.allowanceplanner.activities.AddExpenseActivity;

import static android.content.Context.MODE_PRIVATE;

public class AllowanceFragment extends Fragment implements View.OnClickListener
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

		sharedPreferences = root.getContext().getApplicationContext()
				.getSharedPreferences("SharedPref", MODE_PRIVATE);
		editor = sharedPreferences.edit();

		float storedAllowance = sharedPreferences.getFloat("allowance", 0.00f);
		allowanceViewModel.setAllowance(storedAllowance);

		final TextView textView = root.findViewById(R.id.text_allowance);
		final Button allowanceButton = root.findViewById(R.id.button_allowance);
		final Button expenseButton = root.findViewById(R.id.button_expense);

		allowanceButton.setOnClickListener(this);
		expenseButton.setOnClickListener(this);

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

	@Override
	public void onClick(View view)
	{
		Intent intent;

		switch (view.getId())
		{
			case R.id.button_allowance:
				intent = new Intent(getActivity(), AddAllowanceActivity.class);
				startActivity(intent);
				break;
			case R.id.button_expense:
				intent = new Intent(getActivity(), AddExpenseActivity.class);
				startActivityForResult(intent, 0);
				break;
		}
	}

  	@Override
	public void onResume()
	{
		super.onResume();

		float allowance = sharedPreferences.getFloat("allowance", 0.00f);
		allowanceViewModel.setAllowance(allowance);
	}
}