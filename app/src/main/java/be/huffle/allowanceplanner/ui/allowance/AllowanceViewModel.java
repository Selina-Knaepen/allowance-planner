package be.huffle.allowanceplanner.ui.allowance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;

public class AllowanceViewModel extends ViewModel
{
	private MutableLiveData<String> mText;
	private float allowance = 0;
	private DecimalFormat decimalFormat = new DecimalFormat();

	public AllowanceViewModel()
	{
		decimalFormat.setMaximumFractionDigits(2);
		mText = new MutableLiveData<>();
		mText.setValue("€ " + allowance);
	}

	public LiveData<String> getText()
	{
		return mText;
	}
}