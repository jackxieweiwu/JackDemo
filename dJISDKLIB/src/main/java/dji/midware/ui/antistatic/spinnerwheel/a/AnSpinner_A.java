package dji.midware.ui.antistatic.spinnerwheel.a;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jack_Xie on 17-5-31.
 */

public abstract class AnSpinner_A implements AnSpinner_C {
    private List<DataSetObserver> dataSetObserverList;

    public AnSpinner_A() {
    }

    public View a(View var1, ViewGroup var2) {
        return null;
    }

    public void add_DataSetObserverList(DataSetObserver var1) {
        if(dataSetObserverList == null) {
            dataSetObserverList = new LinkedList();
        }

        dataSetObserverList.add(var1);
    }

    public void remove_DataSetObserverList(DataSetObserver var1) {
        if(dataSetObserverList != null) {
            dataSetObserverList.remove(var1);
        }

    }

    protected void iterator_DataSetObserverList() {
        if(dataSetObserverList != null) {
            Iterator var1 = dataSetObserverList.iterator();

            while(var1.hasNext()) {
                DataSetObserver var2 = (DataSetObserver)var1.next();
                var2.onChanged();
            }
        }

    }
}
