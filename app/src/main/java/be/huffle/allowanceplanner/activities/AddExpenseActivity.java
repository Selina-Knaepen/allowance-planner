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
import android.widget.EditText;

import java.io.File;
import java.util.Date;

import be.huffle.allowanceplanner.R;
import be.huffle.allowanceplanner.models.History;
import be.huffle.allowanceplanner.services.FileService;

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
				}
				else if (Float.valueOf(amountEditText.getText().toString()) == 0)
				{
					showDialog("Amount can't be 0");
				}
				else
				{
					tryAddExpense(Float.valueOf(amountEditText.getText().toString()),
							descriptionEditText.getText().toString());
				}

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

	private void showDialogWithOptions(String message, float balance, float expense, String description)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message);
		builder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.dismiss();
						addExpense(balance, expense, description);
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
					, balance, expense, description);
		}
		else
		{
			addExpense(balance, expense, description);
		}
	}

	private void addExpense(float balance, float expense, String description)
	{
		float newBalance = balance - expense;
		File file = new File(getExternalFilesDir(null), "allowance.csv");
		FileService fileService = new FileService(file);
		if (description.isEmpty())
		{
			description = "/";
		}

		editor.putFloat("allowance", newBalance);
		editor.commit();

		finish();

		fileService.addItem(new History(new Date(), -expense, description));
	}
}