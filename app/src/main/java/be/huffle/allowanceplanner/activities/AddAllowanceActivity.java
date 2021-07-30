package be.huffle.allowanceplanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.huffle.allowanceplanner.R;

public class AddAllowanceActivity extends AppCompatActivity implements View.OnClickListener
{
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private EditText amountEditText;
	private EditText descriptionEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		final ActionBar actionBar = getSupportActionBar();
		final Button addButton = findViewById(R.id.add_button);
		amountEditText = findViewById(R.id.amount_input);
		descriptionEditText = findViewById(R.id.description_input);
		sharedPreferences = getApplicationContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
		editor = sharedPreferences.edit();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Add Allowance");

		addButton.setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		int id = item.getItemId();

		if (id == android.R.id.home)
		{
			finish();
		}

		return true;
	}

	private void addAllowance(float allowance, String description)
	{
		float newBalance = sharedPreferences.getFloat("allowance", 0.00f) + allowance;

		editor.putFloat("allowance", newBalance);
		editor.commit();

		finish();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.add_button:
				addAllowance(Float.valueOf(amountEditText.getText().toString()), descriptionEditText.getText().toString());
			break;
		}
	}
}