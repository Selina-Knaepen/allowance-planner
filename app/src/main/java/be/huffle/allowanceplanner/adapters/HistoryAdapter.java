package be.huffle.allowanceplanner.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.huffle.allowanceplanner.R;
import be.huffle.allowanceplanner.models.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
{
	private List<History> historyList = new ArrayList<>();

	public HistoryAdapter() {}

	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		Context context = parent.getContext();
		int layoutForListItem = R.layout.history_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(layoutForListItem, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position)
	{
		History currentHistory = historyList.get(position);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(currentHistory.getExecutedDate());

		holder.history = currentHistory;
		holder.dateTextView.setText(date);
		holder.amountTextView.setText(String.format("%.2f", currentHistory.getAmount()));
		holder.descriptionTextView.setText(currentHistory.getDescription());

		if (currentHistory.getAmount() < 0)
		{
			holder.amountTextView.setTextColor(Color.RED);
		}
		else
		{
			holder.amountTextView.setTextColor(Color.GREEN);
		}
	}

	@Override
	public int getItemCount()
	{
		return historyList.size();
	}

	public void setHistoryList(List<History> history)
	{
		historyList = history;
		sortList();
	}

	public void sortList()
	{
		Collections.reverse(historyList);
	}

	class ViewHolder extends RecyclerView.ViewHolder
	{
		private History history;
		private TextView dateTextView;
		private TextView amountTextView;
		private TextView descriptionTextView;

		public ViewHolder(View itemView)
		{
			super(itemView);

			dateTextView = itemView.findViewById(R.id.tv_history_date);
			amountTextView = itemView.findViewById(R.id.tv_history_amount);
			descriptionTextView = itemView.findViewById(R.id.tv_history_description);
		}
	}
}
