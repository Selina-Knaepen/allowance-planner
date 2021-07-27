package be.huffle.allowanceplanner.ui.allowance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;

public class AllowanceViewModel extends ViewModel
{
	private final MutableLiveData<String> mText;
	private double mAllowance;
	private final DecimalFormat decimalFormat = new DecimalFormat();

	public AllowanceViewModel()
	{
		decimalFormat.setMinimumFractionDigits(2);
		mText = new MutableLiveData<>();
		setAllowance(0.00);
	}

	public LiveData<String> getText()
	{
		return mText;
	}

	public double getAllowance()
	{
		return mAllowance;
	}

	public void setAllowance(double mAllowance)
	{
		this.mAllowance = mAllowance;
		mText.setValue("€ " + decimalFormat.format(mAllowance));
	}
}