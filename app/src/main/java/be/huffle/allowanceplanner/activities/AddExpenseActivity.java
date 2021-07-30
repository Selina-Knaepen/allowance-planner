package be.huffle.allowanceplanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import be.huffle.allowanceplanner.R;

public class AddExpenseActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		final ActionBar actionBar = getSupportActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Add Expense");
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
}