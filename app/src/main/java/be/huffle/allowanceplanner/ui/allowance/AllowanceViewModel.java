package be.huffle.allowanceplanner.ui.allowance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;

public class AllowanceViewModel extends ViewModel
{
	private final MutableLiveData<String> mText;
	private float mAllowance;
	private final DecimalFormat decimalFormat = new DecimalFormat();

	public AllowanceViewModel()
	{
		decimalFormat.setMinimumFractionDigits(2);
		mText = new MutableLiveData<>();
	}

	public LiveData<String> getText()
	{
		return mText;
	}

	public void setAllowance(float mAllowance)
	{
		this.mAllowance = mAllowance;
		mText.setValue("€ " + decimalFormat.format(mAllowance));
	}
}