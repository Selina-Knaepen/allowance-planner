package be.huffle.allowanceplanner.ui.allowance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

public class AllowanceViewModel extends ViewModel
{
	private MutableLiveData<String> mText;
	private double allowance = 0;
	private DecimalFormat decimalFormat = new DecimalFormat();

	public AllowanceViewModel()
	{
		decimalFormat.setMinimumFractionDigits(2);
		mText = new MutableLiveData<>();
		mText.setValue("€ " + decimalFormat.format(allowance));
	}

	public LiveData<String> getText()
	{
		return mText;
	}
}