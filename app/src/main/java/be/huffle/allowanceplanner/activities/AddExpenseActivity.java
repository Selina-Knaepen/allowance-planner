package be.huffle.allowanceplanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.huffle.allowanceplanner.R;

public class AddExpenseActivity extends AppCompatActivity implements View.OnClickListener
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

		ActionBar actionBar = getSupportActionBar();
		Button addButton = findViewById(R.id.add_button);
		amountEditText = findViewById(R.id.amount_input);
		descriptionEditText = findViewById(R.id.description_input);
		sharedPreferences = getApplicationContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
		editor = sharedPreferences.edit();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Add Expense");

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

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.add_button:
				addExpense(Float.valueOf(amountEditText.getText().toString()),
						descriptionEditText.getText().toString());
			break;
		}
	}

	private void addExpense(float expense, String description)
	{
		float balance = sharedPreferences.getFloat("allowance", 0.00f);

		if (balance - expense < 0)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Insufficient funds");
			builder.setNeutralButton(
					"Ok",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.dismiss();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}
		else
		{
			float newBalance = balance - expense;

			editor.putFloat("allowance", newBalance);
			editor.commit();

			finish();
		}
	}
}