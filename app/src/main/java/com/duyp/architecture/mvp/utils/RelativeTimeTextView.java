package com.duyp.architecture.mvp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.data.remote.RemoteConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A {@code TextView} that, given a reference time, renders that time as a time
 * period relative to the current time.
 * 
 * @author Kiran Rao
 * @see #setReferenceTime(long)
 * 
 */
public class RelativeTimeTextView extends AppCompatTextView {

	private long mReferenceTime;
	private String mStrReferenceTime;
	private String mText;
	private String mPrefix;
	private String mSuffix;
	private Handler mHandler = new Handler();
	private Context mContext;

	/*
	 * private Runnable mUpdateTimeTask = new Runnable() { public void run() {
	 * long difference = Math.abs(System.currentTimeMillis() - mReferenceTime);
	 * long interval = DateUtils.MINUTE_IN_MILLIS; if (difference >
	 * DateUtils.WEEK_IN_MILLIS) { interval = DateUtils.WEEK_IN_MILLIS; } else
	 * if (difference > DateUtils.DAY_IN_MILLIS) { interval =
	 * DateUtils.DAY_IN_MILLIS; } else if (difference >
	 * DateUtils.HOUR_IN_MILLIS) { interval = DateUtils.HOUR_IN_MILLIS; }
	 * updateTextDisplay(); mHandler.postDelayed(this, interval); } };
	 */

	private UpdateTimeRunnable mUpdateTimeTask;

