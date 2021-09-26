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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import be.huffle.allowanceplanner.R;
import be.huffle.allowanceplanner.models.History;
import be.huffle.allowanceplanner.services.FileService;

public class AddAllowanceActivity extends AppCompatActivity
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
		actionBar.setTitle("Add Allowance");
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
					addAllowance(Float.valueOf(amountEditText.getText().toString()),
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

	private void addAllowance(float allowance, String description)
	{
		float newBalance = sharedPreferences.getFloat("allowance", 0.00f) + allowance;
		File file = new File(getExternalFilesDir(null), "allowance.csv");
		FileService fileService = new FileService(file);
		if (description.isEmpty())
		{
			description = "/";
		}

		editor.putFloat("allowance", newBalance);
		editor.commit();

		finish();

		fileService.addItem(new History(new Date(), allowance, description));
	}
}