package com.phucthinh.ungdungdatthucan.model.EventBus;

import com.phucthinh.ungdungdatthucan.model.MonAnMoi;

public class SuaXoaEvent {
    MonAnMoi monAnMoi;

    public SuaXoaEvent(MonAnMoi monAnMoi) {
        this.monAnMoi = monAnMoi;
    }

    public MonAnMoi getMonAnMoi() {
        return monAnMoi;
    }

    public void setMonAnMoi(MonAnMoi monAnMoi) {
        this.monAnMoi = monAnMoi;
    }
}
