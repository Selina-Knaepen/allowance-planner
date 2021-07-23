package be.huffle.allowanceplanner.ui.allowance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllowanceViewModel extends ViewModel
{

	private MutableLiveData<String> mText;

	public AllowanceViewModel()
	{
		mText = new MutableLiveData<>();
		mText.setValue("This is allowance fragment");
	}

	public LiveData<String> getText()
	{
		return mText;
	}
}