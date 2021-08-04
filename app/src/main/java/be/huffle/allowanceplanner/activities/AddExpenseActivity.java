package be.huffle.allowanceplanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.huffle.allowanceplanner.R;

public class AddExpenseActivity extends AppCompatActivity
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
		amountEditText = findViewById(R.id.amount_input);
		descriptionEditText = findViewById(R.id.description_input);
		sharedPreferences = getApplicationContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
		editor = sharedPreferences.edit();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Add Expense");
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
			case R.id.add_menu_button:
				String amountText = amountEditText.getText().toString();

				if (amountText.isEmpty())
				{
					showDialog("Amount needs to be filled out");
					return true;
				}

				tryAddExpense(Float.valueOf(amountEditText.getText().toString()),
						descriptionEditText.getText().toString());
				return true;
			default:
				return super.onOptionsItemSelected(item);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.add_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void showDialog(String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message);
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

	private void showDialogWithOptions(String message, float balance, float expense)
	{
		boolean canAddExpense;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message);
		builder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.dismiss();
						addExpense(balance, expense);
					}
				});
		builder.setNegativeButton("No",
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

	private void tryAddExpense(float expense, String description)
	{
		float balance = sharedPreferences.getFloat("allowance", 0.00f);

		if (balance - expense < 0)
		{
			showDialogWithOptions("Insufficient funds! \nAre you sure that you want to add this expense?"
					, balance, expense);
		}
		else
		{
			addExpense(balance, expense);
		}
	}

	private void addExpense(float balance, float expense)
	{
		float newBalance = balance - expense;

		editor.putFloat("allowance", newBalance);
		editor.commit();

		finish();
	}
}