package com.example.ward.preference;


import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;

import com.example.ward.R;

public class PreferenceDemo extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefersFragment()).commit();
	}
}

 class PrefersFragment extends PreferenceFragment implements OnPreferenceChangeListener{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pre_settings);
	}
	
	public void onResume() {
		super.onResume();
		refreshList();
	};
	
	public void refreshList(){
		PreferenceGroup plist = (PreferenceGroup)findPreference("Profile_Settings");
		if (plist != null) {
			plist.removeAll();
			
//			 Bundle args = new Bundle();
//             args.putParcelable("Profile", profile);
             //使用自定义ChekBoxPreference
			for (int i = 0; i < 5; i++) {
				ProfilesPreference ppref = new ProfilesPreference(this, null);
				ppref.setKey("11111111111");
				ppref.setTitle("2222222");
				ppref.setPersistent(false);
				ppref.setOnPreferenceChangeListener(this);
				ppref.setSelectable(true);
				ppref.setEnabled(true);
				
				plist.addPreference(ppref);
			}
		}
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		return false;
	}
}
