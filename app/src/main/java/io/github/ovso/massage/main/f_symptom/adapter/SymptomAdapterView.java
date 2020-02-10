package io.github.ovso.massage.main.f_symptom.adapter;

import io.github.ovso.massage.framework.adapter.BaseAdapterView;

public interface SymptomAdapterView extends BaseAdapterView {
    void refresh(int position);

    void refreshRemove();

    void refreshRemove(int position);
}