	public RelativeTimeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		MyApplication.setLocaleVi(context);
		mContext = context;
		init(context, attrs);
	}

	public RelativeTimeTextView(Context context, AttributeSet attrs,
								int defStyle) {
		super(context, attrs, defStyle);
//		MyApplication.setLocaleVi(context);
		mContext = context;
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.RelativeTimeTextView, 0, 0);
		try {
			mText = a
					.getString(R.styleable.RelativeTimeTextView_reference_time);
			mPrefix = a
					.getString(R.styleable.RelativeTimeTextView_relative_time_prefix);
			mSuffix = a
					.getString(R.styleable.RelativeTimeTextView_relative_time_suffix);

			mPrefix = mPrefix == null ? "" : mPrefix;
			mSuffix = mSuffix == null ? "" : mSuffix;
		} finally {
			a.recycle();
		}

		try {
			mReferenceTime = Long.valueOf(mText);
		} catch (NumberFormatException nfe) {
			/*
			 * TODO: Better exception handling
			 */
			mReferenceTime = -1L;
		}

	}

	/**
	 * Returns prefix
	 * 
	 * @return
	 */
	public String getPrefix() {
		return this.mPrefix;
	}

	/**
	 * String to be attached before the reference time
	 * 
	 * @param prefix
	 * 
	 *            Example: [prefix] in XX minutes
	 */
	public void setPrefix(String prefix) {
		this.mPrefix = prefix;
		updateTextDisplay();
	}

	/**
	 * Returns suffix
	 * 
	 * @return
	 */
	public String getSuffix() {
		return this.mSuffix;
	}

	/**
	 * String to be attached after the reference time
	 * 
	 * @param suffix
	 * 
	 *            Example: in XX minutes [suffix]
	 */
	public void setSuffix(String suffix) {
		this.mSuffix = suffix;
		updateTextDisplay();
	}

	private void update() {
		/*
		 * Note that this method could be called when a row in a ListView is
		 * recycled. Hence, we need to first stop any currently running
		 * schedules (for example from the recycled view.
		 */
		stopTaskForPeriodicallyUpdatingRelativeTime();

		/*
		 * Instantiate a new runnable with the new reference time
		 */
		mUpdateTimeTask = new UpdateTimeRunnable(mReferenceTime);

		/*
		 * Start a new schedule.
		 */
		startTaskForPeriodicallyUpdatingRelativeTime();

		/*
		 * Finally, update the text display.
		 */
		updateTextDisplay();
	}

	/**
	 * Sets the reference time for this view. At any moment, the view will
	 * render a relative time period relative to the time set here.
	 * <p/>
	 * This value can also be set with the XML attribute {@code reference_time}
	 * 
	 * @param referenceTime
	 *            The timestamp (in milliseconds since epoch) that will be the
	 *            reference point for this view.
	 */
	public void setReferenceTime(long referenceTime) {
		this.mReferenceTime = referenceTime;
		update();
	}

	public void setReferenceTime(String strReferenceTime) {
		this.mStrReferenceTime = strReferenceTime;
		this.mReferenceTime = convertDateToLong(strReferenceTime);
		update();
	}

	public void setReferenceTime(Date dateReferenceTime) {
		SimpleDateFormat df = new SimpleDateFormat(RemoteConstants.DATE_TIME_FORMAT);
		this.mStrReferenceTime = df.format(dateReferenceTime);
		this.mReferenceTime = convertDateToLong(dateReferenceTime);
		update();
	}

	private Long convertDateToLong(String strReferenceTime) {
		if(strReferenceTime == null) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(RemoteConstants.DATE_TIME_FORMAT);
		f.setTimeZone(TimeZone.getDefault());
		Date d = new Date();
		try {
			d = f.parse(strReferenceTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long milliseconds = d.getTime();
		return milliseconds;
	}

	private Long convertDateToLong(Date dateReferenceTime) {
		if(dateReferenceTime == null) {
			return null;
		}
		return dateReferenceTime.getTime();
	}

	private String convertTo12hFormat(String strReferenceTime) {
		String formattedDate = "";
		SimpleDateFormat readFormat = new SimpleDateFormat(RemoteConstants.DATE_TIME_FORMAT);
		readFormat.setTimeZone(TimeZone.getDefault());
		SimpleDateFormat writeFormat = new SimpleDateFormat("hh:mm aa");
		Date date = null;
		try {
			date = readFormat.parse(strReferenceTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			formattedDate = writeFormat.format(date);
		}

		return " at " + formattedDate;
	}

	private String formatDate(String strReferenceTime) {
//		Locale viLocale = new Locale(Constants.VI_LANGUAGE, Constants.VI_COUNTRY);
		String formattedDate = "";
		SimpleDateFormat readFormat = new SimpleDateFormat(RemoteConstants.DATE_TIME_FORMAT);
		readFormat.setTimeZone(TimeZone.getDefault());
		SimpleDateFormat writeFormat = new SimpleDateFormat("dd MMM yy");
		Date date = null;
		try {
			date = readFormat.parse(strReferenceTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			formattedDate = writeFormat.format(date);
		}

		return formattedDate;
	}

	private String convertToDayOfWeek(String strReferenceTime) {
//		Locale viLocale = new Locale(Constants.VI_LANGUAGE, Constants.VI_COUNTRY);
		String formattedDate = "";
		SimpleDateFormat readFormat = new SimpleDateFormat(RemoteConstants.DATE_TIME_FORMAT);
		readFormat.setTimeZone(TimeZone.getDefault());
		SimpleDateFormat writeFormat = new SimpleDateFormat("EEEE");
		Date date = null;
		try {
			date = readFormat.parse(strReferenceTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			formattedDate = writeFormat.format(date);
		}

		return formattedDate;
	}

	private void updateTextDisplay() {
		/*
		 * TODO: Validation, Better handling of negative cases
		 */
		if (this.mReferenceTime == -1L) {
			return;
		}
		setText(mPrefix + getRelativeTimeDisplayString() + mSuffix);
	}

	private CharSequence getRelativeTimeDisplayString() {
		long now = System.currentTimeMillis();
		long difference = now - mReferenceTime;
		// return (difference >= 0 && difference <= DateUtils.MINUTE_IN_MILLIS)
		// ? getResources()
		// .getString(R.string.just_now) : DateUtils
		// .getRelativeTimeSpanString(mReferenceTime, now,
		// DateUtils.MINUTE_IN_MILLIS,
		// DateUtils.FORMAT_ABBREV_RELATIVE);
		CharSequence result = "";
		if (difference <= DateUtils.MINUTE_IN_MILLIS) {
			result = getResources().getString(R.string.just_now);
		} else if (difference > DateUtils.MINUTE_IN_MILLIS
				&& difference <= DateUtils.DAY_IN_MILLIS) {
			result = DateUtils.getRelativeTimeSpanString(mReferenceTime, now,
					DateUtils.MINUTE_IN_MILLIS,
					DateUtils.FORMAT_ABBREV_RELATIVE);
		} else if (difference > DateUtils.DAY_IN_MILLIS
				&& difference <= (DateUtils.DAY_IN_MILLIS + DateUtils.DAY_IN_MILLIS)) {
			result = DateUtils.getRelativeTimeSpanString(mReferenceTime, now,
					DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);
		} else if (difference > (DateUtils.DAY_IN_MILLIS + DateUtils.DAY_IN_MILLIS)
				&& difference <= DateUtils.WEEK_IN_MILLIS) {
			result = convertToDayOfWeek(mStrReferenceTime);
		} else if (difference > 0 && difference > DateUtils.WEEK_IN_MILLIS) {
			result = formatDate(mStrReferenceTime);
		}

		return result;

	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		startTaskForPeriodicallyUpdatingRelativeTime();

	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopTaskForPeriodicallyUpdatingRelativeTime();
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == GONE || visibility == INVISIBLE) {
			stopTaskForPeriodicallyUpdatingRelativeTime();
		} else {
			startTaskForPeriodicallyUpdatingRelativeTime();
		}
	}

	private void startTaskForPeriodicallyUpdatingRelativeTime() {
		mHandler.post(mUpdateTimeTask);
	}

	private void stopTaskForPeriodicallyUpdatingRelativeTime() {
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.referenceTime = mReferenceTime;
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}

		SavedState ss = (SavedState) state;
		mReferenceTime = ss.referenceTime;
		super.onRestoreInstanceState(ss.getSuperState());
	}

	public static class SavedState extends BaseSavedState {

		private long referenceTime;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeLong(referenceTime);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};

		private SavedState(Parcel in) {
			super(in);
			referenceTime = in.readLong();
		}
	}

	private class UpdateTimeRunnable implements Runnable {

		private long mRefTime;

		UpdateTimeRunnable(long refTime) {
			this.mRefTime = refTime;
		}

		@Override
		public void run() {
			long difference = Math.abs(System.currentTimeMillis() - mRefTime);
			long interval = DateUtils.MINUTE_IN_MILLIS;
			if (difference > DateUtils.WEEK_IN_MILLIS) {
				interval = DateUtils.WEEK_IN_MILLIS;
			} else if (difference > DateUtils.DAY_IN_MILLIS) {
				interval = DateUtils.DAY_IN_MILLIS;
			} else if (difference > DateUtils.HOUR_IN_MILLIS) {
				interval = DateUtils.HOUR_IN_MILLIS;
			}
			updateTextDisplay();
			mHandler.postDelayed(this, interval);

		}

	}
}